package VTigerUtilities;

import java.io.FileInputStream;
import java.util.Properties;

public class VTProertyUtilties {

	    // Read data from SwagLabsCommonData.properties
	    public String getDataFromPropertiesFile(String key) throws Exception {

	        // Open the properties file
	        FileInputStream file = new FileInputStream("./src/main/resources/VTCommonData.properties");
	        // Create Properties object
	        Properties properties = new Properties();
	        // Load properties file
	        properties.load(file);
	        // Get value for the given key
	        String value = properties.getProperty(key);
	        // Close the file
	        file.close();

	        return value;
	    }
	}


