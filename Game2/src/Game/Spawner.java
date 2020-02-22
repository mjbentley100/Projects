package Game;

import java.util.Random;

public class Spawner {

    private Handler handler;
    private Main main;
    private int waveNum;

    public Spawner(Player player, Handler handler, Main main) {
        this.handler = handler;
        this.main = main;
        waveNum = 1;
    }

    //HUUUUUUUGE Work In Progress
    //This is only in for testing purposes
    public void tick(Player player) {
        if (Main.numTick % 1000 == 0) {



            int GreenSpawn = 0;
            int RedSpawn = 0;
            int BlueSpawn = 0;
            int PurpleSpawn = 0;
            int BLACKSpawn = 0;


            /*RedSpawn = 0;
            GreenSpawn = 0;
            BlueSpawn = 0;*/


            switch(waveNum) {
                case 1:
                    PurpleSpawn = 1;
                    break;
                case 2:
                    GreenSpawn = 2;
                    break;
                case 3:
                    GreenSpawn = 3;
                    break;
                case 4:
                    GreenSpawn = 4;
                    break;
                case 5:
                    RedSpawn = 1;
                    GreenSpawn = 0;
                    break;
                case 6:
                    RedSpawn = 0;
                    GreenSpawn = 8;
                    BlueSpawn = 0;
                    break;
                case 7:
                    RedSpawn = 0;
                    GreenSpawn = 0;
                    BlueSpawn = 3;
                    break;
                case 8:
                    RedSpawn = 1;
                    GreenSpawn = 5;
                    BlueSpawn = 0;
                    break;
                case 9:
                    RedSpawn = 0;
                    GreenSpawn = 5;
                    BlueSpawn = 5;
                    break;
                case 10:
                    RedSpawn = 4;
                    GreenSpawn = 0;
                    BlueSpawn = 0;
                    break;
                default:

                    GreenSpawn = 1;
            }


            for (int i = 0; i < GreenSpawn; i++) {

                Random randX = new Random();
                Random randY = new Random();
                Random XYdecider = new Random();
                int randomX = randX.nextInt(Main.width - 50);
                int randomY = randY.nextInt(Main.height - 50);
                int randomXYdec = XYdecider.nextInt(3);


                if (randomXYdec == 0) {
                    handler.addObject(new Zombie(randomX, 50, ID.ZOMBIE, player, main, handler));
                } else if (randomXYdec == 1) {
                    handler.addObject(new Zombie(randomX, 600, ID.ZOMBIE, player, main, handler));
                } else if (randomXYdec == 2) {
                    handler.addObject(new Zombie(50, randomY, ID.ZOMBIE, player, main, handler));
                } else if (randomXYdec == 3) {
                    handler.addObject(new Zombie(800, randomY, ID.ZOMBIE, player, main, handler));
                }
            }

            for (int i = 0; i < RedSpawn; i++) {
                Random randX = new Random();
                Random randY = new Random();
                Random XYdecider = new Random();
                int randomX = randX.nextInt(Main.width - 50);
                int randomY = randY.nextInt(Main.height - 50);
                int randomXYdec = XYdecider.nextInt(3);

                if (randomXYdec == 0) {
                    handler.addObject(new RedZombie(randomX, 50, ID.REDZOMBIE, player, main, handler));
                } else if (randomXYdec == 1) {
                    handler.addObject(new RedZombie(randomX, 600, ID.REDZOMBIE, player, main, handler));
                } else if (randomXYdec == 2) {
                    handler.addObject(new RedZombie(50, randomY, ID.REDZOMBIE, player, main, handler));
                } else if (randomXYdec == 3) {
                    handler.addObject(new RedZombie(800, randomY, ID.REDZOMBIE, player, main, handler));
                }


            }


            for (int i = 0; i < BlueSpawn; i++) {
                Random randX = new Random();
                Random randY = new Random();
                Random XYdecider = new Random();
                int randomX = randX.nextInt(Main.width - 50);
                int randomY = randY.nextInt(Main.height - 50);
                int randomXYdec = XYdecider.nextInt(3);

                if (randomXYdec == 0) {
                    handler.addObject(new BlueZombie(randomX, 50, ID.BLUEZOMBIE, player, main, handler));
                } else if (randomXYdec == 1) {
                    handler.addObject(new BlueZombie(randomX, 600, ID.BLUEZOMBIE, player, main, handler));
                } else if (randomXYdec == 2) {
                    handler.addObject(new BlueZombie(50, randomY, ID.BLUEZOMBIE, player, main, handler));
                } else if (randomXYdec == 3) {
                    handler.addObject(new BlueZombie(800, randomY, ID.BLUEZOMBIE, player, main, handler));
                }


            }


            for (int i = 0; i < PurpleSpawn; i++) {
                Random randX = new Random();
                Random randY = new Random();
                Random XYdecider = new Random();
                int randomX = randX.nextInt(Main.width - 50);
                int randomY = randY.nextInt(Main.height - 50);
                int randomXYdec = XYdecider.nextInt(3);

                if (randomXYdec == 0) {
                    handler.addObject(new PurpleZombie(randomX, 50, ID.PURPLEZOMBIE, player, main, handler));
                } else if (randomXYdec == 1) {
                    handler.addObject(new PurpleZombie(randomX, 600, ID.PURPLEZOMBIE, player, main, handler));
                } else if (randomXYdec == 2) {
                    handler.addObject(new PurpleZombie(50, randomY, ID.PURPLEZOMBIE, player, main, handler));
                } else if (randomXYdec == 3) {
                    handler.addObject(new PurpleZombie(800, randomY, ID.PURPLEZOMBIE, player, main, handler));
                }


            }

            waveNum++;
        }
    }


}
