package com.miniworld.entity;

public class Season {
    private String id;

    private String seasonName;

    private String seasonDescription;

    private String seasonKeyWords;

    private Long startTime;

    private Long endTime;

    private Long createTime;

    private Long updateTime;

    private Integer seasonLife;
    
    private Long submitStartTime;

    private Long submitEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName == null ? null : seasonName.trim();
    }

    public String getSeasonDescription() {
        return seasonDescription;
    }

    public void setSeasonDescription(String seasonDescription) {
        this.seasonDescription = seasonDescription == null ? null : seasonDescription.trim();
    }

    public String getSeasonKeyWords() {
        return seasonKeyWords;
    }

    public void setSeasonKeyWords(String seasonKeyWords) {
        this.seasonKeyWords = seasonKeyWords == null ? null : seasonKeyWords.trim();
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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

    public Integer getSeasonLife() {
        return seasonLife;
    }

    public void setSeasonLife(Integer seasonLife) {
        this.seasonLife = seasonLife;
    }
    
    public Long getSubmitStartTime() {
        return submitStartTime;
    }

    public void setSubmitStartTime(Long submitStartTime) {
        this.submitStartTime = submitStartTime;
    }

    public Long getSubmitEndTime() {
        return submitEndTime;
    }

    public void setSubmitEndTime(Long submitEndTime) {
        this.submitEndTime = submitEndTime;
    }
}