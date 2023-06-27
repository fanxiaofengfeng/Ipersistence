package com.amber.sqlSession;

import com.amber.pojo.Configuration;
import com.amber.pojo.MappedStatement;

import java.util.List;

public interface Executor {
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params);
}
