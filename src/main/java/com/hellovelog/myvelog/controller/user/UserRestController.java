package com.hellovelog.myvelog.controller.user;

import com.hellovelog.myvelog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    // 서비스 단으로 로직 이동 필요
    private final UserService userService;

    @PostMapping("/checkUsername")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        boolean exists = userService.checkUsernameExists(username);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean exists = userService.checkEmailExists(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }



}
