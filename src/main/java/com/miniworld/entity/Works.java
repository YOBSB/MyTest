package com.miniworld.entity;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Works implements Serializable {
    private Integer worksId;
    private Integer miniId;
    private String name;
    private String qq;
    private String mail;
    private String phone;
    private String teamMates;
    private String worksMapId;//地图id
    private String worksName;
    private String worksIntroduce;
    private String worksMainImage;//主图片
    private String mainSmallImage;
    private String image1;
    private String imageSmall1;
    private String image2;
    private String imageSmall2;
    private String image3;
    private String imageSmall3;
    private Integer worksState;
    private Long submissionTime;
    private Long createTime;
    private Long updateTime;
    private Integer worksHeat; //总的点击数
    private Integer weekHeat;  //单周点击数
    private Integer weekHeatTime; //单周点击数的周数
    private Integer rank; //荣誉榜单位置，仅在返回荣誉榜单信息时会用到，works表中不存在

    public Works() {
    }


    /**
     * 构造函数,测试用，填入迷你id、作品id，热度
     *
     * @return
     */
    public Works(Integer miniId) {
        this.miniId = miniId;
    }

    /**
     * 构造函数,测试用，填入迷你id、作品id，热度
     *
     * @return
     */
    public Works(Integer miniId, Integer worksId, Integer worksHeat, Long timestamp, Integer weekHeatTime) {
        this.miniId = miniId;
        this.worksId = worksId;
        this.worksHeat = worksHeat;
        this.createTime = timestamp;
        this.weekHeatTime = weekHeatTime;
    }

    /**
     * 构造函数,测试用，填入迷你id，作品id，总热度，创建时间
     *
     * @return
     */
    public Works(Integer miniId, Integer worksId, Integer worksHeat, Long timestamp, Integer worksState, Integer weekHeat, Integer weekHeatTime) {
        this.miniId = miniId;
        this.worksId = worksId;
        this.worksHeat = worksHeat;
        this.createTime = timestamp;
        this.worksState = worksState;
        this.weekHeat = weekHeat;
        this.weekHeatTime = weekHeatTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTeamMates() {
        return teamMates;
    }

    public void setTeamMates(String teamMates) {
        this.teamMates = teamMates;
    }

    public String getWorksMapId() {
        return worksMapId;
    }


    public void setWorksMapId(String worksMapId) {
        this.worksMapId = worksMapId;
    }

    public String getWorksName() {
        return worksName;
    }

    public void setWorksName(String worksName) {
        this.worksName = worksName;
    }

    public String getWorksIntroduce() {
        return worksIntroduce;
    }

    public void setWorksIntroduce(String worksIntroduce) {
        this.worksIntroduce = worksIntroduce;
    }

    public String getWorksMainImage() {
        return worksMainImage;
    }


    public void setWorksMainImage(String worksMainImage) {
        this.worksMainImage = worksMainImage;
    }

    public String getMainSmallImage() {
        return mainSmallImage;
    }


    public void setMainSmallImage(String mainSmallImage) {
        this.mainSmallImage = mainSmallImage;
    }


    public String getImage1() {
        return image1;
    }


    public void setImage1(String image1) {
        this.image1 = image1;
    }


    public String getImageSmall1() {
        return imageSmall1;
    }


    public void setImageSmall1(String imageSmall1) {
        this.imageSmall1 = imageSmall1;
    }


    public String getImage2() {
        return image2;
    }


    public void setImage2(String image2) {
        this.image2 = image2;
    }


    public String getImageSmall2() {
        return imageSmall2;
    }


    public void setImageSmall2(String imageSmall2) {
        this.imageSmall2 = imageSmall2;
    }


    public String getImage3() {
        return image3;
    }


    public void setImage3(String image3) {
        this.image3 = image3;
    }


    public String getImageSmall3() {
        return imageSmall3;
    }


    public void setImageSmall3(String imageSmall3) {
        this.imageSmall3 = imageSmall3;
    }


    public Integer getWorksState() {
        return worksState;
    }

    public void setWorksState(Integer worksState) {
        this.worksState = worksState;
    }

    public Long getSubmissionTime() {
        return submissionTime;
    }


    public void setSubmissionTime(Long submissionTime) {
        this.submissionTime = submissionTime;
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


    public Integer getWorksHeat() {
        return worksHeat;
    }

    public void setWorksHeat(Integer worksHeat) {
        this.worksHeat = worksHeat;
    }

    public Integer getWeekHeat() {
        return weekHeat;
    }

    public void setWeekHeat(Integer weekHeat) {
        this.weekHeat = weekHeat;
    }

    public Integer getWeekHeatTime() {
        return weekHeatTime;
    }

    public void setWeekHeatTime(Integer weekHeatTime) {
        this.weekHeatTime = weekHeatTime;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Works [worksId=" + worksId + ", miniId=" + miniId + ", name=" + name + ", qq=" + qq + ", mail=" + mail
                + ", phone=" + phone + ", teamMates=" + teamMates + ", worksMapId=" + worksMapId + ", worksName="
                + worksName + ", worksIntroduce=" + worksIntroduce + ", worksMainImage=" + worksMainImage
                + ", mainSmallImage=" + mainSmallImage + ", image1=" + image1 + ", imageSmall1=" + imageSmall1
                + ", image2=" + image2 + ", imageSmall2=" + imageSmall2 + ", image3=" + image3 + ", imageSmall3="
                + imageSmall3 + ", worksState=" + worksState + ", submissionTime=" + submissionTime + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", worksHeat=" + worksHeat + ", weekHeat=" + weekHeat
                + ", weekHeatTime=" + weekHeatTime + ", rank=" + rank + "]\n";
    }


    /**
     * 重写 equals
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Works)) return false;
        Works bean = (Works) o;
        return (this.getMiniId().equals(bean.getMiniId()) || this.getWorksId().equals(bean.getWorksId()));
    }

    /**
     * 重写 hashCode 的计算方法
     * 根据所有属性进行 迭代计算，避免重复
     * 计算 hashCode 时 计算因子 31 见得很多，是一个质数，不能再被除
     *
     * @return
     */
    @Override
    public int hashCode() {
        //调用 String 的 hashCode(), 唯一表示一个字符串内容
        int result = getMiniId().hashCode();
        //乘以 31, 再加上 worksId
        result = 31 * result + getWorksId();
        return result;
    }


}
