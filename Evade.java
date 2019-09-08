
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Evade extends JFrame implements KeyListener {
    
    int score = 0;
    
    Image imageBomb = null;

    JPanel p = new JPanel();
    
    int x = 450, y = 500;
    
    class Bomb {
        int x, y;
    }
    
    ArrayList<Bomb> bombs = new ArrayList<Bomb>();
   
    public Evade() {
        setTitle("Eternal LIFE!");
        p.setBounds(0, 0, 1000, 600);
        this.setLayout(null);
        this.setBounds(p.getBounds());
        this.getRootPane().add(p);
        
        this.show();
        this.addKeyListener(this);
        this.play();
        
        this.initBombs();
    }
    
    private void initBombs() {
        for(int i=0; i<10; i++) {
            Random random = new Random();
            int v = random.nextInt(1000);
            int w = -400 + random.nextInt(600);
            Bomb bomb = new Bomb();
            bomb.x = v;
            bomb.y = w;
            bombs.add(bomb);
        }
    }
    
    private void play() {
        try {
            imageBomb = ImageIO.read(ClassLoader.getSystemResource("bomb.gif"));
        } catch(Exception e) {
            e.printStackTrace();
        }
        Graphics g = p.getGraphics();
        Thread t = new Thread() {
            public void run() {
                while(true) {
                    try {
                        for(int i=0; i<bombs.size(); i++) {
                            g.drawImage(imageBomb, bombs.get(i).x, bombs.get(i).y, 70, 70, null);
                            bombs.get(i).y+=5;
                        }
                        boolean f = false;
                        for(int i=0; i<bombs.size(); i++) {
                            f = false;
                            
                            if((bombs.get(i).x <= x && bombs.get(i).x + 70 >= x) && (bombs.get(i).y <= y && bombs.get(i).y + 70 >= y))
                                System.exit(1);

                            if(bombs.get(i).y > 630)
                                f = true;
                            else
                                f = false;
                            if(bombs.get(i).y > 630) {
                                score+=100;
                                bombs.remove(bombs.get(i));
                            }
                        }
                        if(f) {
                            initBombs();
                        }
                        g.setColor(Color.ORANGE);
                        g.drawString("SCORE " + score, 500, 20);
                        Image image = ImageIO.read(ClassLoader.getSystemResource("jet.png"));
                        g.drawImage(image, x, y, 10, 20, null);
                    } catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                    try {
                        Thread.sleep(25);
                        g.setColor(Color.BLUE);
                        g.fillRect(0, 0, 1000, 600);
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            x-=3;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x+=3;
        }
    }
    
    public void keyReleased(KeyEvent e) {
        
    }

    public void keyExited(KeyEvent e) {
        
    }
    
    public void keyTyped(KeyEvent e) {
        
    }

    public static void main(String arg[]) {
        Evade e = new Evade();
    }
}