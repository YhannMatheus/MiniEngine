package miniengine;

import javafx.application.Application;

public class Game {

    private static Game instance;

    private final String title;
    private final int width;
    private final int height;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void start() {
        instance = this;

        Application.launch(GameWindow.class);
    }

    public static Game getInstance() {
        return instance;
    }

    public String getTitle() { return title; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
