import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

//  KeyListener : For detecting the arrow keys which will be used to move the slider
//  ActionListener : For moving the ball

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310; //   Starting position for the slider
    private int ballposX = 120;
    private int ballposY = 350;
    private int balldirX = -1; //   Just set the ball direction for X
    private int balldirY = -2;

    private MapGenerator map;

    public GamePlay() {

        map = new MapGenerator(3, 7);

        addKeyListener(this);
        setFocusable(true);                     //
        setFocusTraversalKeysEnabled(false);    //

        timer = new Timer(delay, this);  //
        timer.start();
    }

    public void paint(Graphics g) {      //  To draw different objects like slider, balls and tiles!!!

        //  Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);    //

        // Drawing Map
        map.draw((Graphics2D) g);

        //  Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 591);
        g.fillRect(0, 0, 691, 3);
        g.fillRect(691, 0, 3, 591);

        //  Scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+ score, 590, 30);

        //  The paddle : maybe its the slider
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        

        //  The ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        //  If all bricks are finished
        if (totalBricks <= 0){
            play = false;
            balldirX = 0;
            balldirY = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Won, Scores : ", 260,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart : ", 230,350);
        }

        //  For GameOver
        if (ballposY > 570){
            play = false;
            balldirX = 0;
            balldirY = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over, Scores : ", 190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart : ", 230,350);
        }
        g.dispose();                                                //
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {

            //  We have created a rectangle around the ball for the intersection between the ball and the slider
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                balldirY = -balldirY;
            }

            //  Loops for iterating through each brick
            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {    // When ball intersects with the brick
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                balldirX = -balldirX;
                            } else {
                                balldirY = -balldirY;
                            }

                            break A;
                        }
                    }
                }
            }
            //  Conditions for the movement of the ball in the left, top and right directions
            ballposX += balldirX;
            ballposY -= balldirY;

            if (ballposX < 0) {          //  Left border
                balldirX = -balldirX;
            }

            if (ballposY < 0) {          //  Top border
                balldirY = -balldirY;
            }

            if (ballposX > 670) {        //  Right border
                balldirX = -balldirX;
            }
        }
        repaint();                                  //  To call the paint() method again.
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {    //  To detect the arrow keys

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {   //  If right arrow key is pressed
            if (playerX >= 600) {
                playerX = 600;              //  So that the slider does not move outside the border of the Panel
            } else {
                moveRight();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {   //  If left arrow key is pressed
            if (playerX <= 10) {
                playerX = 10;              //  So that the slider does not move outside the border of the Panel
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER){   //  If enter key is pressed
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                balldirX = -1;
                balldirY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3,7);

                repaint();
            }
        }
    }

    public void moveRight() {//  For moving the slider right when the right arrow key is pressed
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
