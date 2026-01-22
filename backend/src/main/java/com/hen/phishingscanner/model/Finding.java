package com.hen.phishingscanner.model;

public class Finding {
    private String ruleId;
    private String severity; // LOW/MED/HIGH
    private int points;
    private String evidence;

    public Finding() {}

    public Finding(String ruleId, String severity, int points, String evidence) {
        this.ruleId = ruleId;
        this.severity = severity;
        this.points = points;
        this.evidence = evidence;
    }

    public String getRuleId() { return ruleId; }
    public String getSeverity() { return severity; }
    public int getPoints() { return points; }
    public String getEvidence() { return evidence; }
}
