package com.umeow.modblocker;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.text.TextComponentString;

public class ModBlockerCommand extends CommandBase
{	
	@Override
	public String getName() 
	{
		return ModBlocker.MODID;
	}

	@Override
	public String getUsage(ICommandSender sender) 
	{
		return "/" + ModBlocker.MODID + " reload";
	}
	
	@Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException 
	{
		if(args.length < 1)
		{
			sender.sendMessage(new TextComponentString("/" + ModBlocker.MODID + " reload"));
			return;
		}
		
		if(!args[0].equals("reload"))
		{
			sender.sendMessage(new TextComponentString("Unknown command."));
			return;
		}
		
		ModBlockerConfigUtils.reload(sender);
		
		if(ModBlocker.config == null)
			return;
		
		PlayerList playerList = server.getPlayerList();
		
		for(EntityPlayerMP player : playerList.getPlayers())
		{
			ModBlockerEventHandler.checkMods(player);
		}
	}
}
