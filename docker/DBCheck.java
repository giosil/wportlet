package org.dew.check;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBCheck {
	
	public static void main(String[] args) {
		
		try {
			if(args == null || args.length == 0) {
				System.out.println("Usage: DBCheck file_properties [sleep] [attempts]");
				return;
			}
			
			int sleep    = 2;
			int attempts = 10;
			if(args.length > 1) {
				try {
					sleep = Integer.parseInt(args[1]);
				}
				catch(Exception ex) {
				}
				if(sleep < 1) sleep = 1;
			}
			if(args.length > 2) {
				try {
					attempts = Integer.parseInt(args[2]);
				}
				catch(Exception ex) {
				}
				if(attempts < 1) attempts = 1;
			}
			
			String fileProperties = args[0];
			if(fileProperties == null || fileProperties.length() == 0) {
				System.out.println("[DBCheck] invalid file_properties");
				return;
			}
			
			File file = new File(fileProperties);
			if(!file.exists()) {
				System.out.println("[DBCheck] file properties " + file + " not found.");
				return;
			}
			
			Properties properties = new Properties();
			properties.load(new FileInputStream(file));
			
			String driverClassName = properties.getProperty("jdbc.default.driverClassName");
			if(driverClassName == null || driverClassName.length() == 0) {
				System.out.println("[DBCheck] missing jdbc.default.driverClassName in " + file);
				return;
			}
			String url = properties.getProperty("jdbc.default.url");
			if(url == null || url.length() == 0) {
				System.out.println("[DBCheck] missing jdbc.default.url in " + file);
				return;
			}
			String username = properties.getProperty("jdbc.default.username");
			if(username == null || username.length() == 0) {
				System.out.println("[DBCheck] missing jdbc.default.username in " + file);
				return;
			}
			String password = properties.getProperty("jdbc.default.password");
			if(password == null || password.length() == 0) {
				System.out.println("[DBCheck] missing jdbc.default.password in " + file);
				return;
			}
			
			System.out.println("Class.forName(\"" + driverClassName + "\")...");
			Class.forName(driverClassName);
			
			for(int i = 0; i < attempts; i++) {
				System.out.println("[DBCheck] #" + i + " getConnection(\"" + url + "\",\"" + username + "\",*)...");
				try {
					Connection conn = DriverManager.getConnection(url, username, password);
					conn.close();
					System.out.println("[DBCheck] OK");
					return;
				}
				catch(Exception exc) {
					System.out.println("[DBCheck] error: " + exc);
				}
				Thread.sleep(sleep * 1000);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
