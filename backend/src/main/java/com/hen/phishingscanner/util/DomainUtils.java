package com.hen.phishingscanner.util;

import java.net.URI;

public class DomainUtils {

    public static String hostOf(String url) {
        try {
            URI uri = URI.create(url);
            String host = uri.getHost();
            return host == null ? "" : host.toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean isIpAddress(String host) {
        return host != null && host.matches("\\d{1,3}(\\.\\d{1,3}){3}");
    }

    public static String extractDomainFromEmailField(String field) {
        if (field == null) return "";
        String s = field.trim();

        int lt = s.indexOf('<');
        int gt = s.indexOf('>');
        if (lt >= 0 && gt > lt) s = s.substring(lt + 1, gt);

        int at = s.lastIndexOf('@');
        if (at < 0) return "";
        return s.substring(at + 1).toLowerCase().replaceAll("[^a-z0-9.\\-]", "");
    }
}
