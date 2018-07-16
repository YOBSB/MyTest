package com.miniworld.entity;

public class AdminUser {
    private Integer adminId;

    private String adminLoginName;

    private String adminPassword;

    private String adminRealName;

    private Integer adminRoleId;

    private String keyChain;

    private Long createTime;

    private Long updateTime;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminLoginName() {
        return adminLoginName;
    }

    public void setAdminLoginName(String adminLoginName) {
        this.adminLoginName = adminLoginName == null ? null : adminLoginName.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getAdminRealName() {
        return adminRealName;
    }

    public void setAdminRealName(String adminRealName) {
        this.adminRealName = adminRealName == null ? null : adminRealName.trim();
    }

    public Integer getAdminRoleId() {
        return adminRoleId;
    }

    public void setAdminRoleId(Integer adminRoleId) {
        this.adminRoleId = adminRoleId;
    }

    public String getKeyChain() {
        return keyChain;
    }

    public void setKeyChain(String keyChain) {
        this.keyChain = keyChain == null ? null : keyChain.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

	@Override
	public String toString() {
		return "AdminUser [adminId=" + adminId + ", adminLoginName=" + adminLoginName + ", adminPassword="
				+ adminPassword + ", adminRealName=" + adminRealName + ", adminRoleId=" + adminRoleId + ", keyChain="
				+ keyChain + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
    
    
}