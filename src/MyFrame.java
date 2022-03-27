import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class MyFrame extends JFrame implements KeyListener,Runnable {
    private List<BackGround> allBg = new ArrayList<>();
    private BackGround nowBg = new BackGround();
    private Image offScreenImage = null;
    private Mario mario = new Mario();
    private Thread thread = new Thread(this);

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }

    public MyFrame() {
        this.setSize(800,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.addKeyListener(this);
        this.setTitle("Super Mario");
        StaticValue.init();
        mario = new Mario(10,325);
        for(int i = 1;i <= 3;i++) {
            allBg.add(new BackGround(i,i == 3));
        }
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        repaint();
        thread.start();
    }

    public void paint(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = createImage(800,600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);
        graphics.drawImage(nowBg.getBgImage(),0,0,this);
        for(int i = 0;i < nowBg.getEnemyList().size();i++) {
            Enemy en = nowBg.getEnemyList().get(i);
            graphics.drawImage(en.getShow(),en.getX(),en.getY(),this);
        }
        for(int i = 0;i < nowBg.getObstaclesList().size();i++) {
            Obstacle ob = nowBg.getObstaclesList().get(i);
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }
        graphics.drawImage(nowBg.getPole(),500,220,this);
        graphics.drawImage(nowBg.getTower(),620,270,this);
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);
        g.drawImage(offScreenImage,0,0,this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 37)
            mario.leftMove();
        else if(e.getKeyCode() == 39)
            mario.rightMove();
        else if(e.getKeyCode() == 38)
            mario.jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == 37)
            mario.leftStop();
        else if(e.getKeyCode() == 39)
            mario.rightStop();
    }

    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                if(mario.getX() + 25 >= 800) {
                    Thread.sleep(50);
                    nowBg = allBg.get(nowBg.getLevel() <= 2 ? nowBg.getLevel() : 2);
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(325);
                }
                if(mario.isDeath()) {
                    JOptionPane.showMessageDialog(this,"Your Mario Dead!");
                    System.exit(0);
                }
                if(mario.isOk()) {
                    JOptionPane.showMessageDialog(this,"Congratulations on Your Success!");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
