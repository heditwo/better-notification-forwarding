package com.heditwo.betternotificationforwarding;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("betternotificationforwarding")
public interface BetterNotificationForwardingConfig extends Config
{
	@ConfigItem(
			keyName = "ntfyUrl",
			name = "Ntfy URL",
			description = "The full URL of your ntfy.sh topic (e.g., https://ntfy.sh/yourtopic)"
	)
	default String ntfyUrl()
	{
		return "https://ntfy.sh/yourtopic";
	}

	@ConfigItem(
			keyName = "title",
			name = "Notification Title",
			description = "Set a custom title for your notifications"
	)
	default String title()
	{
		return "RuneLite Notification";
	}

	@ConfigItem(
			keyName = "priority",
			name = "Notification Priority",
			description = "Set the priority of the notification (1-5)"
	)
	default String priority()
	{
		return "default";
	}

	@ConfigItem(
			keyName = "tags",
			name = "Tags",
			description = "Comma-separated list of tags for your notifications (e.g., warning,skull)"
	)
	default String tags()
	{
		return "warning";
	}
}
