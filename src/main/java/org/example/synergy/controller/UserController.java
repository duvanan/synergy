package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;
import org.example.synergy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/filter")
    public ResponseEntity<?> filterUsers(
            @RequestParam(required = false) String organizationCode,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String regionCode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
                userService.filterUsers(organizationCode, departmentId, status, type, regionCode, page, size)
        );
    }
}