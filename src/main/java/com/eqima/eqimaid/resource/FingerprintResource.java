package com.eqima.eqimaid.resource;

import com.eqima.eqimaid.dto.FingerprintListDto;
import com.eqima.eqimaid.mapper.FingerprintMapper;
import com.eqima.eqimaid.model.Response;
import com.eqima.eqimaid.service.FingerprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/{filename}", produces = IMAGE_JPEG_VALUE)
    public byte[] getFingerprintImage(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(
                "E:/Projects/AI & Data science/facial-recognition/biometric_recognition/data/fingerprints/1/"
                        + filename));
    }
}
