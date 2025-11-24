package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.DepartmentTreeDTO;
import org.example.synergy.service.impl.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/tree")
    public ResponseEntity<List<DepartmentTreeDTO>> getDepartmentTree(
            @RequestParam(required = false) String name
    ) {
        return ResponseEntity.ok(departmentService.getDepartmentTree(name));
    }
}
