function doGet(e) {
  const p = e.parameter;
  const result = LanguageApp.translate(p.text, "", p.lang);
  return ContentService.createTextOutput(result);
}