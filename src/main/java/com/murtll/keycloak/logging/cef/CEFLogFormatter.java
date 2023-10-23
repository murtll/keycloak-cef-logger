package com.murtll.keycloak.logging.cef;

import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;

public class CEFLogFormatter {

    public String format(Event e) {
        StringBuilder s = new StringBuilder();

        s.append("CEF:0|Keycloak|Keycloak|20.0.1|")
            .append(e.getId()).append("|")
            .append(e.getType()).append("|")
            .append("Medium").append("|");

        e.getDetails().forEach((k, v) -> {
            s.append(k).append('=').append(v).append(' ');
        });

        return s.toString();
    }

    public String format(AdminEvent e) {
        StringBuilder s = new StringBuilder();

        s.append("CEF:0|Keycloak|Keycloak|20.0.1|")
            .append(e.getId()).append("|")
            .append(e.getOperationType().name()).append("|")
            .append("Medium").append("|");

        s.append("admin event type, no details 4 now");

        return s.toString();
    }

}