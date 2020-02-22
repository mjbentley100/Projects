package Game;

import java.awt.*;

public class MainClicker extends GameObject{


    public static int clickVal = 0;


    public MainClicker(int x, int y, ID id) {
        super(x, y, id);
    }


    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(new Color(0, 207, 207));
        g.fillRect((int)x, (int)y, 15, 15);
    }

    public Rectangle getBounds() {
        return new Rectangle((int )x, (int)y, 15, 15);
    }



    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


}
