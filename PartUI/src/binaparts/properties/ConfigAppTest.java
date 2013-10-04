package binaparts.properties;
 
public class ConfigAppTest {
    private String configFilePath = "config.properties";
    
    public static void main(String... args) throws Exception {
        ConfigAppTest tester = new ConfigAppTest();
        tester.testSave();
        tester.testLoad();
    }
    
    public void testSave() throws Exception {
        ConfigurationManager config = null;
        // test with text file
        config = new ConfigurationManager(configFilePath);
        config.setProperty("host", "localhost");
        config.setProperty("port", "3306");
        config.setProperty("database", "binapartslist");
        config.setProperty("user", "ProjectEngineer");
        config.setProperty("password", "greg");
        config.setProperty("appUser", "dwilson");
        config.setProperty("appPassword", "abc");
        config.save();  
    }
    
    public void testLoad() throws Exception {
        ConfigurationManager config = null;
        // test with text file
        config = new ConfigurationManager(configFilePath);
       printProperties(config);
        
        System.out.println("==================================");
    }
 
    private void printProperties(ConfigurationManager config) {
		System.out.println("host = " + config.getProperty("host"));
		System.out.println("port = " + config.getProperty("port"));
		System.out.println("database = " + config.getProperty("database"));
		System.out.println("user = " + config.getProperty("user"));
		System.out.println("password = " + config.getProperty("password"));
		System.out.println("appUser= " + config.getProperty("appUser"));
		System.out.println("appPassword = " + config.getProperty("appPassword"));
	}
}