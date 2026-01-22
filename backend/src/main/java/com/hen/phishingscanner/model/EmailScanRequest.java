package com.hen.phishingscanner.model;

import jakarta.validation.constraints.NotBlank;

public class EmailScanRequest {
    @NotBlank private String from;
    private String replyTo;
    @NotBlank private String subject;
    private String bodyText;
    private String bodyHtml;

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getReplyTo() { return replyTo; }
    public void setReplyTo(String replyTo) { this.replyTo = replyTo; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBodyText() { return bodyText; }
    public void setBodyText(String bodyText) { this.bodyText = bodyText; }

    public String getBodyHtml() { return bodyHtml; }
    public void setBodyHtml(String bodyHtml) { this.bodyHtml = bodyHtml; }
}
