package dte.employme.inventories;

import org.bukkit.inventory.ItemStack;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;

import dte.employme.job.rewards.ItemsReward;

public class ItemsRewardPreviewGUI extends ChestGui
{
	private final ItemsReward itemsReward;
	
	public ItemsRewardPreviewGUI(ItemsReward itemsReward) 
	{
		super(6, "Reward Preview (Esc to Return)");
		
		this.itemsReward = itemsReward;
		
		setOnTopClick(event -> event.setCancelled(true));
		addPane(createItemsPane(Priority.LOWEST));
		update();
	}
	
	private OutlinePane createItemsPane(Priority priority) 
	{
		OutlinePane pane = new OutlinePane(0, 0, 9, 6, priority);
		
		this.itemsReward.getItems().stream()
		.map(item -> new GuiItem(new ItemStack(item)))
		.forEach(pane::addItem);
		
		return pane;
	}
}
