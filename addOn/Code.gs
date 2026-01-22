const BACKEND_URL = "https://unpursuant-tranquil-bertie.ngrok-free.dev";

function buildAddOn(e) {
  return buildHomeCard_("Ready to scan", "Open an email and click Scan", "🔎");
}

function buildHomeCard_(title, subtitle, iconEmoji) {
  const cs = CardService_();

  const header = cs.newCardHeader()
    .setTitle(`Phishing Scanner ${iconEmoji}`)
    .setSubtitle(subtitle);

  const section = cs.newCardSection()
    .addWidget(
      cs.newKeyValue()
        .setTopLabel("Status")
        .setContent(title)
    )
    .addWidget(
      cs.newTextButton()
        .setText("✨ Scan for Phishing ✨")
        .setOnClickAction(cs.newAction().setFunctionName("scanCurrentEmail"))
        .setTextButtonStyle(cs.TextButtonStyle.FILLED)
    );

  return cs.newCardBuilder()
    .setHeader(header)
    .addSection(section)
    .build();
}

function scanCurrentEmail(e) {
  const cs = CardService_();

  try {
    const msg = getCurrentMessage_(e);
    const payload = {
      from: (msg.getFrom && msg.getFrom()) ? msg.getFrom() : "",
      replyTo: "", // GmailApp message API doesn't always expose reply-to easily
      subject: (msg.getSubject && msg.getSubject()) ? msg.getSubject() : "",
      bodyText: safeText_(msg.getPlainBody ? msg.getPlainBody() : ""),
      bodyHtml: "" // optional
    };

    const result = callBackendScan_(payload);

    const label = result.label || "Unknown";
    const score = (typeof result.score === "number") ? result.score : -1;
    const findings = Array.isArray(result.findings) ? result.findings : [];

    const badge = badgeEmoji_(label);
    const summary = summaryText_(label);
    const helper = helperText_(label);

    const header = cs.newCardHeader()
      .setTitle(`${badge} ${summary}`)
      .setSubtitle(`Label: ${label} | Score: ${score}`);

    const section = cs.newCardSection()
      .addWidget(
        cs.newKeyValue()
          .setTopLabel("Classification")
          .setContent(label)
      )
      .addWidget(
        cs.newKeyValue()
          .setTopLabel("Score")
          .setContent(`${score} / 100`)
      )
      .addWidget(
        cs.newTextParagraph().setText(escape_(helper))
      );

    if (findings.length === 0) {
      section.addWidget(
        cs.newTextParagraph().setText("No suspicious indicators detected ✅")
      );
    } else {
      section.addWidget(
        cs.newTextParagraph().setText("<b>Why it was flagged ❔</b>")
      );

      findings.forEach(f => {
        const ruleId = f.ruleId || "RULE";
        const severity = f.severity || "";
        const points = (typeof f.points === "number") ? f.points : (f.points || 0);
        const evidence = prettyEvidence_(ruleId, f.evidence);

        section.addWidget(
          cs.newKeyValue()
            .setTopLabel(`${sevIcon_(severity)} ${ruleId} • ${String(severity).toUpperCase()} • +${points}`)
            .setContent(escape_(String(evidence || "")))
        );
      });
    }

    section.addWidget(
      cs.newTextButton()
        .setText("✨ Scan another email ✨")
        .setOnClickAction(cs.newAction().setFunctionName("scanCurrentEmail"))
        .setTextButtonStyle(cs.TextButtonStyle.TEXT)
    );

    return cs.newCardBuilder().setHeader(header).addSection(section).build();

  } catch (err) {
    return cs.newCardBuilder()
      .setHeader(cs.newCardHeader().setTitle("Scan Error ⚠️"))
      .addSection(
        cs.newCardSection()
          .addWidget(cs.newTextParagraph().setText("Could not scan email ❌"))
          .addWidget(cs.newTextParagraph().setText(`<code>${escape_(String(err))}</code>`))
      )
      .build();
  }
}

function callBackendScan_(payload) {
  const url = `${BACKEND_URL}/api/scan`;

  const res = UrlFetchApp.fetch(url, {
    method: "post",
    contentType: "application/json",
    payload: JSON.stringify(payload),
    muteHttpExceptions: true
  });

  const code = res.getResponseCode();
  const text = res.getContentText();

  if (code < 200 || code >= 300) {
    throw new Error(`Backend error ${code}: ${text}`);
  }
  return JSON.parse(text);
}

function badgeEmoji_(label) {
  switch (String(label).toLowerCase()) {
    case "safe": return "🟢";
    case "suspicious": return "🟡";
    case "phishing": return "🔴";
    default: return "🔵";
  }
}

function summaryText_(label) {
  switch (String(label).toLowerCase()) {
    case "safe": return "Looks safe";
    case "suspicious": return "Needs review";
    case "phishing": return "High risk";
    default: return "Scan Result";
  }
}

function helperText_(label) {
  switch (String(label).toLowerCase()) {
    case "safe": return "No immediate red flags detected.";
    case "suspicious": return "Some indicators found — review before clicking.";
    case "phishing": return "Avoid clicking links — verify the sender first.";
    default: return "Result ready.";
  }
}

function sevIcon_(sev) {
  switch (String(sev).toUpperCase()) {
    case "HIGH": return "🔴";
    case "MED": return "🟠";
    case "LOW": return "🟡";
    default: return "🔵";
  }
}

function prettyEvidence_(ruleId, evidence) {
  const ev = String(evidence || "");
  if (ruleId === "R-URL-03" && ev.startsWith("linksCount=")) {
    const n = ev.split("=")[1];
    return `Many links (${n})`;
  }
  return ev;
}

function safeText_(s) {
  if (!s) return "";
  return String(s).slice(0, 8000);
}

function escape_(s) {
  return String(s)
    .replace(/&/g, "&amp;")
    .replace(/</g, "&lt;")
    .replace(/>/g, "&gt;");
}

function getCurrentMessage_(e) {
  const messageId = e && e.gmail && e.gmail.messageId ? e.gmail.messageId : null;
  if (!messageId) throw new Error("No message context. Open an email and try again.");
  return GmailApp.getMessageById(messageId);
}

function CardService_() {
  return CardService;
}
