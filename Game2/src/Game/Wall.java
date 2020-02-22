package Game;

import java.awt.*;

public class Wall extends GameObject{

    private int height;
    private int width;
    private int health;
    private Handler handler;

    public Wall(int x, int y, ID id, int height, int width, Handler handler) {
        super(x, y, id);
        this.height = height;
        this.width = width;
        health = 200;
        this.handler = handler;
    }

    public void tick() {
        collision();
        if (health <= 0) {
            handler.removeObject(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(53, 49, 49));
        g.fillRect((int)x, (int)y, width, height);

    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 15, 15);
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);

            if (tmpObject.getID() == ID.ZOMBIE || tmpObject.getID() == ID.BLUEZOMBIE || tmpObject.getID() == ID.PURPLEZOMBIE) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    health--;
                }
            }

            if (tmpObject.getID() == ID.REDZOMBIE) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    health -= 2;
                }
            }

        }
    }


}
