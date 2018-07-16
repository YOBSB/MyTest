package com.miniworld.comparator;

import com.miniworld.entity.Works;

import java.util.Comparator;

/**
 * 最新作品排序方法，当作品的createTime为null时，抛出null异常，
 * 在往集合中添加集合时，需要捕捉该异常而且要做相应的处理
 */
public class WorksNewestComparator implements Comparator<Works> {

    @Override
    public int compare(Works o1, Works o2) {
        if (o1 == null || o1.getCreateTime() == null) throw new NullPointerException();
        if (o2 == null || o2.getCreateTime() == null) throw new NullPointerException();
        if(o1.equals(o2)) return 0;
        if(o2.getCreateTime().equals(o1.getCreateTime())) return 1;
        return (int) (o2.getCreateTime()- o1.getCreateTime());
    }
}
