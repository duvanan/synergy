package org.example.synergy.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;
import org.example.synergy.dto.response.user.UserListResponse;
import org.example.synergy.openapi.Error400Response;
import org.example.synergy.security.AuthoritiesConstants;
//import org.example.synergy.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "User Management API", description = "API for managing user")
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Validated
public class UserController {

//    private final UserService userService;

//    @Operation(summary = "Get User List")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "get user list result", content = {
//                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserListResponse.class))
//            }),
//            @ApiResponse(responseCode = "400", description = "Missing required parameters, invalid values, or other malformed requests.", content = {
//                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Error400Response.class))
//            })
//    })
//    @PreAuthorize("hasAuthority('" + AuthoritiesConstants.ADMIN + "')")
//    @PostMapping("/search")
//    public ResponseEntity<?> getUserList(@RequestBody @Valid UserSearch search) {
//        List<UserListDTO> data = userService.getUserList(search);
//        int total = userService.countTotalUsers(search);
//        return ResponseEntity.ok(new UserListResponse(total, data));
//    }
}
