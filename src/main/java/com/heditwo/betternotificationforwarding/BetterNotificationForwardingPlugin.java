package com.heditwo.betternotificationforwarding;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NotificationFired;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.*;

import java.io.IOException;

@Slf4j
@PluginDescriptor(
		name = "BetterNotificationForwarding"
)
public class BetterNotificationForwardingPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private BetterNotificationForwardingConfig config;

	@Inject
	private OkHttpClient okHttpClient;

	private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("text/plain; charset=utf-8");

	private void sendNtfyMessage(String message)
	{
		String ntfyUrl = config.ntfyUrl();
		log.debug("Attempting to send message. NTFY URL from config: {}", ntfyUrl);

		if (ntfyUrl == null || ntfyUrl.trim().isEmpty())
		{
			log.warn("Missing or empty Ntfy URL. Cannot send message.");
			return;
		}

		RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, message);

		Request request = new Request.Builder()
				.url(ntfyUrl.trim())
				.post(requestBody)
				.addHeader("Title", config.title())
				.addHeader("Priority", config.priority())
				.addHeader("Tags", config.tags())
				.build();

		okHttpClient.newCall(request).enqueue(new Callback()
		{
			@Override
			public void onFailure(Call call, IOException e)
			{
				log.error("Error submitting message to Ntfy.", e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException
			{
				log.info("Successfully sent message to Ntfy. Response code: {}", response.code());
				response.close();
			}
		});
	}

	@Subscribe
	public void onNotificationFired(NotificationFired notificationFired) {
		sendNtfyMessage(notificationFired.getMessage());
	}

	@Override
	protected void startUp() throws Exception
	{
		log.info("Starting BetterNotificationForwarding Plugin.");
		log.debug("Current Ntfy URL configuration: {}", config.ntfyUrl());
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("BetterNotificationForwarding Plugin stopped!");
	}

	@Provides
	BetterNotificationForwardingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BetterNotificationForwardingConfig.class);
	}
}
