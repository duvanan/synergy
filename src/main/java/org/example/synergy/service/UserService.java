package org.example.synergy.service;

import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearch;

import java.util.List;

public interface UserService {

    int countTotalUsers(UserSearch search);

    List<UserListDTO> getUserList(UserSearch search);
}
