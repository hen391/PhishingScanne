package com.hen.phishingscanner.rules.impl;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.rules.Rule;
import com.hen.phishingscanner.util.DomainUtils;

import java.util.Optional;

public class ReplyToMismatchRule implements Rule {
    @Override
    public Optional<Finding> evaluate(EmailScanRequest email) {
        String fromDomain = DomainUtils.extractDomainFromEmailField(email.getFrom());
        String replyDomain = DomainUtils.extractDomainFromEmailField(email.getReplyTo());

        if (!replyDomain.isBlank() && !fromDomain.isBlank() && !fromDomain.equalsIgnoreCase(replyDomain)) {
            return Optional.of(new Finding("R-HDR-01","MED",20,
                    "fromDomain=" + fromDomain + ", replyToDomain=" + replyDomain));
        }
        return Optional.empty();
    }
}
