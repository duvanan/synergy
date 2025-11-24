package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.DepartmentTreeDTO;
import org.example.synergy.entity.Department;
import org.example.synergy.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentTreeDTO> getDepartmentTree(String nameFilter) {

        List<Department> departments = departmentRepository.findByNameContaining(nameFilter);

        // Map code → DepartmentTreeDTO
        Map<String, DepartmentTreeDTO> map = new HashMap<>();

        departments.forEach(d -> map.put(
                d.getCode(),
                new DepartmentTreeDTO(
                        d.getId(),
                        d.getCode(),
                        d.getName(),
                        d.getRegionCode(),
                        d.getRegionName(),
                        d.getRegionFullname(),
                        d.getDescription(),
                        d.getOrganizationName(),
                        d.getOrganizationCode(),
                        d.getIsDeleted(),
                        d.getIsActive(),
                        d.getParentCode(),
                        new ArrayList<>()
                )
        ));

        List<DepartmentTreeDTO> roots = new ArrayList<>();

        // Build cây
        for (DepartmentTreeDTO node : map.values()) {
            if (node.getParentCode() == null || !map.containsKey(node.getParentCode())) {
                roots.add(node);
            } else {
                map.get(node.getParentCode()).getChildren().add(node);
            }
        }

        return roots;
    }
}
