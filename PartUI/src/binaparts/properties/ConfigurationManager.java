package binaparts.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private String configFilePath;
    private Properties properties = new Properties();
    
     public ConfigurationManager(String configFilePath) throws Exception {
        this.configFilePath = configFilePath;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(configFilePath);
            properties.load(fis);
        } catch (Exception ex) {
            // creates the configuration file and set default properties
            setDefaults();
            save();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
    private void setDefaults() {
        properties.put("host", "Host Name");
        properties.put("port", "Port");
        properties.put("database", "Database Name");
        properties.put("user", "ProjectEngineer");
        properties.put("password", "greg");  
        properties.put("appUser", "Username");
        properties.put("appPassword", "Password");
        
    }
    public void save() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFilePath);
            properties.store(fos, "My Application Settings");
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    public void printProperties(ConfigurationManager config) {
        System.out.println("host = " + config.getProperty("host"));
        System.out.println("port = " + config.getProperty("port"));
        System.out.println("database = " + config.getProperty("database"));
        System.out.println("user = " + config.getProperty("user"));
        System.out.println("password = " + config.getProperty("password"));
        System.out.println("appUser = " + config.getProperty("appUser"));
        System.out.println("appPassword = " + config.getProperty("appPassword"));
    }
}
