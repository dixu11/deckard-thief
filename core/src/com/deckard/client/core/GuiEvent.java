package com.deckard.client.core;

import com.deckard.server.event.Event;
import com.deckard.server.event.GuiEventType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GuiEvent implements Event<GuiEventType> {

    private GuiEventType type;
//    private CardView cardView;
//    private MinionView minionView;
//    private TeamView teamView;

    @Override
    public GuiEventType getType() {
        return type;
    }

    public static GuiEvent of(GuiEventType type) {
        return builder()
                .type(type)
                .build();
    }
}
