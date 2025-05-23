# 🧪 Gemma-3 OCR PoC

This is a **proof of concept (PoC)** project that demonstrates how to use a locally hosted **LLM (Large Language Model)** like `gemma3:12b` with **Ollama**, combined with a minimal **Spring Boot + Thymeleaf** web interface to extract structured content (in JSON format) directly from images.

> ⚠️ **Note**: This project is for experimentation only. It is not production-ready.

---

## 📌 Features

- Upload an image via a simple web UI
- Send the image (as base64) to a local multimodal LLM via `ollama`
- Request extraction and structuring of the visible text as valid JSON
- Display the result in a formatted block on the UI

---

## 🧱 Tech Stack

- Java 21
- Spring Boot 3.x
- Thymeleaf
- Ollama (for running LLMs locally)
- Model used: `gemma3:12b`

---

## 🚀 Getting Started

### 1. Install Ollama

See: [https://ollama.com](https://ollama.com)

```bash
# macOS/Linux
curl -fsSL https://ollama.com/install.sh | sh

# Windows
# Download and install from the website
```

### 2. Download and run your model

```bash
ollama run gemma3:12b
```
### 3. Run the Spring Boot app

```bash
./mvnw spring-boot:run
```

Then go to: http://localhost:8080

## 🧠 Prompt Logic

This is the prompt sent to the model:

Analyze the text in the provided image. Extract all readable content and present it in a well-structured JSON format that is clear, concise, and semantically organized. Use keys and nested objects when appropriate (e.g., for sections, tables, lists, labels, or titles). Do not include explanations, comments, or Markdown formatting — only return a valid and parsable JSON object that represents the meaningful content of the image.

## 📄 License

This project is licensed under the **MIT License**.  
See the [LICENSE](LICENSE) file for details.

## ✨ Disclaimer

This repository is not intended for production and is provided as-is for learning, prototyping, and experimentation purposes only.