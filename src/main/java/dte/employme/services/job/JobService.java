package dte.employme.services.job;

import java.time.Duration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dte.employme.board.JobBoard.JobCompletionContext;
import dte.employme.job.Job;
import dte.employme.services.rewards.PartialCompletionInfo;

public interface JobService 
{
	FinishState getFinishState(Player player, Job job);
	
	String describeCompletionInGame(Job job, JobCompletionContext context);
	String describeInGame(Job job);
	
	int getGoalAmountInInventory(Job job, Inventory inventory);
	PartialCompletionInfo getPartialCompletionInfo(Player player, Job job, int maxGoalAmount);
	
	void loadJobs();
	void saveJobs();
	
	void deleteAfter(Job job, Duration delay);
	void stopAutoDelete(Job job);
	void loadAutoDeletionData();
	void saveAutoDeletionData();
	
	
	
	enum FinishState
	{
		NEGATIVE, PARTIALLY, FULLY;
		
		public boolean hasFinished() 
		{
			return this != NEGATIVE;
		}
	}
}