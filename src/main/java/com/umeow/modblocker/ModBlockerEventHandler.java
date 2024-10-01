package com.umeow.modblocker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ServerConnectionFromClientEvent;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;

public class ModBlockerEventHandler
{
	@SubscribeEvent
    public void onServerConnectionFromClientEvent(ServerConnectionFromClientEvent event) 
	{
		if(ModBlocker.config == null)
			return;
		
		NetHandlerPlayServer netHandler = (NetHandlerPlayServer) event.getHandler();
		
		checkMods(netHandler.player);
	}
	
	public static Boolean checkMods(EntityPlayerMP player) {
		if(ModBlocker.config == null)
			return false;
		
		NetworkManager networkManager = player.connection.getNetworkManager();
		NetworkDispatcher networkDispatcher = NetworkDispatcher.get(networkManager);
		
		Map<String, String> modMap = networkDispatcher.getModList();
		
		if (modMap == null) 
		{
			modMap = new HashMap<String, String>();
		}
		
		List<String> blockIdList = ModBlockerConfigUtils.getBlockIdList();
		List<String> blockMessageList = ModBlockerConfigUtils.getBlockMessageList();
		
		Set<String> modList = modMap.keySet();
		for(String modid : modList)
		{
			int index = blockIdList.indexOf(modid);
			if(index == -1)
				continue;
			
			String message = new String(blockMessageList.get(index));
			
			if(message == null || message.length() == 0)
				message = new String(ModBlocker.config.defaultMessage);
			
			message = message.replaceAll("\\{username\\}", player.getName());
			message = message.replaceAll("\\{modid\\}", modid);
			
			player.connection.disconnect(new TextComponentString(message));
			
			if(ModBlocker.config.chat)
			{
				String chatMessage = new String(ModBlocker.config.chatMessage);
				
				chatMessage = chatMessage.replaceAll("\\{username\\}", player.getName());
				chatMessage = chatMessage.replaceAll("\\{modid\\}", modid);
				
				player.getServer().sendMessage(new TextComponentString(chatMessage));
			}
			return true;
		}
		
		return false;
	}
}
