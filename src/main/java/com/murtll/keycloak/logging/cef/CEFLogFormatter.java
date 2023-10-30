package com.murtll.keycloak.logging.cef;

import java.util.HashMap;
import java.util.Map;

import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;

public class CEFLogFormatter {

    private String cefDeviceVendor;
    private String cefDeviceProduct;
    private String cefDeviceVersion;

    public CEFLogFormatter() {
        this.cefDeviceVendor  = "Keycloak";
        this.cefDeviceProduct = "Keycloak";
        this.cefDeviceVersion = "20.0.1";
    }

    public CEFLogFormatter(String cefDeviceVendor, String cefDeviceProduct, String cefDeviceVersion) {
        this.cefDeviceVendor  = cefDeviceVendor;
        this.cefDeviceProduct = cefDeviceProduct;
        this.cefDeviceVersion = cefDeviceVersion;
    }

    public String format(Event e) {
        return this.formatGeneric(e.getType().getStableIndex(),
            e.getType().name(),
            this.getSeverity(e.getType().getStableIndex()),
            e.getDetails());
    }

    public String format(AdminEvent e) {
        int eventId = Integer.parseInt(e.getOperationType().getStableIndex() + "" + e.getResourceType().ordinal());
        String eventName = e.getOperationType() + "_" + e.getResourceType();

        Map<String, String> details = new HashMap<>();
        details.put("client_id", e.getAuthDetails().getClientId());
        details.put("ip_address", e.getAuthDetails().getIpAddress());
        details.put("realm_id", e.getAuthDetails().getRealmId());
        details.put("user_id", e.getAuthDetails().getUserId());
        details.put("resource_path", e.getResourcePath());

        return this.formatGeneric(eventId,
            eventName,
            this.getSeverity(eventId),
            details);
    }

    private String getSeverity(int index) {
        if (index > 10) {
            return "Medium";
        } else {
            return "Low";
        }
    }

    private String formatGeneric(int eventId, String eventType, String severity, Map<String, String> details) {
        StringBuilder s = new StringBuilder();

        s.append("CEF:0|")
            .append(cefDeviceVendor).append("|")
            .append(cefDeviceProduct).append("|")
            .append(cefDeviceVersion).append("|")
            .append(eventId).append("|")
            .append(eventType).append("|")
            .append(severity).append("|");

        details.forEach((k, v) -> {
            s.append(k).append('=').append(v.replace(' ', '_')).append(' ');
        });

        return s.toString();
    }

    public String getCefDeviceVendor() {
        return this.cefDeviceVendor;
    }

    public String getCefDeviceProduct() {
        return this.cefDeviceProduct;
    }

    public String getCefDeviceVersion() {
        return this.cefDeviceVersion;
    }
}