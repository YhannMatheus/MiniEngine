package Game;

import miniengine.Core.Game;

public class Main {
    public static void main(String[] args) {
        Game myGame = new Game("Demo", 1920,1080, 2);

        //Game.getInstance().serInitialWorld( new Scene());
        myGame.start();
    }
}
