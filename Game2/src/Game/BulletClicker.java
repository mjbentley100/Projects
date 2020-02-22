package Game;

import java.awt.*;

public class BulletClicker extends GameObject{

    public static int ammo = 6;


    public BulletClicker(int x, int y, ID id) {
        super(x, y, id);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(new Color(206, 207, 0));
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
