package com.amber.config;

import com.amber.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 使用dom4j解析配置文件，并将解析结果封装到Configuration对象中。
     *
     * @param inputStream 配置文件的输入流。
     * @return 解析后的Configuration对象。
     * @throws DocumentException     如果解析XML文档时发生错误。
     * @throws PropertyVetoException 如果设置驱动类时发生错误。
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        // 创建一个SAXReader对象，用于读取XML文件
        SAXReader saxReader = new SAXReader();
        // 使用SAXReader对象读取XML文档，返回一个Document对象
        Document document = saxReader.read(inputStream);

        // 获取XML文档的根元素
        Element rootElement = document.getRootElement();

        // 获取所有名为"property"的子元素
        List<Element> propertyElements = rootElement.selectNodes("//property");

        // 创建一个Properties对象，用于存储属性名和属性值
        Properties properties = new Properties();

        // 遍历propertyElements列表，解析属性名和属性值，并存储到properties对象中
        for (Element element : propertyElements) {
            // 获取属性名和属性值
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            // 将属性名和属性值存储到properties对象中
            properties.setProperty(name, value);
        }

        // 创建ComboPooledDataSource对象，用于管理数据库连接池
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        // 设置数据库驱动类
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        // 设置JDBC连接URL
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        // 设置数据库用户名
        comboPooledDataSource.setUser(properties.getProperty("username"));
        // 设置数据库密码
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        // 将ComboPooledDataSource对象设置到Configuration对象中
        configuration.setDataSource(comboPooledDataSource);

        // 返回解析后的Configuration对象
        return configuration;
    }
}
