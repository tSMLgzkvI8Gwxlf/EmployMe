package dte.employme.utils;

import static dte.employme.utils.InventoryUtils.createWall;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;

public class InventoryFrameworkUtils
{
	//Container of static methods
	private InventoryFrameworkUtils(){}

	public static Pane createRectangle(Priority priority, int x, int y, int length, int height, GuiItem item) 
	{
		OutlinePane pane = new OutlinePane(x, y, length, height, priority);
		pane.addItem(item);
		pane.setRepeat(true);
		return pane;
	}
	
	public static Pane createSquare(Priority priority, int x, int y, int length, GuiItem item) 
	{
		return createRectangle(priority, x, y, length, length, item);
	}
	
	public static Pane createWalls(ChestGui gui, Priority priority) 
	{
		PatternPane background = new PatternPane(0, 0, 9, 6, createWallsPattern(gui));
		background.bindItem('W', new GuiItem(createWall(Material.BLACK_STAINED_GLASS_PANE)));

		return background;
	}
	
	public static StaticPane createItemPane(int x, int y, GuiItem item) 
	{
		return createItemPane(x, y, Priority.NORMAL, item);
	}
	
	public static StaticPane createItemPane(int x, int y, Priority priority, GuiItem item) 
	{
		StaticPane pane = new StaticPane(x, y, 1, 1, priority);
		pane.addItem(item, x, y);
		
		return pane;
	}

	private static Pattern createWallsPattern(ChestGui gui) 
	{
		List<String> pattern = new ArrayList<>();

		pattern.add(StringUtils.repeat("W", 9));

		for(int i = 1, rows = gui.getRows()-2; i <= rows; i++)
			pattern.add("W" + StringUtils.repeat(" ", 7) + "W");

		pattern.add(StringUtils.repeat("W", 9));

		return new Pattern(pattern.toArray(new String[0]));
	}
}