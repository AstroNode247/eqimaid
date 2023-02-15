package com.eqima.eqimaid.resource;

import com.eqima.eqimaid.dto.FingerprintListDto;
import com.eqima.eqimaid.mapper.FingerprintMapper;
import com.eqima.eqimaid.model.Response;
import com.eqima.eqimaid.service.FingerprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/api/v1/fingerprint")
@RequiredArgsConstructor
public class FingerprintResource {
    private final FingerprintService fingerprintService;

    @GetMapping
    public ResponseEntity<Response> getFingerprints() {
        List<FingerprintListDto> fingerprintResponse = fingerprintService.list().stream()
                .map(FingerprintMapper.mapper::list).collect(Collectors.toList());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("fingerprints", fingerprintResponse))
                        .message("Fingerprint fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @GetMapping(path = "/{ownerId}/{filename}", produces = IMAGE_JPEG_VALUE)
    public byte[] getFingerprintImage(@PathVariable String ownerId, @PathVariable("filename") String filename)
            throws IOException {
        return Files.readAllBytes(Paths.get(
                "E:/Projects/Web/eqimaid/data/" + ownerId + "/" + filename));
    }

    @PostMapping
    public ResponseEntity<Response> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = fingerprintService.uploadImage(file);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("Fingerprint", uploadImage))
                        .message("New fingerprint uploaded")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

//    @GetMapping(value = "{ownerId}/{filename}")
//    public ResponseEntity<?> downloadImage(@PathVariable String ownerId, @PathVariable String filename)
//            throws IOException {
//        byte[] imageData = fingerprintService.downloadImage(filename);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/jpeg"))
//                .body(imageData);
//    }
}
