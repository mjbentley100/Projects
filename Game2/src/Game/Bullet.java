package Game;

import java.awt.*;

public class Bullet extends GameObject {

    private Handler handler;
    private int lifeSpan;

    public Bullet(int x, int y, ID id, Handler handler, int mx, int my) {
        super(x, y, id);
        this.handler = handler;

        calculateVelocity(x, y, mx ,my);
        lifeSpan = Main.numTick;

    }


    public void tick() {
        x += velocityX;
        y += velocityY;

        collision();

        if (Main.numTick - lifeSpan > 60) {
            handler.removeObject(this);
        }

    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect((int)x, (int)y, 4, 4);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 4, 4);
    }

    public void calculateVelocity(int fromX, int fromY, int toX, int toY)
    {
        double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        double speed = 10; //set the speed in [2,n)  n should be < 20 for normal speed
        //find Y
        velocityY = (float)((toY - fromY) * speed / distance);
        //find X
        velocityX = (float)((toX - fromX) * speed / distance);
    }


    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);


            if (tmpObject.getID() == ID.ZOMBIE) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    handler.removeObject(tmpObject);
                    handler.removeObject(this);
                }
            }

        }
    }



}
