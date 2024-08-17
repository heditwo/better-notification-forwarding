package com.heditwo.betternotificationforwarding;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BetterNotificationForwardingPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BetterNotificationForwardingPlugin.class);
		RuneLite.main(args);
	}
}