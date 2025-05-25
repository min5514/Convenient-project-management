package com.example;

import com.example.commands.WccCommand;
import com.example.commands.WccExecutes;
import com.example.datas.WccDataCopy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wcc implements ModInitializer {
	public static final String MOD_ID = "wcc";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			WccDataCopy.Copy();
		});
		WccCommand.project_list();
		WccExecutes.tellraw();
	}
}
