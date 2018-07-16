package com.miniworld.entity;

import java.util.List;

public class PreHonorTop {

    private List<SeasonInfo> seasonInfoList;

    public static class SeasonInfo {
        private Season season;
        private List<Works> works;

        public Season getSeason() {
            return season;
        }

        public void setSeason(Season season) {
            this.season = season;
        }

        public List<Works> getWorks() {
            return works;
        }

        public void setWorks(List<Works> works) {
            this.works = works;
        }
    }

    public List<SeasonInfo> getSeasonInfoList() {
        return seasonInfoList;
    }

    public void setSeasonInfoList(List<SeasonInfo> seasonInfoList) {
        this.seasonInfoList = seasonInfoList;
    }

}
