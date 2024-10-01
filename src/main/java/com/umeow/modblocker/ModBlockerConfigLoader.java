package com.umeow.modblocker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

public class ModBlockerConfigLoader
{
	public static String getDefaultConfigString() throws IOException
	{
		InputStream inputStream = ModBlockerConfigLoader.class.getResourceAsStream("/modblocker.json");
		byte[] bytes = IOUtils.toByteArray(inputStream);
		
		String fileString =  new String(bytes, StandardCharsets.UTF_8);
		int sliceIndex = fileString.indexOf("\n");
		
		return fileString.substring(sliceIndex+1);
	}
	
	public static void createConfigFile() throws IOException
	{
		File configDirectory = new File("config");
		
		if(!configDirectory.exists())
			configDirectory.mkdirs();
		
		if(configDirectory.isFile())
		{
			ModBlocker.logger.error("`config` is a file, can't load config file!");
			return;
		}
		
		File configFile = new File("config/modblocker.json");
		
		if(!configFile.exists())
		{
			configFile.createNewFile();
			FileWriter writer = new FileWriter(configFile);
			writer.write(getDefaultConfigString());
			writer.close();
		}
		
		if(configFile.isDirectory())
		{
			ModBlocker.logger.error("`config/modblocker.json` is a directory, can't load config file!");
			return;
		}
		
		readConfigString();
	}
	
	public static String readConfigString() throws IOException
	{
		File configFile = new File("config/modblocker.json");
		
		if(!configFile.exists() || configFile.isDirectory())
		{
			return null;
		}
		
		return new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8);
	}

	public static void loadConfig() {
		try
		{
			ModBlocker.config = null;
			
			createConfigFile();
		
			String json = readConfigString();
		
			Gson gson = new Gson();
		
			ModBlocker.config = gson.fromJson(json, ModBlockerConfig.class);
		}
		catch(IOException err)
		{
			ModBlocker.config = null;
		}
	}
}
