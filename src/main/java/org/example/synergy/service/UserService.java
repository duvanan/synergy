package org.example.synergy.service;

import org.example.synergy.dto.UserDepartmentDTO;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    int countTotalUsers(UserSearch search);

    List<UserListDTO> getUserList(UserSearch search);

    Page<UserDepartmentDTO> filterUsers(
            String organizationCode,
            Long departmentId,
            Boolean status,
            Integer type,
            String regionCode,
            int page,
            int size
    );
}
