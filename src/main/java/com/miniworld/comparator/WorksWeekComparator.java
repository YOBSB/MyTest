package com.miniworld.comparator;

import com.miniworld.entity.Works;

import java.util.Comparator;

/**
 * 作品单周点击数排序方法，当作品weekHeat为null时，抛出null异常，
 * 在往集合中添加集合时，需要捕捉该异常而且要做相应的处理
 */
public class WorksWeekComparator implements Comparator<Works> {
    @Override
    public int compare(Works o1, Works o2) {
        if (o1 == null || o1.getWeekHeat() == null) throw new NullPointerException();
        if (o2 == null || o2.getWeekHeatTime() == null) throw new NullPointerException();
        if(o1.equals(o2)) return 0;
        if(o2.getWeekHeat().equals(o1.getWeekHeat())) return 1;
        return o2.getWeekHeat() - o1.getWeekHeat();
    }
}
