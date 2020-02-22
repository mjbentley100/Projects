package Game;

import java.awt.*;

public class Player extends GameObject{

    private Handler handler;
    public static int health = 100;

    public Player(int x, int y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
    }


    public void tick() {
        x += velocityX;
        y += velocityY;

        x = Main.clamp((int)x, 0, Main.width - 60);
        y = Main.clamp((int)y, 0, Main.height - 75);
        collision();
    }

    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int)x, (int)y, 15, 15);
    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);

            if (tmpObject.getID() == ID.WALL) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    x += velocityX * -1;
                    y += velocityY * -1;
                }
            }
            if (tmpObject.getID() == ID.ZOMBIE || tmpObject.getID() == ID.PURPLEZOMBIE || tmpObject.getID() == ID.BLUEZOMBIE || tmpObject.getID() == ID.REDZOMBIE) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    health--;
                    health = Main.clamp(health, 0, 100);
                }
            }

            if (tmpObject.getID() == ID.ZOMBIEBULLET) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    health -= 10;
                }
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 15, 15);
    }
}
