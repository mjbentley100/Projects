package Game;

import java.awt.*;

public class BasicAutoClicker extends GameObject{

    public static int BasicAutoClickerCost = 100;


    public BasicAutoClicker(int x, int y, ID id) {
        super(x, y, id);
    }

    public void tick() {
        if (Main.numTick % 30 == 0) {
            MainClicker.clickVal++;
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(199, 112, 0));
        g.fillRect((int)x, (int)y, 15,15);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 15,15);
    }




}
