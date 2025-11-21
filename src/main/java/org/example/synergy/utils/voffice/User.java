//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.synergy.utils.voffice;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String rsaPublicKey;
    private String aesKey;
    private String sessionId;
    private long loginTime;
    private Long userId;
    private List<VhrOrgEntity> listAssistantVhrOrg;
    private List<VhrOrgEntity> listManagementVhrOrg;
    private List<Long> listAssistantVhrOrgId;
    private List<Long> listManagementVhrOrgId;

    public User(String username, String password, String rsaPublicKey, String aesKey, String sessionId, Long loginTime) {
        this.username = username;
        this.password = password;
        this.rsaPublicKey = rsaPublicKey;
        this.aesKey = aesKey;
        this.sessionId = sessionId;
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRsaPublicKey() {
        return this.rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    public List<VhrOrgEntity> getListAssistantVhrOrg() {
        return this.listAssistantVhrOrg;
    }

    public void setListAssistantVhrOrg(List<VhrOrgEntity> listAssistantVhrOrg) {
        this.listAssistantVhrOrg = listAssistantVhrOrg;
    }

    public List<VhrOrgEntity> getListManagementVhrOrg() {
        return this.listManagementVhrOrg;
    }

    public void setListManagementVhrOrg(List<VhrOrgEntity> listManagementVhrOrg) {
        this.listManagementVhrOrg = listManagementVhrOrg;
    }

    public List<Long> getListAssistantVhrOrgId() {
        return this.listAssistantVhrOrgId;
    }

    public void setListAssistantVhrOrgId(List<Long> listAssistantVhrOrgId) {
        this.listAssistantVhrOrgId = listAssistantVhrOrgId;
    }

    public List<Long> getListManagementVhrOrgId() {
        return this.listManagementVhrOrgId;
    }

    public void setListManagementVhrOrgId(List<Long> listManagementVhrOrgId) {
        this.listManagementVhrOrgId = listManagementVhrOrgId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
