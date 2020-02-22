package Game;

import java.awt.*;

public class HUD {


    public static int mouseToggle = 1;
    //private Player player;
    //private Handler handler;

    public HUD() {
        //this.handler = handler;
        //this.player = player;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, Main.height - 60, Main.width, 30);
        g.setColor(Color.green);
        g.fillRect(5,Main.height - 55, Player.health * 2, 20);
        if (mouseToggle == 1) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.red);
        }
        g.drawRect(250, Main.height - 55, 30, 20);
        g.drawString("Click", 250, Main.height - 40);

        if (mouseToggle == 2) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.red);
        }
        g.drawRect(290, Main.height - 55, 30, 20);
        g.drawString("Gun", 290, Main.height - 40);

        if (mouseToggle != 1 && mouseToggle != 2) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.red);
        }

        g.drawRect(330, Main.height - 55, 30, 20);

        switch (mouseToggle) {
            case 1:
            case 2:
            case 3:
                g.drawString("build", 330, Main.height - 40);
                break;
            case 10:
                g.drawString("T1", 330, Main.height - 40);
                break;
            case 11:
                g.drawString("Wall", 330, Main.height - 40);
                break;
            default:
                g.drawString("build", 330, Main.height - 40);
        }
        if (mouseToggle == 3) {
            g.setColor(Color.GRAY);
            g.fillRect(245, Main.height - 91, 80, 30);
            g.setColor(Color.RED);
            g.drawRect(250, Main.height - 85, 30, 20);
            g.drawString("T1", 250, Main.height - 70);
            g.drawRect(290, Main.height - 85, 30, 20);
            g.drawString("Wall", 290, Main.height - 70);
        }


        /*if (mouseToggle == 4) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.red);
        }
        g.drawRect(370, Main.height - 55, 30, 20);
        g.drawString("Build", 370, Main.height - 40);*/

        g.setColor(Color.BLACK);
        g.drawString("val: " + MainClicker.clickVal, 500, Main.height - 40);
        g.drawString("Ammo: " + BulletClicker.ammo, 600, Main.height - 40);

    }




}
