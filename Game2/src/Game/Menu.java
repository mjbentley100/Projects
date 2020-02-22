package Game;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter {

    private Main main;
    private Handler handler;


    public Menu(Main main, Handler handler) {
        this.main = main;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();


        if (Main.gameState == Main.state.MENU) {
            if (mouseOver(mx, my, 100, 110, 100, 30)) {
                BufferedImageLoader loader = new BufferedImageLoader();
                main.hud = new HUD();
                main.mainClicker = new MainClicker(0, 0, ID.MAINCLICKER);
                handler.addObject(main.mainClicker);
                main.bulletClicker = new BulletClicker(0, 0, ID.BULLETCLICKER);
                handler.addObject(main.bulletClicker);
                main.addMouseListener(new MouseInput(handler, main.mainClicker, main.bulletClicker));


                main.map = loader.loadImage("/House.png");
                main.LoadLevel(main.map);
                main.spawner = new Spawner(main.player, handler, main);
                main.gameState = Main.state.GAME;
            }


            if (mouseOver(mx, my, 100, 140, 100, 30)) {
                System.exit(1);
            }
        }


    }


    public void mouseReleased(MouseEvent e) {

    }



    public void tick() {

    }


    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("BlockMin 2", 100, 100);
        g.setFont(new Font("Serif", Font.BOLD, 12));
        g.drawString("Play", 100, 130);
        g.drawString("Quit", 100, 160);


    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) {
                return true;
            }
        }

        return false;
    }


}
