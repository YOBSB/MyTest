package com.miniworld.entity;


public class WorksHonor {
    private Integer rank;
    private Integer worksId;
    private Integer miniId;
    private Long createTime;
    private Long updateTime;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getWorksId() {
        return worksId;
    }

    public void setWorksId(Integer worksId) {
        this.worksId = worksId;
    }

    public Integer getMiniId() {
        return miniId;
    }

    public void setMiniId(Integer miniId) {
        this.miniId = miniId;
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
        return "WorksHonor [worksId=" + worksId + ", miniId=" + miniId + ", rank=" + rank + ", createTime=" + createTime + ", updateTime=" + updateTime +"]\n";
    }
}
