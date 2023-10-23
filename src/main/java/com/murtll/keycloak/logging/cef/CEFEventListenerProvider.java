package com.murtll.keycloak.logging.cef;

import java.util.Date;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class CEFEventListenerProvider implements EventListenerProvider {

    private CEFLogFormatter logFormatter;

    public CEFEventListenerProvider() {
        this.logFormatter = new CEFLogFormatter();
    }

    @Override
    public void onEvent(Event event) {
        System.out.println(new Date(event.getTime()) + " " + this.logFormatter.format(event));
    }

    @Override
    public void onEvent(AdminEvent event, boolean includeRepresentation) {
        System.out.println(new Date(event.getTime()) + " " + this.logFormatter.format(event));
    }

    @Override
    public void close() {}
}