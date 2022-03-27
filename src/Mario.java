import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    private int x;
    private int y;
    private String status;
    private int xSpeed;
    private int ySpeed;
    private int index;
    private int upTime;
    private boolean isOk;
    private boolean isDeath;
    private BufferedImage show = null;
    private BackGround backGround = new BackGround();

    private Thread thread = null;

    public Mario() {

    }
    public Mario(int x,int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand-right";
        thread = new Thread(this);
        thread.start();
    }

    public void leftMove() {
        xSpeed = -5;
        if(backGround.isReach())
            xSpeed = 0;
        if(status.indexOf("jump") != -1)
            status = "jump-left";
        else
            status = "move-left";
    }
    public void rightMove() {
        xSpeed = 5;
        if(backGround.isReach())
            xSpeed = 0;
        if(status.indexOf("jump") != -1)
            status = "jump-right";
        else
            status = "move-right";
    }
    public void leftStop() {
        xSpeed = 0;
        if(status.indexOf("jump") != -1)
            status = "jump-left";
        else
            status = "stop-left";
    }
    public void rightStop() {
        xSpeed = 0;
        if(status.indexOf("jump") != -1)
            status = "jump-right";
        else
            status = "stop-right";
    }
    public void jump() {
        if(status.indexOf("jump") == -1) {
            if(status.indexOf("left") != -1)
                status = "jump-left";
            else
                status = "jump-right";
            ySpeed = -10;
            upTime = 7;
        }
        if(backGround.isReach())
            ySpeed = 0;
    }
    public void fall() {
        ySpeed = 10;
        if(status.indexOf("left") != -1)
            status = "jump-left";
        else
            status = "jump-right";
    }

    public void death() {
        isDeath = true;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
    public BufferedImage getShow() {
        return show;
    }
    public void setShow(BufferedImage show) {
        this.show = show;
    }
    public boolean isOk() {
        return isOk;
    }
    public boolean isDeath() {
        return isDeath;
    }

    @Override
    public void run() {
        while(true) {
            boolean onObstacle = false;
            boolean canRight = true;
            boolean canLeft = true;
            if(backGround.isLastLevel() && this.x >= 500) {
                backGround.setReach(true);
                if(this.backGround.isBase()) {
                    status = "move-right";
                    if(x < 690)
                        x += 5;
                    else
                        isOk = true;
                }
                else {
                    if(y < 395) {
                        xSpeed = 0;
                        y += 5;
                        status = "jump-right";
                    }
                    if(y > 395) {
                        y = 395;
                        status = "stop-right";
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                for (int i = 0; i < backGround.getObstaclesList().size(); i++) {
                    Obstacle ob = backGround.getObstaclesList().get(i);
                    if (ob.getY() == this.y + 25 && ob.getX() > this.x - 30 && ob.getX() < this.x + 25)
                        onObstacle = true;
                    if (ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20 && ob.getX() > this.x - 30 && ob.getX() < this.x + 25) {
                        if (ob.getType() == 0) {
                            backGround.getObstaclesList().remove(ob);
                            break;
                        }
                        upTime = 0;
                    }
                    if (ob.getX() == this.x + 25 && ob.getY() > this.y - 30 && ob.getY() < this.y + 25)
                        canRight = false;
                    if (ob.getX() == this.x - 30 && ob.getY() > this.y - 30 && ob.getY() < this.y + 25)
                        canLeft = false;
                }
                // with enemy
                for(int i = 0;i < backGround.getEnemyList().size();i++) {
                    Enemy en = backGround.getEnemyList().get(i);
                    if(en.getY() == this.y + 20 && en.getX() - 25 <= this.x && en.getX() + 35 >= this.x) {
                        if(en.getType() == 1) {
                            en.death();
                            upTime = 3;
                            ySpeed = -10;
                        }
                        else if(en.getType() == 2) {
                            this.death();
                        }
                    }
                    if(en.getX() + 35 > this.x && en.getX() - 25 < this.x && en.getY() + 35 > this.y && en.getY() - 20 < this.y)
                        this.death();
                }
                // jump / fall
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0)
                            status = "move-left";
                        else
                            status = "stand-left";
                    } else {
                        if (xSpeed != 0)
                            status = "move-right";
                        else
                            status = "stand-right";
                    }
                } else {
                    if (upTime != 0)
                        upTime--;
                    else
                        fall();
                    y += ySpeed;
                }
                // run / stop
                if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) {
                    x += xSpeed;
                    if (x < 0)
                        x = 0;
                }
                if (status.contains("move")) {
                    index = index == 0 ? 1 : 0;
                }
                if (status.equals("move-left")) {
                    show = StaticValue.run_L.get(index);
                }
                if (status.equals("move-right")) {
                    show = StaticValue.run_R.get(index);
                }
                if (status.equals("stop-left")) {
                    show = StaticValue.stand_L;
                }
                if (status.equals("stop-right")) {
                    show = StaticValue.stand_R;
                }
                if (status.equals("jump-left")) {
                    show = StaticValue.jump_L;
                }
                if (status.equals("jump-right")) {
                    show = StaticValue.jump_R;
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
