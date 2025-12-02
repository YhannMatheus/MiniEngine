package Game;

import miniengine.Game;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando a engine....");

        Game myGame = new Game("Teste", 1920,1080, 2);

        myGame.start();
    }
}
