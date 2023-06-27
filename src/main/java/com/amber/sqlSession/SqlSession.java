package com.amber.sqlSession;

import java.util.List;

public interface SqlSession {


    //查询所有
    <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //根据条件查询单个
    <E> E selectOne(String statementId, Object... params) throws Exception;
}
