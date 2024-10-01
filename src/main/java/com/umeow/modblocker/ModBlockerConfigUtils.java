package com.umeow.modblocker;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;

public class ModBlockerConfigUtils
{
	private static List<String> blockIdList = null;
	private static List<String> blockMessageList = null;
	
	public static List<String> getBlockIdList() 
	{
		if(ModBlocker.config == null)
			return null;
		
		if(blockIdList != null)
			return new ArrayList<String>(blockIdList);
		
		List<String> result = new ArrayList<String>();
		for(ModBlockerConfigModProfile profile : ModBlocker.config.blockList)
		{
			result.add(profile.id);
		}
		
		blockIdList = new ArrayList<String>(result);
		return result;
	}
	
	public static List<String> getBlockMessageList()
	{
		if(ModBlocker.config == null)
			return null;
		
		if(blockMessageList != null)
			return new ArrayList<String>(blockMessageList);
		
		List<String> result = new ArrayList<String>();
		for(ModBlockerConfigModProfile profile : ModBlocker.config.blockList)
		{
			result.add(profile.message);
		}
		
		blockMessageList = new ArrayList<String>(result);
		return result;
	}
	
	public static void reload(ICommandSender sender)
	{
		sender.sendMessage(new TextComponentString("Reloading..."));
		
		blockIdList = null;
		blockMessageList = null;
		
		String oldConfig = null;
		
		Gson gson = new Gson();
		
		if(ModBlocker.config != null)
			oldConfig = gson.toJson(ModBlocker.config);
		
		ModBlockerConfigLoader.loadConfig();
		
		if(ModBlocker.config != null)
		{
			sender.sendMessage(new TextComponentString("Reload Success!"));
		}
		else
		{
			sender.sendMessage(new TextComponentString("Reload Failed, will back to previous config"));
			
			if(oldConfig != null)
				ModBlocker.config = gson.fromJson(oldConfig, ModBlockerConfig.class);
		}
	}
}
