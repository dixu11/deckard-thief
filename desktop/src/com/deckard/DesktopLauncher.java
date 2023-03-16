package com.deckard;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.deckard.client.GameScreen;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Decard Thief");
        config.setWindowedMode(1920, 980);
        config.useVsync(true);
        config.setForegroundFPS(60);
        new Lwjgl3Application(new GameScreen(), config);
    }
}
