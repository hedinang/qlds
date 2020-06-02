package vn.byt.qlds.config.db;

import org.hibernate.cfg.Environment;

import java.util.Properties;

public class PropertiesFactory {

    public static Properties createProperties(DialectSQL dialectSQL, String dbName) {
        switch (dialectSQL) {
            case MYSQL:
                return createPropertiesDbMysql(dbName);
            case POSTGRESSQL:
                return null;
            default:
                return null;
        }
    }


    private static Properties createPropertiesDbMysql(String dbName) {
        String url = "jdbc:mysql://171.244.9.248:3306/" + dbName;
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.setProperty(Environment.USER, "ghdc");
        properties.setProperty(Environment.PASS, "ghdc@123");
        properties.setProperty(Environment.URL, url);
        return properties;
    }
}
