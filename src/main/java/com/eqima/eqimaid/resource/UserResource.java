package com.eqima.eqimaid.resource;

import com.eqima.eqimaid.model.Response;
import com.eqima.eqimaid.model.User;
import com.eqima.eqimaid.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.time.LocalDateTime.now;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Response> getUsers() {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("users", userService.list()))
                        .message("Users fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response> getUser(@PathVariable Integer uid) {
        User user = userService.get(uid);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", user))
                        .message("User " + user.getUid() + " fetched")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PostMapping
    public ResponseEntity<Response> registerUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userService.create(user)))
                        .message("New user created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build()
        );
    }

    @PutMapping("/{uid}")
    public ResponseEntity<Response> updateUser(@RequestBody @Valid User user, @PathVariable Integer uid) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("user", userService.updateByUid(user, uid)))
                        .message("User " + uid + " updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @DeleteMapping("/{uid}")
    public ResponseEntity<Response> deleteUser(@PathVariable Integer uid) {
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
