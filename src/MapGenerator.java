import java.awt.*;

public class MapGenerator {

    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public MapGenerator(int row, int col){
        map = new int [row][col];

        for (int i=0; i < map.length; i++){
            for (int j=0; j < map[0].length; j++){      //  ???
                map[i][j] = 1;                          //  It is equal to 1 that is brick exists there and if equal to zero than brick does not exist there!!!
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    //  Method to draw particular Bricks
    public void draw(Graphics2D g){

        for (int i=0; i < map.length; i++){
            for (int j=0; j < map[0].length; j++){
                if (map[i][j] > 0){
                    g.setColor(Color.white);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth,brickHeight );

                    g.setStroke(new BasicStroke(3));    //  ???
                    g.setColor(Color.black);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                }
            }
        }

    }
    //  Setting the value of the brick equal to 0 if the ball intersects with brick
    public void setBrickValue(int value, int row, int col){
        map[row][col] = value;
    }
}
