package Game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Main extends Canvas implements Runnable{

    public static final int width = 900, height = width / 12 * 9;
    public static int numTick = 0;

    private Thread thread;
    private boolean running = false;
    private Handler handler;
    public KeyInput keyInput;
    public boolean GameOver = false;
    public Player player;
    public BufferedImage map = null;
    public HUD hud;
    public MainClicker mainClicker;
    public BulletClicker bulletClicker;
    public Spawner spawner;
    public Menu menu;

    public static state gameState = state.MENU;

    public enum state {
        MENU,
        GAME
    }


    public Main() {
        new GameFrame(width, height, "Blockmin 2", this);
        handler = new Handler();
        keyInput = new KeyInput(handler, this);
        this.addKeyListener(keyInput);

        if (gameState == state.GAME) {


            BufferedImageLoader loader = new BufferedImageLoader();
            hud = new HUD();
            mainClicker = new MainClicker(0, 0, ID.MAINCLICKER);
            handler.addObject(mainClicker);
            bulletClicker = new BulletClicker(0, 0, ID.BULLETCLICKER);
            handler.addObject(bulletClicker);
            this.addMouseListener(new MouseInput(handler, mainClicker, bulletClicker));


            map = loader.loadImage("/House.png");
            LoadLevel(map);
            spawner = new Spawner(player, handler, this);
        } else {
            menu = new Menu(this, handler);
            this.addMouseListener(menu);
        }
    }


    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        this.requestFocus();
        while(running)
        {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >=1)
            {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer += 1000;
                //System.out.println("FPS: "+ frames);
                frames = 0;
            }
        }
        stop();
    }


    private void tick() {

        handler.tick();

        if (gameState == state.GAME) {

            if (numTick > 100) {
                spawner.tick(player);
            }
            numTick++;
        } else {
            menu.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(54, 183, 104));
        g.fillRect(0,0,width, height);

        if (gameState == state.GAME) {

            mainClicker.render(g);
            bulletClicker.render(g);
            handler.render(g);
            hud.render(g);
        } else {
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public void LoadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int Playerx = 0;
        int Playery = 0;
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = (pixel) & 0xFF;
                if (red == 255) {
                    handler.addObject(new Wall(xx*15, yy*15, ID.WALL, 15, 15, handler));
                }

                if (blue == 255) {
                    Playerx = xx;
                    Playery = yy;
                }

                if (blue == 254) {
                    mainClicker.setXY(xx * 15, yy * 15);
                }

                if (blue == 253) {
                    bulletClicker.setXY(xx * 15, yy * 15);
                }
            }
        }

        player = new Player(Playerx*15, Playery*15, ID.PLAYER, handler);
        handler.addObject(player);
    }


    public static int clamp(int var, int min, int max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min){
            return var = min;
        } else {
            return var;
        }
    }

    public static double clampDouble(double var, double min, double max) {
        if (var >= max) {
            return var = max;
        } else if (var <= min){
            return var = min;
        } else {
            return var;
        }
    }


    public static void main(String[] args) {
        new Main();
    }

}
