import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    private int level;
    private boolean isLastLevel;
    private boolean isReach = false;

    private boolean isBase = false;
    private BufferedImage bgImage = null;
    private BufferedImage pole = null;
    private BufferedImage tower = null;

    private List<Obstacle> obstaclesList = new ArrayList<>();
    private List<Enemy> enemyList = new ArrayList<>();

    public BackGround() {

    }
    public BackGround(int level,boolean isLastLevel) {
        this.level = level;
        this.isLastLevel = isLastLevel;
        if(isLastLevel) {
            bgImage = StaticValue.bg2;
        }
        else bgImage = StaticValue.bg1;

        if(level == 1) {
            // soil
            for(int i = 0;i < 800 / 30;i++) {
                obstaclesList.add(new Obstacle(i*30,420,1,this));
            }
            for(int i = 0;i <= 120;i += 30) {
                for(int j = 0;j < 800 / 30;j++) {
                    obstaclesList.add(new Obstacle(j*30,570 - i,2,this));
                }
            }
            // brick
            for(int i = 120;i <= 150;i += 30) {
                obstaclesList.add(new Obstacle(i,300,7,this));
            }
            for(int i = 300;i <= 570;i += 30) {
                if(i == 360 || i == 390 || i == 480 || i == 510 || i == 540)
                    obstaclesList.add(new Obstacle(i,300,7,this));
                else
                    obstaclesList.add(new Obstacle(i,300,0,this));
            }
            for(int i = 420;i <= 450;i += 30) {
                obstaclesList.add(new Obstacle(i,240,7,this));
            }
            // pipe
            for(int i = 360;i <= 600;i += 25) {
                if(i == 360) {
                    obstaclesList.add(new Obstacle(620,i,3,this));
                    obstaclesList.add(new Obstacle(645,i,4,this));
                }
                else {
                    obstaclesList.add(new Obstacle(620,i,5,this));
                    obstaclesList.add(new Obstacle(645,i,6,this));
                }
            }
            this.enemyList.add(new Enemy(580,385,true,1,this));
            this.enemyList.add(new Enemy(635,420,true,2,328,428,this));
        }
        else if(level == 2) {
            // soil
            for(int i = 0;i < 800 / 30;i++) {
                obstaclesList.add(new Obstacle(i*30,420,1,this));
            }
            for(int i = 0;i <= 120;i += 30) {
                for(int j = 0;j < 800 / 30;j++) {
                    obstaclesList.add(new Obstacle(j*30,570 - i,2,this));
                }
            }
            // brick
            obstaclesList.add(new Obstacle(300,330,0,this));
            for(int i = 270;i <= 330;i += 30) {
                if(i == 270 || i == 330)
                    obstaclesList.add(new Obstacle(i,360,0,this));
                else
                    obstaclesList.add(new Obstacle(i,360,7,this));
            }
            for(int i = 240;i <= 360;i += 30) {
                if(i == 240 || i == 360)
                    obstaclesList.add(new Obstacle(i,390,0,this));
                else
                    obstaclesList.add(new Obstacle(i,390,7,this));
            }
            obstaclesList.add(new Obstacle(240,300,0,this));
            for(int i = 360;i <= 540;i += 60) {
                obstaclesList.add(new Obstacle(i,270,7,this));
            }
            // pipe
            for(int i = 360;i <= 600;i += 25) {
                if(i == 360) {
                    obstaclesList.add(new Obstacle(60,i,3,this));
                    obstaclesList.add(new Obstacle(85,i,4,this));
                }
                else {
                    obstaclesList.add(new Obstacle(60,i,5,this));
                    obstaclesList.add(new Obstacle(85,i,6,this));
                }
            }
            for(int i = 330;i <= 600;i += 25) {
                if(i == 330) {
                    obstaclesList.add(new Obstacle(620,i,3,this));
                    obstaclesList.add(new Obstacle(645,i,4,this));
                }
                else {
                    obstaclesList.add(new Obstacle(620,i,5,this));
                    obstaclesList.add(new Obstacle(645,i,6,this));
                }
            }
            enemyList.add(new Enemy(500,385,true,1,this));
            enemyList.add(new Enemy(200,385,true,1,this));
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            enemyList.add(new Enemy(635,420,true,2,298,388,this));
        }
        else if(level == 3) {
            // soil
            for(int i = 0;i < 800 / 30;i++) {
                obstaclesList.add(new Obstacle(i*30,420,1,this));
            }
            for(int i = 0;i <= 120;i += 30) {
                for(int j = 0;j < 800 / 30;j++) {
                    obstaclesList.add(new Obstacle(j*30,570 - i,2,this));
                }
            }
            //brick
            for(int i = 390,temp = 290;i >= 270;i -= 30,temp += 30) {
                for(int j = temp;j <= 410;j += 30)
                    obstaclesList.add(new Obstacle(j,i,7,this));
            }
            for(int i = 390,temp = 60;i >= 360;i -= 30,temp += 30) {
                for(int j = temp;j <= 90;j += 30)
                    obstaclesList.add(new Obstacle(j,i,7,this));
            }
            //pole
            pole = StaticValue.pole;

            //tower
            tower = StaticValue.tower;
            //flag to pole
            obstaclesList.add(new Obstacle(515,220,8,this));
            enemyList.add(new Enemy(150,385,true,1,this));
        }
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }
    public int getLevel() {
        return level;
    }
    public boolean isLastLevel() {
        return isLastLevel;
    }
    public List<Obstacle> getObstaclesList() {
        return obstaclesList;
    }
    public BufferedImage getPole() {
        return pole;
    }
    public BufferedImage getTower() {
        return tower;
    }
    public boolean isReach() {
        return isReach;
    }
    public void setReach(boolean reach) {
        isReach = reach;
    }
    public boolean isBase() {
        return isBase;
    }
    public void setBase(boolean base) {
        isBase = base;
    }
    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
