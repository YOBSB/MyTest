package com.miniworld.dao;

import org.apache.ibatis.annotations.Param;

public interface EventSysDao {
    Integer isTableExist(@Param("tableName")String tableName);
}
