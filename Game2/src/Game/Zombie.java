package Game;

import java.awt.*;

public class Zombie extends GameObject{


    private Player player;
    private Main main;
    private Handler handler;

    public Zombie(int x, int y, ID id, Player player, Main main, Handler handler) {
        super(x, y, id);
        this.player = player;
        this.main = main;
        this.handler = handler;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 15, 15);
    }

    public void tick() {


        calculateVelocity((int) x, (int) y, (int) player.getX(), (int) player.getY());

        if (main.GameOver) {
            velocityX = 0;
            velocityY = 0;
        }

        collision();

        x += velocityX;
        y += velocityY;

    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, 15, 15);

    }

    private void collision() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);

            if (tmpObject.getID() == ID.WALL) {
                if (getBounds().intersects(tmpObject.getBounds())) {
                    velocityX = 0;
                    velocityY = 0;
                }
            }
        }
    }


    public void calculateVelocity(int fromX, int fromY, int toX, int toY)
    {
        double distance = Math.sqrt(Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        double speed = 1; //set the speed in [2,n)  n should be < 20 for normal speed
        //find Y
        if (distance != 0) {
            velocityY = (float) ((toY - fromY) * speed / distance);
            //find X
            velocityX = (float) ((toX - fromX) * speed / distance);
        } else {
            velocityY = 0;
            velocityX = 0;
        }
    }

}
