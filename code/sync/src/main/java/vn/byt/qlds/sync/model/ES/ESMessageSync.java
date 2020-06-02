package vn.byt.qlds.sync.model.ES;


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