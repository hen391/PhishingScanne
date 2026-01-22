package com.hen.phishingscanner.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlExtractor {
    private static final Pattern URL_PATTERN =
            Pattern.compile("(https?://[^\\s\"'<>]+)", Pattern.CASE_INSENSITIVE);

    public static List<String> extractUrls(String text) {
        List<String> out = new ArrayList<>();
        if (text == null) return out;
        Matcher m = URL_PATTERN.matcher(text);
        while (m.find()) out.add(m.group(1));
        return out;
    }
}
