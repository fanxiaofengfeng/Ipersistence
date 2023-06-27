package com.amber.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Configuration {

    Map<String, MappedStatement> mappedStatementMap = new HashMap<>();
    private DataSource dataSource;
}
