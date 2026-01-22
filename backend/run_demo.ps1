function Run-Scan($title, $file) {
  Write-Host ""
  Write-Host "=============================="
  Write-Host $title
  Write-Host "=============================="
  $resp = curl.exe -s -X POST "http://localhost:8080/api/scan" -H "Content-Type: application/json" --data-binary "@$file"
  $resp | ConvertFrom-Json | ConvertTo-Json -Depth 6
}

Run-Scan "SAFE (expected: Safe, score low)" "tests\test_safe.json"
Run-Scan "SUSPICIOUS (expected: Suspicious, score mid)" "tests\test_suspicious.json"
Run-Scan "PHISHING (expected: Phishing, score high)" "tests\test_phishing.json"
