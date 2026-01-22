package com.hen.phishingscanner.rules.impl;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.rules.Rule;
import com.hen.phishingscanner.util.UrlExtractor;

import java.util.Optional;

public class ManyLinksRule implements Rule {
    @Override
    public Optional<Finding> evaluate(EmailScanRequest email) {
        var urls = UrlExtractor.extractUrls(safe(email.getBodyText()) + " " + safe(email.getBodyHtml()));
        if (urls.size() >= 5) {
            return Optional.of(new Finding("R-URL-03","LOW",10,"linksCount=" + urls.size()));
        }
        return Optional.empty();
    }

    private String safe(String s){ return s == null ? "" : s; }
}
