package com.hen.phishingscanner.rules.impl;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.rules.Rule;
import com.hen.phishingscanner.util.DomainUtils;
import com.hen.phishingscanner.util.UrlExtractor;

import java.util.Optional;
import java.util.Set;

public class UrlShortenerRule implements Rule {
    private static final Set<String> SHORTENERS = Set.of("bit.ly","tinyurl.com","t.co");

    @Override
    public Optional<Finding> evaluate(EmailScanRequest email) {
        var urls = UrlExtractor.extractUrls(safe(email.getBodyText()) + " " + safe(email.getBodyHtml()));
        for (String url : urls) {
            String host = DomainUtils.hostOf(url);
            if (SHORTENERS.contains(host)) {
                return Optional.of(new Finding("R-URL-01","HIGH",25,url));
            }
        }
        return Optional.empty();
    }

    private String safe(String s){ return s == null ? "" : s; }
}
