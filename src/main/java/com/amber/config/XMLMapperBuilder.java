package com.amber.config;

import com.amber.pojo.Configuration;
import com.amber.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 解析Mapper配置文件，并将解析结果添加到Configuration对象中。
     *
     * @param in Mapper配置文件的输入流。
     * @return 解析后的Configuration对象。
     * @throws DocumentException 如果解析XML文档时发生错误。
     */
    public Configuration parse(InputStream in) throws DocumentException {
        // 创建一个SAXReader对象，用于读取XML文件
        SAXReader saxReader = new SAXReader();
        // 使用SAXReader对象读取XML文档，返回一个Document对象
        Document document = saxReader.read(in);

        // 获取XML文档的根元素
        Element rootElement = document.getRootElement();

        // 获取Mapper的命名空间
        String namespace = rootElement.attributeValue("namespace");

        // 获取所有名为"select"的子元素
        List<Element> selectElements = rootElement.selectNodes("//select");

        // 遍历selectElements列表，解析每个<select>元素
        for (Element element : selectElements) {
            // 获取<select>元素的id、resultType、parameterType属性值以及SQL语句内容
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            //获取元素的文本内容
            String sqlText = element.getTextTrim();
            // 创建MappedStatement对象，用于存储解析结果
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sqlText);

            // 构造 MappedStatement 在 Configuration 中的唯一标识
            String key = namespace + "." + id;

            // 将MappedStatement 对象添加到 Configuration 的 MappedStatementMap 中
            configuration.getMappedStatementMap().put(key, mappedStatement);
        }

        // 返回解析后的 Configuration 对象
        return configuration;
    }
}
