package dte.employme.inventories;

import static com.github.stefvanschie.inventoryframework.pane.Orientable.Orientation.HORIZONTAL;
import static dte.employme.messages.MessageKey.INVENTORY_ITEM_PALETTE_BACK_ITEM_NAME;
import static dte.employme.messages.MessageKey.INVENTORY_ITEM_PALETTE_ENGLISH_SEARCH_ITEM_NAME;
import static dte.employme.messages.MessageKey.INVENTORY_ITEM_PALETTE_NEXT_ITEM_NAME;
import static dte.employme.messages.MessageKey.INVENTORY_ITEM_PALETTE_TITLE;
import static dte.employme.utils.InventoryFrameworkUtils.createRectangle;
import static dte.employme.utils.InventoryUtils.createWall;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.Pane.Priority;

import dte.employme.conversations.Conversations;
import dte.employme.conversations.JobGoalPrompt;
import dte.employme.services.message.MessageService;
import dte.employme.utils.MaterialUtils;
import dte.employme.utils.items.ItemBuilder;

public class ItemPaletteGUI extends ChestGui
{
	private final MessageService messageService;
	private final Function<ItemStack, GuiItem> itemCreator;
	private final ConversationFactory typeConversationFactory;
	private Consumer<InventoryClickEvent> englishItemClickHandler;
	private PaginatedPane itemsPane;

	private static final List<ItemStack> ALL_ITEMS = Arrays.stream(Material.values())
			.filter(MaterialUtils::isObtainable)
			.map(ItemStack::new)
			.collect(toList());

	private static final int
	ITEMS_PER_PAGE = 9*5,
	PAGES_AMOUNT = (ALL_ITEMS.size() / ITEMS_PER_PAGE) +1;
	
	public ItemPaletteGUI(MessageService messageService, Function<ItemStack, GuiItem> itemCreator, Consumer<ConversationFactory> typeConversationFactoryCustomizer)
	{
		super(6, messageService.getMessage(INVENTORY_ITEM_PALETTE_TITLE).first());

		this.messageService = messageService;
		this.itemCreator = itemCreator;

		this.typeConversationFactory = Conversations.createFactory(messageService)
				.withLocalEcho(false)
				.withFirstPrompt(new JobGoalPrompt(messageService));
		
		typeConversationFactoryCustomizer.accept(this.typeConversationFactory);

		setOnTopClick(event -> event.setCancelled(true));
		addPane(createItemsPane());
		addPane(createControlPane());
		addPane(createRectangle(Priority.LOWEST, 1, 5, 7, 1, new GuiItem(new ItemStack(createWall(Material.BLACK_STAINED_GLASS_PANE)))));
		update();
	}
	
	protected void setEnglishItemClickHandler(Consumer<InventoryClickEvent> handler) 
	{
		this.englishItemClickHandler = handler;
	}


	/*
	 * Panes
	 */
	private Pane createControlPane()
	{
		OutlinePane pane = new OutlinePane(0, 5, 9, 1, Priority.LOW);
		pane.setOrientation(HORIZONTAL);
		pane.setGap(3);

		pane.addItem(createController("MHF_ArrowLeft", this.messageService.getMessage(INVENTORY_ITEM_PALETTE_BACK_ITEM_NAME).first(), currentPage -> currentPage > 0, currentPage -> --currentPage));
		pane.addItem(createEnglishSearchItem());
		pane.addItem(createController("MHF_ArrowRight", this.messageService.getMessage(INVENTORY_ITEM_PALETTE_NEXT_ITEM_NAME).first(), currentPage -> currentPage < (this.itemsPane.getPages()-1), currentPage -> ++currentPage));

		return pane;
	}

	private Pane createItemsPane() 
	{
		Deque<GuiItem> remainingItems = ALL_ITEMS.stream()
				.map(this.itemCreator::apply)
				.collect(toCollection(LinkedList::new));

		PaginatedPane pane = new PaginatedPane(0, 0, 9, 6, Priority.LOWEST);

		for(int i = 0; i < PAGES_AMOUNT; i++)
			pane.addPane(i, createPage(remainingItems));

		pane.setPage(0);

		this.itemsPane = pane;

		return pane;
	}

	private Pane createPage(Deque<GuiItem> items) 
	{
		OutlinePane itemsPane = new OutlinePane(0, 0, 9, 6, Priority.LOWEST);
		itemsPane.setOrientation(HORIZONTAL);

		for(int i = 1; i <= ITEMS_PER_PAGE; i++) 
		{
			if(!items.isEmpty()) //a little trick to avoid NoSuchElementException at the last page
				itemsPane.addItem(items.removeFirst());
		}

		return itemsPane;
	}



	/*
	 * Items
	 */
	@SuppressWarnings("deprecation")
	private GuiItem createController(String ownerName, String itemName, IntPredicate shouldContinue, IntUnaryOperator nextPage) 
	{
		return new GuiItem(new ItemBuilder(Material.PLAYER_HEAD)
				.withItemMeta(SkullMeta.class, skullMeta -> skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(ownerName)))
				.named(itemName)
				.createCopy(),
				event -> 
		{
			int currentPage = this.itemsPane.getPage();

			if(!shouldContinue.test(currentPage))
				return;

			this.itemsPane.setPage(nextPage.applyAsInt(currentPage));
			update();
		});
	}

	private GuiItem createEnglishSearchItem() 
	{
		return new GuiItem(new ItemBuilder(Material.NAME_TAG)
				.named(this.messageService.getMessage(INVENTORY_ITEM_PALETTE_ENGLISH_SEARCH_ITEM_NAME).first())
				.glowing()
				.createCopy(), 
				event -> 
		{
			if(this.englishItemClickHandler != null)
				this.englishItemClickHandler.accept(event);
			
			Player player = (Player) event.getWhoClicked();
			player.closeInventory();

			this.typeConversationFactory.buildConversation(player).begin();
		});
	}
}