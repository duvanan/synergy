/*
 * VIETTEL SOFTWARE (VTIT)
 *
 * COPYRIGHT NOTICE:
 * All content including source code, documentation, and other information is the property of RFIAS.
 * Unauthorized use, disclosure, reproduction, or distribution is strictly prohibited and may be unlawful.
 * Permission for any use must be obtained in writing from RFIAS.
 */
package org.example.synergy.repository;


import org.example.synergy.dto.UserListDTO;
import org.example.synergy.dto.request.user.UserSearchCondition;

import java.util.List;

public interface UserRepositoryCustom {

    List<UserListDTO> getUserList(UserSearchCondition condition);

    int countTotalUsers(UserSearchCondition condition);
}
