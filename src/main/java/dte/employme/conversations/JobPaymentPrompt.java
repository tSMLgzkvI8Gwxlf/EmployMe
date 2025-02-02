package dte.employme.conversations;

import static dte.employme.messages.MessageKey.CURRENCY_SYMBOL;
import static dte.employme.messages.MessageKey.MONEY_PAYMENT_AMOUNT_QUESTION;
import static dte.employme.messages.MessageKey.MONEY_REWARD_ERROR_NEGATIVE;
import static dte.employme.messages.MessageKey.MONEY_REWARD_NOT_A_NUMBER;
import static dte.employme.messages.MessageKey.MONEY_REWARD_NOT_ENOUGH;
import static dte.employme.messages.Placeholders.PLAYER_MONEY;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import dte.employme.messages.Placeholders;
import dte.employme.rewards.ItemsReward;
import dte.employme.rewards.MoneyReward;
import dte.employme.rewards.Reward;
import dte.employme.services.message.MessageService;
import dte.employme.utils.InventoryUtils;
import dte.employme.utils.java.NumberUtils;
import net.milkbowl.vault.economy.Economy;

public class JobPaymentPrompt extends NumericPrompt
{
	private final Economy economy;
	private final MessageService messageService;
	
	public JobPaymentPrompt(Economy economy, MessageService messageService) 
	{
		this.economy = economy;
		this.messageService = messageService;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		Double employerMoney = NumberUtils.limit(this.economy.getBalance((Player) context.getForWhom()), 2);
		
		return this.messageService.getMessage(MONEY_PAYMENT_AMOUNT_QUESTION)
				.inject(PLAYER_MONEY, employerMoney)
				.inject(Placeholders.CURRENCY_SYMBOL, this.messageService.getMessage(CURRENCY_SYMBOL).first())
				.first();
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number input) 
	{
		Player player = (Player) context.getForWhom();
		MoneyReward moneyReward = new MoneyReward(this.economy, input.doubleValue());
		
		takeReward(player, moneyReward);
		context.setSessionData("reward", moneyReward);
		
		return Prompt.END_OF_CONVERSATION;
	}
	
	@Override
	protected boolean isNumberValid(ConversationContext context, Number input) 
	{
		Player player = (Player) context.getForWhom();
		double payment = input.doubleValue();
		
		return payment > 0 && this.economy.has(player, payment);
	}
	
	@Override
	protected String getFailedValidationText(ConversationContext context, Number invalidInput) 
	{
		double payment = invalidInput.doubleValue();
		
		if(payment <= 0)
			return this.messageService.getMessage(MONEY_REWARD_ERROR_NEGATIVE).first();
		
		else if(!this.economy.has((Player) context.getForWhom(), payment))
			return this.messageService.getMessage(MONEY_REWARD_NOT_ENOUGH).first();
		
		throw new IllegalStateException("Couldn't parse the provided input to an payment amount!");
	}
	
	@Override
	protected String getInputNotNumericText(ConversationContext context, String invalidInput) 
	{
		return this.messageService.getMessage(MONEY_REWARD_NOT_A_NUMBER).first();
	}
	
	private void takeReward(Player player, Reward reward)
	{
		if(reward instanceof MoneyReward) 
			this.economy.withdrawPlayer(player, ((MoneyReward) reward).getPayment());
		
		else if(reward instanceof ItemsReward) 
			((ItemsReward) reward).getItems().forEach(item -> InventoryUtils.remove(player.getInventory(), item));
		
		else
			throw new IllegalArgumentException("Cannot take the provided reward!");
	}
}
