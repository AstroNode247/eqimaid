package com.eqima.eqimaid.resource;

import com.eqima.eqimaid.dto.ExistingUserDto;
import com.eqima.eqimaid.dto.FingerprintDto;
import com.eqima.eqimaid.dto.UserDto;
import com.eqima.eqimaid.mapper.FingerprintMapper;
import com.eqima.eqimaid.model.Fingerprint;
import com.eqima.eqimaid.model.Response;
import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.service.FingerprintService;
import com.eqima.eqimaid.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserResource {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final FingerprintService fingerprintService;

    @GetMapping
    public ResponseEntity<Response> getUsers() {
        List<UserDto> usersResponse = userService.list().stream().map(user ->
                modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("users", usersResponse))
                        .message("Users fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response> getUser(@PathVariable String uid) {
        User user = userService.get(uid);
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userResponse))
                        .message("User " + userResponse.getUid() + " fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Response> registerUser(@RequestBody @Valid UserDto userDto) {
        User userRequest = modelMapper.map(userDto, User.class);
        User user = userService.create(userRequest);
        UserDto userResponse = modelMapper.map(user, UserDto.class);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userResponse))
                        .message("New user created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

//    @PostMapping("/{ownerId}/fingerprint")
//    public ResponseEntity<Response> addFingerprint(@PathVariable String ownerId,
//                                                   @RequestBody @Valid FingerprintDto fingerprintDto) {
//        Fingerprint fingerprintRequest = FingerprintMapper.mapper.toFingerprint(fingerprintDto);
//        Fingerprint fingerprint = fingerprintService.addFingerprint(ownerId, fingerprintRequest);
//        FingerprintDto fingerprintResponse = FingerprintMapper.mapper.toFingerprintDto(ownerId, fingerprint);
//
//        return ResponseEntity.ok(
//                Response.builder()
//                        .timeStamp(now())
//                        .data(Map.of("fingerprint", fingerprintResponse))
//                        .message("New fingerprint created added for user : " + ownerId)
//                        .status(HttpStatus.CREATED)
//                        .statusCode(HttpStatus.CREATED.value())
//                        .build()
//        );
//    }
@PostMapping("/{ownerId}/fingerprint")
public ResponseEntity<Response> addFingerprint(@PathVariable String ownerId) throws IOException {
    Fingerprint fingerprint = fingerprintService.addFingerprint(ownerId);
    FingerprintDto fingerprintResponse = FingerprintMapper.mapper.toFingerprintDto(ownerId, fingerprint);

    return ResponseEntity.ok(
            Response.builder()
                    .timeStamp(now())
                    .data(Map.of("fingerprint", fingerprintResponse))
                    .message("New fingerprint created added for user : " + ownerId)
                    .status(HttpStatus.CREATED)
                    .statusCode(HttpStatus.CREATED.value())
                    .build()
    );
}

    @PutMapping("/{uid}")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid ExistingUserDto existingUserDto,
                                               @PathVariable String uid) {
        User userRequest = modelMapper.map(existingUserDto, User.class);
        User user = userService.updateByUid(userRequest, uid);
        ExistingUserDto userResponse = modelMapper.map(user, ExistingUserDto.class);

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userResponse))
                        .message("User " + uid + " updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response> deleteUser(@PathVariable String uid) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userService.delete(uid)))
                        .message("User " + uid + " deleted")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

}
