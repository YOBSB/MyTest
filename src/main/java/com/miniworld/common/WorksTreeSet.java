package com.miniworld.common;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 排行榜维护集合，使用 ReentrantReadWriteLock 和 TreeSet 实现
 * TreeSet 作为元素存储的容器，其存取复杂度为O(log(n))，适合频繁变更的集合体
 * ReentrantReadWriteLock 作为集合数据一致性的保证，另外个根据使用场景其是读的次数会比写的次数要高，所以使用读写锁，提高集合的整体性能
 * 默认严格降序排序
 *
 * @param <T>
 */
public class WorksTreeSet<T> {

    private ReentrantReadWriteLock reentrantRWLock; //读写锁

    private TreeSet<T> treeSet; //内部存数据容器

    private static final int MAX_SIZE = 1000;//默认集合最大数量

    private int maxSize;//集合最大数量

    private volatile T minT; //最小的元素


    /**
     * 构造函数:指定大小,指定比较规则
     */
    public WorksTreeSet(int maxSize, Comparator<? super T> comparator) {
        this.maxSize = maxSize;
        this.treeSet = new TreeSet<>(comparator);
        this.reentrantRWLock = new ReentrantReadWriteLock();
    }


    /**
     * 封装的添加
     * 如果容器中的元素未达到最大值，则加入
     * 如果容器中的元素数目达到最大值，则比较一下要加入的元素和排最后的元素，如果比排最后的元素大，则去掉排最后的元素，添加该元素
     *
     * @param t
     */
    public void add(T t) {
        if (t == null) throw new NullPointerException();
        try {
            reentrantRWLock.writeLock().lock();
            if (isAllowAdd(t)) {
                if (treeSet.size() >= maxSize) {
                    treeSet.pollLast();
                }
                treeSet.add(t);
                minT = treeSet.last();
            }
        } finally {
            reentrantRWLock.writeLock().unlock();
        }
    }

    /**
     * 判断是否可以在list添加该元素，满足两个条件说明可以添加
     * 1.集合中的元素数量小于最大数量（maxSize）；
     * 2.集合中元素大于等于最大数量，但该元素比集合中最小的元素大，则允许加入
     *
     * @param t
     * @return
     */
    public boolean isAllowAdd(T t) {
        if (t == null) throw new NullPointerException();
        try {
            reentrantRWLock.readLock().lock();
            int size = treeSet.size();
            if (size < maxSize || (size >= maxSize && comparator().compare(t, treeSet.last()) < 0)) {
                return true;
            }
            return false;
        } finally {
            reentrantRWLock.readLock().unlock();
        }
    }

    /**
     * @param num  :每一页的数量
     * @param page :获取的页数
     * @return reList:返回操作结果，如果内部集合为空，则返回空的list
     * @MethodName : getElemsHead
     * @Description : 获取特定页数下特定个数的元素
     * start = num*(page-1)，表示获取第一个元素在treeSet的排序位置
     * 如果start>treeSet.size();返回空list，否则正常返回（list个数不一定满num个）
     */
    public List<T> getElemsHead(Integer num, Integer page) {
        if (num == null || num <= 0 || page == null || page <= 0) return new ArrayList<>();
        int start = num * (page - 1);
        List<T> reList = new ArrayList<>(num);
        try {
            reentrantRWLock.readLock().lock();
            if (start > treeSet.size()) return new ArrayList<>();
            int index = 0;
            for (T t : treeSet) {
                if (index >= start + num) break;
                if (index++ >= start) {
                    reList.add(t);
                }
            }
        } finally {
            reentrantRWLock.readLock().unlock();
        }
        return reList;
    }

    public void clear() {
        try {
            reentrantRWLock.writeLock().lock();
            this.treeSet.clear();
        }finally {
            reentrantRWLock.writeLock().unlock();
        }
    }



    public Comparator<? super T> comparator() {
        return treeSet.comparator();
    }


    public int size() {
        return treeSet.size();
    }

    public T getMinT() {
        return minT;
    }

    public boolean remove(T t){
        try {
            reentrantRWLock.writeLock().lock();
            return this.treeSet.remove(t);
        }finally {
            reentrantRWLock.writeLock().unlock();
        }
    }

    public Iterator<T> iterator() {
        return treeSet.iterator();
    }


    @Override
    public String toString() {
        try {
            reentrantRWLock.readLock().lock();
            return treeSet.toString();
        } finally {
            reentrantRWLock.readLock().unlock();
        }
    }
}
