package vn.byt.qlds.model;

public class ESMessageSync<T> {
    String tableName;
    String dbName;
    public T data;

    public ESMessageSync(String tableName, String dbName) {
        this.tableName = tableName;
        this.dbName = dbName;
    }

    public ESMessageSync() {

    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
