package com.hen.phishingscanner.rules.impl;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.rules.Rule;
import com.hen.phishingscanner.util.DomainUtils;
import com.hen.phishingscanner.util.UrlExtractor;

import java.util.Optional;

public class PunycodeRule implements Rule {
    @Override
    public Optional<Finding> evaluate(EmailScanRequest email) {
        var urls = UrlExtractor.extractUrls(safe(email.getBodyText()) + " " + safe(email.getBodyHtml()));
        for (String url : urls) {
            String host = DomainUtils.hostOf(url);
            if (host.startsWith("xn--")) {
                return Optional.of(new Finding("R-URL-04","HIGH",25,url));
            }
        }
        return Optional.empty();
    }

    private String safe(String s){ return s == null ? "" : s; }
}
