package com.deckard.server.event.bus;

import com.badlogic.gdx.Gdx;
import com.deckard.client.core.GuiEvent;
import com.deckard.server.event.*;

/**
 * Facade for EventBus implementation of {@link Event}s
 */
public class Bus {
    private static EventBusImplementation instance = new EventBusImplementation();

    public static void register(EventHandler handler, EventType type) {
        instance.register(handler, type);
    }

    public static void unregister(EventHandler handler, EventType type) {
        instance.unregister(handler,type);
    }

    public static void post(CoreEvent event) {
        instance.post(event);
    }

    public static void post(StateEvent event) {
        instance.post(event);
    }

    public static void post(ActionEvent event) {
        Gdx.app.log("EVENT",  event.getType().name());
        instance.post(event);
    }

    public static void post(GuiEvent event) {
        instance.post(event);
    }

    public static void reInitialize() {
        instance = new EventBusImplementation();
    }
}
