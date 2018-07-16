package com.miniworld.utils;

import com.miniworld.exception.ParamException;

import java.util.*;

public class RandomListUtil {


    /**
     * @param min :范围的最左边界值
     * @param max :范围的最右边界值
     * @param num :需要获取的数量
     * @return list:返回操作结果，如果内部集合为空，则返回空的list
     * @MethodName : GetRandomList
     * @Description : 在给定范围中获取定量不重复的随机数
     * 1.num,max,min必须为正整数
     * 2.num<max时，在[min,max]中获取数值
     * 3.num>=max时，直接返回max所有元素
     * <p>
     * 先初始化一个待选数组sourceArr，里面包含元素为[min,max]，长度为len
     * 再新建一个结果数组reArr
     * 在[0,len-1]中随机一个数，假设为p，将sourceArr[p]放入reArr中，然后将sourceArr的p与len-1数值互换
     * 重复以上操作，即可获得不重复随机数
     */
    public static List<Integer> GetRandomList(int min, int max, int num) {
        if (min < 0 || max < 0 || num < 0 || max < min) throw new ParamException("参数范围错误");
        List<Integer> reList;
        if (num >= max) {
            reList = new ArrayList<>(max);
            for (int i = min; i < max + 1; i++) {
                reList.add(i);
            }
            return reList;
        }
        int len = max - min + 1;
        //初始化给定范围的待选数组
        int[] sourceArr = new int[len];
        for (int i = min; i < min + len; i++) {
            sourceArr[i - min] = i;
        }
        reList = new ArrayList<>(num);
        int index = 0;
        Random rd = new Random();
        for (int i = 0; i < num; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            reList.add(sourceArr[index]);
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            sourceArr[index] = sourceArr[len];
        }
        return reList;
    }
}
