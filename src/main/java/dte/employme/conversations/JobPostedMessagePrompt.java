package dte.employme.conversations;

import org.bukkit.Bukkit;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dte.employme.EmployMe;
import dte.employme.board.JobBoard;
import dte.employme.board.service.JobBoardService;
import dte.employme.job.Job;
import dte.employme.job.SimpleJob;
import dte.employme.job.rewards.Reward;
import dte.employme.visitors.reward.RewardTaker;

public class JobPostedMessagePrompt extends MessagePrompt
{
	private final JobBoardService jobBoardService;
	private final JobBoard jobBoard;

	public JobPostedMessagePrompt(JobBoardService jobBoardService, JobBoard jobBoard) 
	{
		this.jobBoardService = jobBoardService;
		this.jobBoard = jobBoard;
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		Player employer = (Player) context.getForWhom();
		ItemStack goalItem = (ItemStack) context.getSessionData("goal");
		Reward reward = (Reward) context.getSessionData("reward");

		Job job = new SimpleJob.Builder()
				.by(employer)
				.of(goalItem)
				.thatOffers(reward)
				.build();

		reward.accept(new RewardTaker(employer));

		//to allow messages to be sent to the player
		Bukkit.getScheduler().runTask(EmployMe.getInstance(), () -> this.jobBoardService.addJob(this.jobBoard, job));

		return "";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return Prompt.END_OF_CONVERSATION;
	}
}
