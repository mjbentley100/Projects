package Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {
    private Handler handler;
    private MainClicker mainClicker;
    private BulletClicker bulletClicker;

    public MouseInput(Handler handler, MainClicker mainClicker, BulletClicker bulletClicker) {
        this.handler = handler;
        this.mainClicker = mainClicker;
        this.bulletClicker = bulletClicker;
    }


    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        int collisionFlag = 0;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmp2Object = handler.object.get(i);

            if (tmp2Object.getID() == ID.WALL || tmp2Object.getID() == ID.BULLETCLICKER || tmp2Object.getID() == ID.BASICAUTOCLICKER || tmp2Object.getID() == ID.MAINCLICKER) {
                if (tmp2Object.getX() == (mx/15) * 15 && tmp2Object.getY() == (my/15) * 15) {
                    collisionFlag = 1;
                }
            }

        }


        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);

            if (tmpObject.getID() == ID.PLAYER && HUD.mouseToggle == 2 && BulletClicker.ammo != 0) {
                handler.addObject(new Bullet((int)tmpObject.getX() + 8, (int)tmpObject.getY() + 8, ID.BULLET, handler, mx, my));
                BulletClicker.ammo--;
            } else if (tmpObject.getID() == ID.PLAYER && HUD.mouseToggle == 1) {
                if (mx >= mainClicker.getX() && mx <= mainClicker.getX() + 15 && my >= mainClicker.getY() && my <= mainClicker.getY() + 15) {
                    MainClicker.clickVal++;
                }
                if (mx >= bulletClicker.getX() && mx <= bulletClicker.getX() + 15 && my >= bulletClicker.getY() && my <= bulletClicker.getY() + 15 && MainClicker.clickVal >=50) {
                    BulletClicker.ammo += 6;
                    MainClicker.clickVal -= 50;
                }
            } else if (tmpObject.getID() == ID.PLAYER && HUD.mouseToggle == 10) {
                if (MainClicker.clickVal >= BasicAutoClicker.BasicAutoClickerCost && collisionFlag == 0) {
                    handler.addObject(new BasicAutoClicker((mx/15) * 15, (my/15) * 15, ID.BASICAUTOCLICKER));
                    MainClicker.clickVal -=100;
                    BasicAutoClicker.BasicAutoClickerCost *= 1.1;
                }
            } else if (tmpObject.getID() == ID.PLAYER && HUD.mouseToggle == 11) {
                if (MainClicker.clickVal >= 50 && collisionFlag == 0) {
                    handler.addObject(new Wall((mx/15) * 15, (my/15) * 15, ID.WALL, 15, 15, handler));
                    MainClicker.clickVal -= 50;
                }
            }



        }
    }
}
