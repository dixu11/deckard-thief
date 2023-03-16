package com.deckard.server.event;

public interface Event<T extends EventType> {
   T getType();
}
