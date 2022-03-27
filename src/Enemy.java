import sun.awt.windows.ThemeReader;
import sun.plugin2.message.ShowDocumentMessage;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {
    private int x;
    private int y;
    private int type;
    private int max_up;
    private int max_down;
    private int image_type = 0;
    private boolean face_to = true;
    private BufferedImage show;
    private BackGround bg;

    private Thread thread = new Thread(this);

    public Enemy(int x,int y,boolean face_to,int type,BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        show = StaticValue.mushroom.get(0);
        thread.start();
    }

    public Enemy(int x, int y, boolean face_to, int type, int max_up, int max_down, BackGround bf) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        this.max_down = max_down;
        this.max_up = max_up;
        show = StaticValue.flower.get(0);
        thread.start();
    }

    public void death() {
        show = StaticValue.mushroom.get(2);
        bg.getEnemyList().remove(this);
    }

    @Override
    public void run() {
        while(true) {
            // mushroom
            if(type == 1) {
                if(face_to)
                    this.x -= 2;
                else
                    this.x += 2;
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mushroom.get(image_type);
                boolean canLeft = true;
                boolean canRight = true;
                for(int i = 0;i < bg.getObstaclesList().size();i++) {
                    Obstacle ob = bg.getObstaclesList().get(i);
                    if(ob.getX() == this.x + 36 && ob.getY() + 65 > this.y && ob.getY() - 35 < this.y)
                        canRight = false;
                    if(ob.getX() == this.x - 36 && ob.getY() + 65 > this.y && ob.getY() - 35 < this.y)
                        canLeft = false;
                }
                if(face_to && !canLeft || this.x == 0)
                    face_to = false;
                else if(!face_to && !canRight || this.x == 800 - 36)
                    face_to = true;
            }
            // flower
            else if(type == 2) {
                if(face_to)
                    this.y -= 2;
                else
                    this.y += 2;
                image_type = image_type == 1 ? 0 : 1;
                if(face_to && this.y == max_up)
                    face_to = false;
                if(!face_to && this.y == max_down)
                    face_to = true;
                show = StaticValue.flower.get(image_type);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public BufferedImage getShow() {
        return show;
    }
    public int getType() {
        return type;
    }
}
