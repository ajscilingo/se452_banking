package net.scilingo.se452.banking;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class MongoPropertiesLoader {

	private static Properties _mongoProperties = new Properties();
	
	public static Properties getMongoProperties() throws IOException, FileNotFoundException {
		
		FileInputStream mongoclientPropertyFile = new FileInputStream("src/main/resources/mongoclient.properties");		
		
		try {
			_mongoProperties.load(mongoclientPropertyFile);
			return _mongoProperties;
		}
		finally {
			mongoclientPropertyFile.close();
		}
	}
	
	
	
}
