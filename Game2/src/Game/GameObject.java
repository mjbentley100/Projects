package Game;

import java.awt.*;

public abstract class GameObject {

    protected double x;
    protected double y;
    protected double velocityX;
    protected double velocityY;
    protected ID id;


    public GameObject(int x, int y, ID id) {

        this.x = x;
        this.y = y;
        this.id = id;

    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public double getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }


    public void setVelocityX(double v) {
        velocityX = v;
    }

    public void setVelocityY(double v) {
        velocityY = v;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }


    public void setY(int y) {
        this.y = y;
    }

    public ID getID() {
        return id;
    }



}
