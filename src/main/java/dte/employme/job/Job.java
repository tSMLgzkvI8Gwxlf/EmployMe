package dte.employme.job;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import de.tr7zw.nbtapi.NBTItem;
import dte.employme.rewards.Reward;
import dte.employme.utils.java.MapBuilder;

@SerializableAs("Job")
public class Job implements ConfigurationSerializable
{
	private final UUID uuid;
	private final OfflinePlayer employer;
	private ItemStack goal;
	private Reward reward;

	public Job(OfflinePlayer employer, ItemStack goal, Reward reward) 
	{
		this(UUID.randomUUID(), employer, goal, reward);
	}

	public Job(Map<String, Object> serialized) 
	{
		this(
				UUID.fromString((String) serialized.get("UUID")),
				Bukkit.getOfflinePlayer(UUID.fromString((String) serialized.get("Employer UUID"))), 
				(ItemStack) serialized.get("Goal"), 
				(Reward) serialized.get("Reward")
				);
	}
	
	private Job(UUID uuid, OfflinePlayer employer, ItemStack goal, Reward reward) 
	{
		this.uuid = uuid;
		this.employer = employer;
		this.goal = goal;
		this.reward = reward;
	}
	
	public UUID getUUID() 
	{
		return this.uuid;
	}

	public OfflinePlayer getEmployer() 
	{
		return this.employer;
	}

	public ItemStack getGoal() 
	{
		return new ItemStack(this.goal);
	}

	public Reward getReward() 
	{
		return this.reward;
	}
	
	public void setGoal(ItemStack goal) 
	{
		this.goal = goal;
	}
	
	public void setReward(Reward reward) 
	{
		this.reward = reward;
	}
	
	public boolean isGoal(ItemStack item) 
	{
		NBTItem nbtItem = new NBTItem(item);
		nbtItem.removeKey("RepairCost");
		ItemStack finalItem = nbtItem.getItem();
		
		//basic check that the item is the required goal
		if(!finalItem.isSimilar(this.goal))
			return false;
		
		//damaged goals are unacceptable
		if(finalItem.getItemMeta() instanceof Damageable && ((Damageable) finalItem.getItemMeta()).hasDamage())
			return false;
		
		return true;
	}
	
	@Override
	public Map<String, Object> serialize()
	{
		return new MapBuilder<String, Object>()
				.put("UUID", this.uuid.toString())
				.put("Employer UUID", this.employer.getUniqueId().toString())
				.put("Goal", this.goal)
				.put("Reward", this.reward)
				.build();
	}

	@Override
	public String toString()
	{
		return String.format("Job [uuid=%s, employer=%s, goal=%s, reward=%s]", this.uuid, this.employer.getUniqueId(), this.goal, this.reward);
	}

	@Override
	public int hashCode() 
	{
		return Objects.hash(this.uuid);
	}

	@Override
	public boolean equals(Object object) 
	{
		if(this == object)
			return true;
		
		if(!(object instanceof Job))
			return false;
		
		Job other = (Job) object;
		
		return Objects.equals(this.uuid, other.uuid);
	}
}