package com.miniworld.service;

import com.miniworld.common.ReMessage;
import com.miniworld.entity.Works;

import java.util.List;

public interface WorksShowService {
    ReMessage getDefaultList(Integer num);
    ReMessage getPopularList(Integer num,Integer page);
    ReMessage getNewestList(Integer num,Integer page);
    ReMessage getWeekList(Integer num,Integer page);
    Works getByUid(Integer uid);
    ReMessage incrHet(Integer wid,Integer uid);
}
