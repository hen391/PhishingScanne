package com.hen.phishingscanner.rules;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;

import java.util.Optional;

public interface Rule {
    Optional<Finding> evaluate(EmailScanRequest email);
}
