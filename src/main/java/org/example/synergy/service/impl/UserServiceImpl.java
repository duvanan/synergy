package org.example.synergy.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.synergy.contants.Constants;
import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;
import org.example.synergy.dto.request.user.UserSearchCondition;
import org.example.synergy.dto.response.PaginationInfo;
import org.example.synergy.repository.UserRepository;
import org.example.synergy.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserListDTO> getUserList(UserSearch search) {
        UserSearchCondition condition = UserSearchCondition.builder()
                .searchKeyword(search.getKeyword())
                .collate(Constants.UTF8MB4_UNICODE_520_CI)
                .status(search.getStatus())
                .departmentIds(search.getDepartmentIds())
                .userTypes(search.getUserTypes())
                .regionCodes(search.getRegionCodes())
                .paginationInfo(new PaginationInfo(search.getPageNumber(), search.getPageSize()))
                .build();

        return userRepository.getUserList(condition);
    }

    @Override
    @Transactional(readOnly = true)
    public int countTotalUsers(UserSearch search) {
        UserSearchCondition condition = UserSearchCondition.builder()
                .searchKeyword(search.getKeyword())
                .collate(Constants.UTF8MB4_UNICODE_520_CI)
                .status(search.getStatus())
                .departmentIds(search.getDepartmentIds())
                .userTypes(search.getUserTypes())
                .regionCodes(search.getRegionCodes())
                .build();

        return userRepository.countTotalUsers(condition);
    }
}
