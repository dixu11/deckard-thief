package com.deckard.server.event;

import com.deckard.client.core.GuiEvent;

public interface EventHandler {
    default void handle(ActionEvent event) {

    }

    default void handle(CoreEvent event) {

    }

    default void handle(StateEvent event) {

    }

    default void handle(GuiEvent event) {

    }
}
