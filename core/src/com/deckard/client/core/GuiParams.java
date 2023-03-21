package com.deckard.client.core;


import com.badlogic.gdx.graphics.Color;

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
    public static final int MINION_WIDTH = 225;
    public static final int MINION_HEIGHT = 225;
    public static final int CARD_SPACING = 40;
    public static final int HIGHLIGHT_BORDER = 3;
    public static final int LEADER_HAND_X = (int) (WIDTH / 2 );
    public static final int LEADER_HAND_Y =-CARD_HEIGHT/2;

    public static int getWidth(double percent) {
        return (int) (WIDTH * percent);
    }

    public static int getHeight(double percent) {
        return (int) (HEIGHT * percent);
    }
}
