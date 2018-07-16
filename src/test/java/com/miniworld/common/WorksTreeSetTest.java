package com.miniworld.common;

import com.miniworld.BaseTest;
import com.miniworld.comparator.WorksHeatComparator;
import com.miniworld.comparator.WorksNewestComparator;
import com.miniworld.comparator.WorksWeekComparator;
import com.miniworld.entity.Works;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.*;

public class WorksTreeSetTest<T> extends BaseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public WorksTreeSet<Works> iniPopularTreeSet(int maxSize, Comparator<? super Works> worksComparable) {
        WorksTreeSet<Works> worksTreeSet = new WorksTreeSet<>(maxSize, worksComparable);
        //单线程的添加，查看
        Works w1 = new Works(1, 1, 1, 1529412701L, null);
        Works w2 = new Works(2, 2, 2, 1529412702L, null);
        Works w3 = new Works(3, 3, 3, 1529412703L, null);
        Works w4 = new Works(4, 4, 4, 1529412704L, null);
        Works w5 = new Works(5, 5, 5, 1529412705L, null);
        Works w6 = new Works(6, 6, 6, 1529412706L, null);
        Works w7 = new Works(7, 7, 7, 1529412707L, null);
        Works w8 = new Works(8, 8, 8, 1529412708L, null);
        Works w9 = new Works(9, 9, 9, 1529412709L, null);
        Works w10 = new Works(10, 10, 10, 1529412710L, null);
        Works w11 = new Works(11, 11, 11, 1529412711L, null);
        Works w12 = new Works(12, 12, 12, 1529412712L, null);
        Works w13 = new Works(13, 13, 13, 1529412713L, null);
        Works w14 = new Works(14, 14, 14, 1529412714L, null);
        Works w15 = new Works(15, 15, 15, 1529412715L, null);

        worksTreeSet.add(w8);
        worksTreeSet.add(w9);
        worksTreeSet.add(w10);
        worksTreeSet.add(w11);
        worksTreeSet.add(w12);
        worksTreeSet.add(w13);
        worksTreeSet.add(w14);
        worksTreeSet.add(w15);
        worksTreeSet.add(w1);
        worksTreeSet.add(w2);
        worksTreeSet.add(w3);
        worksTreeSet.add(w4);
        worksTreeSet.add(w5);
        worksTreeSet.add(w6);
        worksTreeSet.add(w7);

        return worksTreeSet;
    }

    /**
     * 添加测试
     */

    @Test
    public void testAdd() {

        /**
         * 人气集合
         */
        Works w0 = new Works(00, 00, 00, 1529412700L, null);
        Works w1 = new Works(1, 1, 1, 1529412701L, null);
        Works w2 = new Works(2, 2, 2, 1529412702L, null);
        Works w3 = new Works(3, 3, 3, 1529412703L, null);
        Works w4 = new Works(4, 4, 4, 1529412704L, null);
        Works w5 = new Works(5, 5, 5, 1529412705L, null);
        Works w6 = new Works(6, 6, 6, 1529412706L, null);
        Works w7 = new Works(7, 7, 7, 1529412707L, null);
        Works w8 = new Works(8, 8, 8, 1529412708L, null);
        Works w9 = new Works(9, 9, 9, 1529412709L, null);
        Works w10 = new Works(10, 10, 10, 1529412710L, null);
        Works w11 = new Works(11, 11, 11, 1529412711L, null);
        Works w12 = new Works(12, 12, 12, 1529412712L, null);
        Works w13 = new Works(13, 13, 13, 1529412713L, null);
        Works w14 = new Works(14, 14, 14, 1529412714L, null);
        Works w15 = new Works(15, 15, 15, 1529412715L, null);
        Works w16 = new Works(16, 16, 16, 1529412716L, null);

        logger.info("-------------限定maxSize为 1 ---------------------");
        WorksTreeSet<Works> treeSetHeat1 = new WorksTreeSet<>(1, new WorksHeatComparator());
        WorksTreeSet<Works> treeSetNew1 = new WorksTreeSet<>(1, new WorksNewestComparator());

        treeSetHeat1.add(w4);
        treeSetNew1.add(w4);
        logger.info("treeSetHeat1 初始状态" + treeSetHeat1.toString());
        logger.info("treeSetNew1 初始状态" + treeSetNew1.toString());

        logger.info("treeSetHeat1 是否允许插入一个 heat 更高的：" + treeSetHeat1.isAllowAdd(w16));
        logger.info("treeSetNew1 是否允许插入一个 time 更高的：" + treeSetNew1.isAllowAdd(w16));

        treeSetHeat1.add(w16);
        treeSetNew1.add(w16);
        logger.info("treeSetHeat1 一个 heat 更高的：" + treeSetHeat1.toString());
        logger.info("treeSetNew1 一个 time 更高的：" + treeSetNew1.toString());

        logger.info("treeSetHeat1 是否允许插入一个 heat 更低的：" + treeSetHeat1.isAllowAdd(w0));
        logger.info("treeSetNew1 是否允许插入一个 time 更低的：" + treeSetNew1.isAllowAdd(w0));
        treeSetHeat1.add(w0);
        treeSetNew1.add(w0);
        logger.info("treeSetHeat1 一个 heat 更低的：" + treeSetHeat1.toString());
        logger.info("treeSetNew1 一个 time 更低的：" + treeSetNew1.toString());


        logger.info("----------多个元素---------------------");
        WorksTreeSet<Works> treeSetHeat2 = new WorksTreeSet<>(4, new WorksHeatComparator());
        WorksTreeSet<Works> treeSetNew2 = new WorksTreeSet<>(4, new WorksNewestComparator());
        treeSetHeat2.add(w3);
        treeSetHeat2.add(w2);
        treeSetHeat2.add(w5);

        treeSetNew2.add(w3);
        treeSetNew2.add(w2);
        treeSetNew2.add(w5);
        logger.info("treeSetHeat2 初始状态" + treeSetHeat2.toString());
        logger.info("treeSetNew2 初始状态" + treeSetNew2.toString());

        logger.info("treeSetHeat2 是否允许插入一个 heat 更高的：" + treeSetHeat2.isAllowAdd(w6));
        logger.info("treeSetNew2 是否允许插入一个 time 更高的：" + treeSetNew2.isAllowAdd(w6));

        treeSetHeat2.add(w6);
        treeSetNew2.add(w6);
        logger.info("treeSetHeat2 一个 heat 更高的：" + treeSetHeat2.toString());
        logger.info("treeSetNew2 一个 time 更高的：" + treeSetNew2.toString());

        logger.info("treeSetHeat2 是否允许插入一个 heat 更低的：" + treeSetHeat2.isAllowAdd(w0));
        logger.info("treeSetNew2 是否允许插入一个 time 更低的：" + treeSetNew2.isAllowAdd(w0));

        treeSetHeat2.add(w0);
        treeSetNew2.add(w0);
        logger.info("treeSetHeat2 一个 heat 更低的：" + treeSetHeat2.toString());
        logger.info("treeSetNew2 一个 time 更低的：" + treeSetNew2.toString());

        treeSetHeat2.add(w16);
        treeSetNew2.add(w16);
        logger.info("满状态下 treeSetHeat2 一个 heat 更高的：" + treeSetHeat2.toString());
        logger.info("满状态下 treeSetNew2 一个 time 更高的：" + treeSetNew2.toString());


    }

    /**
     * 当元素排序的值为null时，会不会报错
     */

    @Test
    public void testSort() {
        WorksTreeSet<Works> worksTreeSet = new WorksTreeSet<>(1, new WorksHeatComparator());
        Works w1 = new Works(1, 1, 1, 1529412711L, null);
        Works w2 = new Works(2, 2, null, null, null);
        Works w3 = new Works(3, 3, null, null, null);
        Works w4 = new Works(4, 4, null, null, null);
        Works w5 = new Works(5, 5, null, null, null);

        /**
         * 先插入一个判断条件为null的元素，再插入一个正常元素
         */
        try {
            worksTreeSet.add(w2);
            worksTreeSet.add(w1);
        } catch (NullPointerException e) {
            logger.info("抛出null异常");
            e.printStackTrace();
        }
        logger.info("继续运行");
        //logger.info("worksTreeSet:"+worksTreeSet.toString());
    }


    /**
     * 获取人气最高的作品
     */
    @Test
    public void testGetElemsHead() {
        WorksTreeSet<Works> treeSet = iniPopularTreeSet(5, new WorksHeatComparator());
        List<Works> list;

        list = treeSet.getElemsHead(10, 1);
        logger.info("treeSet 15,num=10,page=1" + list.toString());

        list = treeSet.getElemsHead(1, 0);
        logger.info("treeSet 15,num=1,page=0" + list.toString());

        list = treeSet.getElemsHead(100, 1000);
        logger.info("treeSet 15,num=100,page=100" + list.toString());

        list = new WorksTreeSet<Works>(15, new WorksHeatComparator()).getElemsHead(100, 100);
        logger.info("空treeSet，,num=100,page=100" + list.toString());
    }


    /**
     * 获取最新的作品
     */
    @Test
    public void testGetElemsHeadNewest() {
        logger.info(new Timestamp(System.currentTimeMillis()).toString());
        WorksTreeSet<Works> treeSet = iniPopularTreeSet(15, new WorksNewestComparator());
        List<Works> list;

        Works w16 = new Works(16, 16, 16, 1529412716L, null);
        Works w0 = new Works(00, 00, 00, 1529412700L, null);

        logger.info("treeSet初始状态" + treeSet.toString());

        logger.info("treeSet 是否允许插入一个时间更新的：" + treeSet.isAllowAdd(w16));

        logger.info("treeSet 是否允许插入一个时间更旧的： " + treeSet.isAllowAdd(w0));

        treeSet.add(w16);
        logger.info("treeSet 插入一个时间更新的：" + treeSet.toString());


        list = treeSet.getElemsHead(10, 1);
        logger.info("treeSet 15,num=10,page=1" + list.toString());

        list = treeSet.getElemsHead(1, 0);
        logger.info("treeSet 15,num=1,page=0" + list.toString());

        list = treeSet.getElemsHead(100, 1000);
        logger.info("treeSet 15,num=100,page=100" + list.toString());

        list = new WorksTreeSet<Works>(15, new WorksHeatComparator()).getElemsHead(100, 100);
        logger.info("空treeSet，,num=100,page=100" + list.toString());
    }


    @Test
    public void testRemove() {
        /**
         * 人气集合
         */
        Works w0 = new Works(00, 00, 1, 1529412700L, null);
        Works w1 = new Works(1, 1, 2, 1529412701L, null);
        Works w2 = new Works(2, 2, 3, 1529412702L, null);
        Works w3 = new Works(3, 3, 4, 1529412703L, null);
        Works w4 = new Works(4, 4, 5, 1529412704L, null);
        Works w5 = new Works(5, 5, 6, 1529412705L, null);
        Works w6 = new Works(6, 6, 7, 1529412706L, null);
        Works w7 = new Works(7, 7, 8, 1529412707L, null);
        Works w8 = new Works(8, 8, 9, 1529412708L, null);
        Works w9 = new Works(9, 9, 9, 1529412709L, null);
        Works w10 = new Works(10, 10, 10, 1529412710L, null);
        Works w11 = new Works(11, 11, 11, 1529412711L, null);
        Works w12 = new Works(12, 12, 12, 1529412712L, null);
        Works w13 = new Works(13, 13, 13, 1529412713L, null);
        Works w14 = new Works(14, 14, 14, 1529412714L, null);
        Works w15 = new Works(15, 15, 15, 1529412715L, null);
        Works w16 = new Works(16, 16, 16, 1529412716L, null);

        logger.info("-------------限定maxSize为 8 ---------------------");
        WorksTreeSet<Works> treeSetHeat1 = new WorksTreeSet<>(8, new WorksHeatComparator());
        WorksTreeSet<Works> treeSetNew1 = new WorksTreeSet<>(8, new WorksNewestComparator());


        treeSetHeat1.add(w1);
        treeSetHeat1.add(w2);

        w1.setWorksHeat(2);
        treeSetHeat1.remove(w1);
        treeSetHeat1.add(w3);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w5);
        treeSetHeat1.add(w6);
        treeSetHeat1.add(w7);
        treeSetHeat1.add(w8);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        treeSetHeat1.add(w9);
        logger.info("初始化tree：{}", treeSetHeat1.toString());

        treeSetHeat1.remove(w1);
        logger.info("删除不存在的works：{}", treeSetHeat1.toString());

        treeSetHeat1.remove(w3);
        logger.info("删除w2：{}", treeSetHeat1.toString());

        treeSetHeat1.remove(w9);
        logger.info("删除w9：{}", treeSetHeat1.toString());

        treeSetHeat1.remove(w2);
        treeSetHeat1.remove(w4);
        treeSetHeat1.remove(w5);
        treeSetHeat1.remove(w6);
        treeSetHeat1.remove(w7);
        treeSetHeat1.remove(w8);
        logger.info("删除所有：{}", treeSetHeat1.toString());
    }

    @Test
    public void testAddZero() {
        /**
         * 人气集合
         */
        Works w1 = new Works(1, 1, 11, 1529412700L, 1,11,1);
        Works w2 = new Works(2, 2, 11, 1529412700L, 1,11,1);
        Works w3 = new Works(3, 3, 11, 1529412700L, 1,11,1);
        Works w4 = new Works(4, 4, 11, 1529412700L, 1,11,1);
        Works w5 = new Works(5, 5, 11, 1529412700L, 1,11,1);
        Works w6 = new Works(6, 6, 11, 1529412700L, 1,11,1);
        Works w7 = new Works(7, 7, 11, 1529412700L, 1,11,1);

        Works w8 = new Works(8, 8, 1123, 1529412700L, 1,1,1);
        Works w9 = new Works(9, 9, 13, 1529412700L, 1,11,1);


        logger.info("-------------限定maxSize为 8 ---------------------");
        WorksTreeSet<Works> treeSetHeat1 = new WorksTreeSet<>(8, new WorksHeatComparator());
        WorksTreeSet<Works> treeSetHeat2 = new WorksTreeSet<>(8, new WorksWeekComparator());
        WorksTreeSet<Works> treeSetHeat3 = new WorksTreeSet<>(8, new WorksNewestComparator());
        treeSetHeat1.add(w6);
        treeSetHeat1.add(w1);
        treeSetHeat1.add(w2);
        treeSetHeat1.add(w3);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w5);
        treeSetHeat1.add(w7);





        treeSetHeat2.add(w6);
        treeSetHeat2.add(w1);
        treeSetHeat2.add(w2);
        treeSetHeat2.add(w3);
        treeSetHeat2.add(w4);
        treeSetHeat2.add(w4);
        treeSetHeat2.add(w4);
        treeSetHeat2.add(w4);
        treeSetHeat2.add(w4);
        treeSetHeat2.add(w5);
        treeSetHeat2.add(w7);
        treeSetHeat2.add(w7);

        treeSetHeat3.add(w6);
        treeSetHeat3.add(w1);
        treeSetHeat3.add(w2);
        treeSetHeat3.add(w3);
        treeSetHeat3.add(w4);
        treeSetHeat3.add(w4);
        treeSetHeat3.add(w4);
        treeSetHeat3.add(w4);
        treeSetHeat3.add(w4);
        treeSetHeat3.add(w5);
        treeSetHeat3.add(w7);
        treeSetHeat3.add(w7);


        logger.info(treeSetHeat1.toString());
        logger.info(treeSetHeat2.toString());
        logger.info(treeSetHeat3.toString());
    }

    @Test
    public void testIterator(){
        /**
         * 人气集合
         */
        Works w1 = new Works(1, 1, 11, 1529412700L, 1,11,1);
        Works w2 = new Works(2, 2, 11, 1529412700L, 1,11,1);
        Works w3 = new Works(3, 3, 11, 1529412700L, 1,11,1);
        Works w4 = new Works(4, 4, 11, 1529412700L, 1,11,1);
        Works w5 = new Works(5, 5, 11, 1529412700L, 1,11,1);
        Works w6 = new Works(6, 6, 11, 1529412700L, 1,11,1);
        Works w7 = new Works(7, 7, 11, 1529412700L, 1,11,1);
        Works w8 = new Works(8, 8, 1123, 1529412700L, 1,1,1);
        Works w9 = new Works(9, 9, 13, 1529412700L, 1,11,1);

        logger.info("-------------限定maxSize为 8 ---------------------");
        WorksTreeSet<Works> treeSetHeat1 = new WorksTreeSet<>(8, new WorksHeatComparator());
        WorksTreeSet<Works> treeSetHeat2 = new WorksTreeSet<>(8, new WorksWeekComparator());
        WorksTreeSet<Works> treeSetHeat3 = new WorksTreeSet<>(8, new WorksNewestComparator());
        treeSetHeat1.add(w6);
        treeSetHeat1.add(w1);
        treeSetHeat1.add(w2);
        treeSetHeat1.add(w3);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w4);
        treeSetHeat1.add(w5);
        treeSetHeat1.add(w7);


        logger.info("遍历前:{}",treeSetHeat1.toString());
        Iterator<Works> worksIterator = treeSetHeat1.iterator();
        while (worksIterator.hasNext()){
            Works works = worksIterator.next();
            works.setWorksHeat(works.getWorksHeat()+1);
        }
        logger.info("遍历后:{}",treeSetHeat1.toString());
    }
    class t {
        private int index;
        private String str;

        public t(int index, String str) {
            this.index = index;
            this.str = str;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return String.format("index=%d,str=%s", index, str);
        }
    }

    class comparator implements Comparator<t> {
        @Override
        public int compare(t o1, t o2) {
            return 0;
        }
    }

}





