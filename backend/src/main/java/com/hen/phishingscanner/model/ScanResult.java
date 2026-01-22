package com.hen.phishingscanner.model;

import java.util.List;

public class ScanResult {
    private String label; // Safe/Suspicious/Phishing
    private int score;    // 0-100
    private List<Finding> findings;

    public ScanResult(String label, int score, List<Finding> findings) {
        this.label = label;
        this.score = score;
        this.findings = findings;
    }

    public String getLabel() { return label; }
    public int getScore() { return score; }
    public List<Finding> getFindings() { return findings; }
}
