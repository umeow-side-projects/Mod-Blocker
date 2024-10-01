package com.umeow.modblocker;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

@Mod(modid = ModBlocker.MODID, name = ModBlocker.NAME, version = ModBlocker.VERSION, 
		serverSideOnly = true, acceptableRemoteVersions = "*")
public class ModBlocker
{
    public static final String MODID = "modblocker";
    public static final String NAME = "Mod Blocker";
    public static final String VERSION = "1.0";

    public static Logger logger = null;
    public static ModBlockerConfig config = null;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }
    
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ModBlockerCommand());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	logger.info("Mod Blocker initialization!");
    	MinecraftForge.EVENT_BUS.register(new ModBlockerEventHandler());
    	
    	ModBlockerConfigLoader.loadConfig();
    	
    	if(config == null)
    	{
    		ModBlocker.logger.warn("Load config failed, Mod Blocker Disabling...");
    		return;
    	}
    	
    	ModBlocker.logger.info("Load config file success!");
    }
}
