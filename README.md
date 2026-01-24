# 🛡️ Phishing Scanner 🔎  
**Manual QA Home Task – Explainable Detection MVP**

A lightweight, explainable phishing detection system.  
Built for **manual QA review**, **local demo**, and **clear rule-based validation**.

---

## ✨ Key Features

- Rule-based scoring system (0–100)
- Clear labeling: **Safe / Suspicious / Phishing**
- Explainable findings with evidence per rule
- Ready-to-run demo scenarios for fast validation
- Simple REST API (Spring Boot)

---

## 📁 Project Structure

```md

backend/
├── src/ Spring Boot REST API (Java 17)
├── tests/ JSON fixtures (Safe / Suspicious / Phishing)
├── run_demo.ps1 Demo script for 3 scenarios
└── pom.xml

addOn/
└── placeholder/ Gmail Add-on (Apps Script – future work)
```
---

## ⚙️ Requirements

- Java 17
- Maven 3.x
- Windows (for demo script) / curl for manual tests

---

## 🚀 Running the Backend

```bash
cd backend
mvn spring-boot:run
```

---

## Health Check

```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{"status":"ok"}
```
## 🔌 Scan API
Endpoint:

```http
POST /api/scan
```

Response:
```json
{
  "label": "Phishing",
  "score": 75,
  "findings": [
    {
      "ruleId": "R-URL-01",
      "description": "URL shortener detected",
      "evidence": "bit.ly/..."
    }
  ]
}
```

**Fields**
- `label` – Safe / Suspicious / Phishing
- `score` – Integer (0–100)
- `findings` – Triggered rules with evidence

## 🧪 Quick Demo (3 Scenarios)

From the backend folder:
```
.\run_demo.ps1
```

**This runs**

✅ Safe example

⚠️ Suspicious example

🚨 Phishing example


**note:**

 Run Single Test Manually:

```bash
curl.exe -X POST http://localhost:8080/api/scan \
  -H "Content-Type: application/json" \
  --data-binary "@tests\test_phishing.json"
```

## 🧠 Detection Rules (Explainable Heuristics)

| Rule ID     | Description                                      | Points |
|-------------|--------------------------------------------------|--------|
| R-TEXT-01   | Urgent / security language detected              | 15     |
| R-HDR-01    | Reply-To domain mismatch vs From domain           | 20     |
| R-URL-01    | URL shortener detected (bit.ly, tinyurl, t.co)   | 25     |
| R-URL-02    | URL host is an IP address                         | 25     |
| R-URL-03    | Many links detected (5 or more)                   | 10     |
| R-URL-04    | Punycode domain detected (xn--)                   | 25     |

## 🧮 Scoring & Labeling

Score = sum of all triggered rules (max 100)
| Score Range | Label        |
|-------------|--------------|
| 0–29        | Safe         |
| 30–59       | Suspicious   |
| 60–100      | Phishing     |

## 👩‍💻 Author

**Hen Ben Gigi**  

Built as part of a technical home assignment
