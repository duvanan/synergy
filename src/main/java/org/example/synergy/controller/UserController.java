package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;
import org.example.synergy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/search")
    public ResponseEntity<List<UserListDTO>> getUserList(@RequestBody UserSearch search) {
        List<UserListDTO> result = userService.getUserList(search);
        return ResponseEntity.ok(result);
    }
}