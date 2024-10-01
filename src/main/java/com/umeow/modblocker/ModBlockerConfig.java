package com.umeow.modblocker;

import java.util.List;

class ModBlockerConfig
{
	Boolean chat;
	String chatMessage;
	String defaultMessage;
	List<ModBlockerConfigModProfile> blockList;
}

class ModBlockerConfigModProfile
{
	String id;
	String message;
}