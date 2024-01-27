package com.alfin.crud_mongodb;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> home() throws IOException {
        // Lokasi file HTML di dalam direktori static
        String filePath = "static/home.html";

        // Membaca file HTML
        Resource resource = new ClassPathResource(filePath);
        if (resource.exists()) {
            byte[] fileBytes = Files.readAllBytes(Path.of(resource.getURI()));
            String htmlContent = new String(fileBytes);

            // Mengembalikan HTML dalam respons ResponseEntity
            return ResponseEntity.ok(htmlContent);
        } else {
            // Jika file tidak ditemukan, kirim respons Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
