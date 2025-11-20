package org.example.synergy.job;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.example.synergy.entity.User;
import org.example.synergy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SyncUser {
    private static final Logger logger = LoggerFactory.getLogger(SyncUser.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String STAFF_API_URL = "http://10.120.10.24:8199/api/v1/staff";
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public SyncUser(ObjectMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedRate = 1000)
    @Transactional// runs every hour
    public void syncStaff() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(STAFF_API_URL, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode dataNode = root.get("data");
            List<User> users = mapper.readValue(
                    dataNode.toString(),
                    new TypeReference<>() {
                    }
            );
            Map<String, List<User>> mapUserCode = userRepository.findAll().stream()
                    .filter(i -> ObjectUtils.isNotEmpty(i.getUserCode()))
                    .collect(Collectors.groupingBy(User::getUserCode));
            users.forEach(user -> {
                List<User> existingUsers = mapUserCode.get(user.getUserCode());
                if (ObjectUtils.isEmpty(existingUsers)) {
                    // New user
                    userRepository.save(user);
                } else {
                    // Existing user, update fields as necessary
                    User existingUser = existingUsers.get(0);
                    existingUser.setFullName(user.getFullName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setCode(user.getCode());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    existingUser.setJobTitleId(user.getJobTitleId());
                    existingUser.setDepartmentId(user.getDepartmentId());
                    existingUser.setJti(user.getJti());
                    existingUser.setStatus(user.getStatus());
                    existingUser.setIsAssign(user.getIsAssign());
                    existingUser.setIsDeleted(user.getIsDeleted());
                    existingUser.setType(user.getType());
                    existingUser.setUserCode(user.getUserCode());
                    existingUser.setGender(user.getGender());
                    existingUser.setOrganizationCode(user.getOrganizationCode());
                    existingUser.setStaffRole(user.getStaffRole());
                    existingUser.setStaffLevel(user.getStaffLevel());
                    existingUser.setIsActive(user.getIsActive());
                    existingUser.setUserKey(user.getUserKey());
                    existingUser.setDirectoryId(user.getDirectoryId());
                    existingUser.setJiraActive(user.getJiraActive());
                    existingUser.setStaffPosition(user.getStaffPosition());
                    existingUser.setOrganizationId(user.getOrganizationId());
                    existingUser.setPositionId(user.getPositionId());
                    userRepository.save(existingUser);
                }
            });
            logger.info("Fetched staff: {}", users);
            // TODO: Add your sync logic here (e.g., save to DB)
        } catch (Exception e) {
            logger.error("Error syncing staff", e);
        }
    }
}
