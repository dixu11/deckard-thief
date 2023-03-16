package com.deckard.client;

import java.awt.*;

public class GuiParams {
    public static final Color MAIN_COLOR_BRIGHT = Color.GRAY;
    public static final Color MAIN_COLOR_DARK = Color.DARK_GRAY;
    public static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    private static final int DEFAULT_WIDTH = 1920;
    private static final int DEFAULT_HEIGHT = 980;
    public static final int WIDTH = DEFAULT_WIDTH;
    public static final int HEIGHT = DEFAULT_HEIGHT;
    //card
    public static final int CARD_WIDTH = 150;
    public static final int CARD_HEIGHT = 225;
    public static final int CARD_PADDING = 20;
    public static final int HIGHLIGHT_BORDER = 3;

    public static int getWidth(double percent) {
        return (int) (WIDTH * percent);
    }

    public static int getHeight(double percent) {
        return (int) (HEIGHT * percent);
    }
}
