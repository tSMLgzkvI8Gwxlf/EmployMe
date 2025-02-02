package dte.employme.services.addnotifiers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import dte.employme.addednotifiers.JobAddedNotifier;
import dte.spigotconfiguration.SpigotConfig;

public class SimpleJobAddedNotifierService implements JobAddedNotifierService
{
	private final Map<String, JobAddedNotifier> notifierByName = new HashMap<>();
	private final Map<UUID, JobAddedNotifier> playersNotifiers = new HashMap<>();
	private final SpigotConfig notifiersConfig;
	
	public SimpleJobAddedNotifierService(SpigotConfig notifiersConfig) 
	{
		this.notifiersConfig = notifiersConfig;
	}
	
	@Override
	public JobAddedNotifier getByName(String name) 
	{
		return this.notifierByName.get(name.toLowerCase());
	}
	
	@Override
	public void register(JobAddedNotifier notifier) 
	{
		this.notifierByName.put(notifier.getName().toLowerCase(), notifier);
	}
	
	@Override
	public Set<JobAddedNotifier> getNotifiers() 
	{
		return new HashSet<>(this.notifierByName.values());
	}

	@Override
	public JobAddedNotifier getPlayerNotifier(UUID playerUUID, JobAddedNotifier defaultNotifier) 
	{
		return this.playersNotifiers.getOrDefault(playerUUID, defaultNotifier);
	}

	@Override
	public void setPlayerNotifier(UUID playerUUID, JobAddedNotifier notifier) 
	{
		this.playersNotifiers.put(playerUUID, notifier);
	}

	@Override
	public void loadPlayersNotifiers()
	{
		this.notifiersConfig.getValues(false).forEach((uuidString, policyName) -> 
		{
			UUID playerUUID = UUID.fromString(uuidString);
			JobAddedNotifier playerNotifier = getByName((String) policyName);
			
			setPlayerNotifier(playerUUID, playerNotifier);
		});
	}

	@Override
	public void savePlayersNotifiers() 
	{
		this.playersNotifiers.forEach((playerUUID, playerPolicy) -> this.notifiersConfig.set(playerUUID.toString(), playerPolicy.getName()));
		
		try 
		{
			this.notifiersConfig.save();
		} 
		catch(IOException exception)
		{
			exception.printStackTrace();
		}
	}
}
