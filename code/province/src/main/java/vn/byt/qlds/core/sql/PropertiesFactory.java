package vn.byt.qlds.core.sql;

import org.hibernate.cfg.Environment;

import java.util.Properties;

public class PropertiesFactory {
    private static final String URL_DB_MYSQL = "jdbc:mysql://171.244.9.248:3306/";
    private final String URL_DB_POSTGRES_SQL = "jdbc:mysql://125.212.226.145:3306/";

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
        String url = URL_DB_MYSQL + dbName;
        Properties properties = new Properties();
        properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.setProperty(Environment.USER, "ghdc");
        properties.setProperty(Environment.PASS, "ghdc@123");
        properties.setProperty(Environment.URL, url);
        return properties;
    }
}
