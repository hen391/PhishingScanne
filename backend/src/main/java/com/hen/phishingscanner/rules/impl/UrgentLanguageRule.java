package com.hen.phishingscanner.rules.impl;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.rules.Rule;

import java.util.List;
import java.util.Optional;

public class UrgentLanguageRule implements Rule {
    private static final List<String> KEYWORDS = List.of(
            "urgent","act now","verify","suspended","immediately","password","limited time"
    );

    @Override
    public Optional<Finding> evaluate(EmailScanRequest email) {
        String text = (safe(email.getSubject()) + " " + safe(email.getBodyText()) + " " + safe(email.getBodyHtml()))
                .toLowerCase();
        for (String k : KEYWORDS) {
            if (text.contains(k)) {
                return Optional.of(new Finding("R-TEXT-01","MED",15,"keyword=" + k));
            }
        }
        return Optional.empty();
    }

    private String safe(String s){ return s == null ? "" : s; }
}
