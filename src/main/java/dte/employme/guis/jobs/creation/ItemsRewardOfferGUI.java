package dte.employme.guis.jobs.creation;

import static dte.employme.messages.MessageKey.GUI_ITEMS_JOB_NO_ITEMS_WARNING;
import static dte.employme.messages.MessageKey.GUI_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_LORE;
import static dte.employme.messages.MessageKey.GUI_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_NAME;
import static dte.employme.messages.MessageKey.GUI_ITEMS_REWARD_OFFER_TITLE;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;

import dte.employme.EmployMe;
import dte.employme.board.JobBoard;
import dte.employme.rewards.ItemsReward;
import dte.employme.services.job.subscription.JobSubscriptionService;
import dte.employme.services.message.MessageService;
import dte.employme.services.playercontainer.PlayerContainerService;
import dte.employme.utils.InventoryUtils;
import dte.employme.utils.inventoryframework.GuiItemBuilder;
import dte.employme.utils.items.ItemBuilder;

public class ItemsRewardOfferGUI extends ChestGui
{
	private final MessageService messageService;
	private final PlayerContainerService playerContainerService;
	private final JobSubscriptionService jobSubscriptionService;
	private final JobBoard jobBoard;

	private ItemStack confirmationButton;

	public ItemsRewardOfferGUI(JobBoard jobBoard, MessageService messageService, PlayerContainerService playerContainerService, JobSubscriptionService jobSubscriptionService) 
	{
		super(6, messageService.getMessage(GUI_ITEMS_REWARD_OFFER_TITLE).first());

		this.jobBoard = jobBoard;
		this.messageService = messageService;
		this.playerContainerService = playerContainerService;
		this.jobSubscriptionService = jobSubscriptionService;

		setOnClose(event -> 
		{
			Player player = (Player) event.getPlayer();

			if(getOfferedItems().isEmpty()) 
			{
				messageService.getMessage(GUI_ITEMS_JOB_NO_ITEMS_WARNING).sendTo(player);
			}

			player.closeInventory();
			player.getInventory().addItem(getOfferedItems().toArray(new ItemStack[0]));
		});

		addPane(createConfirmationButtonPane());
		update();
	}

	/*
	 * Panes
	 */
	private Pane createConfirmationButtonPane() 
	{
		StaticPane pane = new StaticPane(0, 5, 1, 1, Priority.LOW);
		pane.setOnClick(event -> event.setCancelled(true));
		pane.addItem(createConfirmationButton(), 0, 0);

		return pane;
	}



	/*
	 * Buttons
	 */
	private GuiItem createConfirmationButton() 
	{
		ItemStack button = new ItemBuilder(Material.GREEN_TERRACOTTA)
				.named(this.messageService.getMessage(GUI_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_NAME).first())
				.withLore(this.messageService.getMessage(GUI_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_LORE).toArray())
				.createCopy();

		this.confirmationButton = button;

		return new GuiItemBuilder()
				.forItem(this.confirmationButton)
				.whenClicked(event ->
				{
					Player player = (Player) event.getWhoClicked();
					List<ItemStack> offeredItems = getOfferedItems();

					//block confirming if the player didn't offer anything
					if(offeredItems.isEmpty())
						return;

					//disable the item return mechanism
					setOnClose(closeEvent -> {});

					//open the Goal Customization GUI
					ItemsReward itemsReward = new ItemsReward(offeredItems, this.playerContainerService);
					GoalCustomizationGUI goalCustomizationGUI = new GoalCustomizationGUI(this.messageService, this.jobSubscriptionService, this.jobBoard, itemsReward);

					Bukkit.getScheduler().runTask(EmployMe.getInstance(), () -> goalCustomizationGUI.show(player));
				})
				.build();
	}

	private List<ItemStack> getOfferedItems()
	{
		return InventoryUtils.itemsStream(getInventory(), true)
				.filter(item -> !item.equals(this.confirmationButton))
				.collect(toList());
	}
}