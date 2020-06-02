package vn.byt.qlds.configuration;

import org.hibernate.Session;

import java.util.HashMap;
import java.util.Map;

public class ConnectorManager {
    private Map<String, Connector> connectors;

    public ConnectorManager() {
        System.out.println("create  new instance of class ConnectorManager");
        this.connectors = new HashMap<>();
    }

    public void addConnector(String key, Connector connector) {
        connectors.put(key, connector);
    }

    public Session openSessionById(String id) {
        Connector connector = connectors.get(id);
        System.out.println("Open session --- [" + id + "] --- " + connector.toString());
        return connector.sessionFactory.openSession();
    }
}
