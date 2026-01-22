package com.hen.phishingscanner.service;

import com.hen.phishingscanner.model.EmailScanRequest;
import com.hen.phishingscanner.model.Finding;
import com.hen.phishingscanner.model.ScanResult;
import com.hen.phishingscanner.rules.Rule;
import com.hen.phishingscanner.rules.impl.IpUrlRule;
import com.hen.phishingscanner.rules.impl.ManyLinksRule;
import com.hen.phishingscanner.rules.impl.PunycodeRule;
import com.hen.phishingscanner.rules.impl.ReplyToMismatchRule;
import com.hen.phishingscanner.rules.impl.UrgentLanguageRule;
import com.hen.phishingscanner.rules.impl.UrlShortenerRule;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScanService {

    private final List<Rule> rules = List.of(        new UrgentLanguageRule(),
        new ReplyToMismatchRule(),
        new UrlShortenerRule(),
        new IpUrlRule(),
        new ManyLinksRule(),
        new PunycodeRule()
);

    public ScanResult scan(EmailScanRequest email) {
        List<Finding> findings = new ArrayList<>();
        int score = 0;

        for (Rule r : rules) {
            r.evaluate(email).ifPresent(findings::add);
        }
        for (Finding f : findings) score += f.getPoints();
        score = Math.min(100, score);

        String label = (score >= 60) ? "Phishing" : (score >= 30) ? "Suspicious" : "Safe";
        return new ScanResult(label, score, findings);
    }
}
