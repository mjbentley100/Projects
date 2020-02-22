package Game;

import Game.GameObject;
import Game.Handler;
import Game.ID;
import Game.Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private Main main;

    public KeyInput(Handler handler, Main main) {
        this.handler = handler;
        this.main = main;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);
            if (tmpObject.getID() == ID.PLAYER && !main.GameOver) {
                //key events for player 1
                switch (key) {
                    case KeyEvent.VK_W:
                        if (tmpObject.getVelocityY() > -2) {
                            tmpObject.setVelocityY(tmpObject.getVelocityY() - 2);
                        }
                        break;
                    case KeyEvent.VK_A:
                        if (tmpObject.getVelocityX() > -2) {
                            tmpObject.setVelocityX(tmpObject.getVelocityX() - 2);
                        }
                        break;
                    case KeyEvent.VK_S:
                        if (tmpObject.getVelocityY() < 2) {
                            tmpObject.setVelocityY(tmpObject.getVelocityY() + 2);
                        }
                        break;
                    case KeyEvent.VK_D:
                        if (tmpObject.getVelocityX() < 2) {
                            tmpObject.setVelocityX(tmpObject.getVelocityX() + 2);
                        }
                        break;
                    case KeyEvent.VK_1:
                        if (HUD.mouseToggle != 3) {
                            HUD.mouseToggle = 1;
                        } else {
                            HUD.mouseToggle = 10;
                        }
                        break;
                    case KeyEvent.VK_2:
                        if (HUD.mouseToggle != 3) {
                            HUD.mouseToggle = 2;
                        } else {
                            HUD.mouseToggle = 11;
                        }
                        break;
                    case KeyEvent.VK_3:
                        if (HUD.mouseToggle != 3) {
                            HUD.mouseToggle = 3;
                        } else {
                            HUD.mouseToggle = 13;
                        }
                        break;
                    case KeyEvent.VK_4:
                        if (HUD.mouseToggle == 3) {
                            HUD.mouseToggle = 14;
                        }
                        break;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObject = handler.object.get(i);
            if (tmpObject.getID() == ID.PLAYER) {
                //key events for player 1
                if (!main.GameOver) {
                    switch (key) {
                        case KeyEvent.VK_W:
                            tmpObject.setVelocityY(tmpObject.getVelocityY() + 2);
                            break;
                        case KeyEvent.VK_A:
                            tmpObject.setVelocityX(tmpObject.getVelocityX() + 2);
                            break;
                        case KeyEvent.VK_S:
                            tmpObject.setVelocityY(tmpObject.getVelocityY() - 2);
                            break;
                        case KeyEvent.VK_D:
                            tmpObject.setVelocityX(tmpObject.getVelocityX() - 2);
                            break;
                    }
                }


            }
        }
    }
}

