package fr.corentinbringer.ocr_gemma3;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
public class GemmaImageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/upload")
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        String response = callGemma(base64Image);

        model.addAttribute("json", response);
        return "index";
    }

    private String callGemma(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> message = Map.of(
                "role", "user",
                "content", """
                            Analyze the text in the provided image. Extract all readable content
                            and present it in a well-structured **JSON format** that is clear, concise,
                            and semantically organized.
                            
                            Use keys and nested objects when appropriate (e.g., for sections, tables, lists, labels, or titles).
                            Do not include explanations, comments, or Markdown formatting — only return a valid and parsable JSON object
                            that represents the meaningful content of the image.
                        """,
                "images", List.of(base64Image)
        );

        Map<String, Object> request = Map.of(
                "model", "gemma3:12b",
                "messages", List.of(message),
                "stream", false
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:11434/api/chat", entity, Map.class);

        Map<?, ?> responseMessage = (Map<?, ?>) response.getBody().get("message");
        return responseMessage.get("content").toString();
    }
}
