package dte.employme.messages;

public enum MessageKey
{
	//Jobs
	JOB_ADDED_TO_BOARD,
	JOB_COMPLETED,
	ITEMS_JOB_COMPLETED,
	PLAYER_COMPLETED_YOUR_JOB,
	YOU_OFFERED_TOO_MANY_JOBS,
	JOB_CANCELLED_REWARD_REFUNDED,

	//Job Added Notifiers
	YOUR_NEW_JOB_ADDED_NOTIFIER_IS,
	NEW_JOB_POSTED,

	//Subscriptions
	SUCCESSFULLY_SUBSCRIBED_TO_GOAL,
	SUCCESSFULLY_UNSUBSCRIBED_FROM_GOAL,
	SUBSCRIBED_TO_GOALS_NOTIFICATION,
	MUST_BE_SUBSCRIBED_TO_GOAL,
	YOUR_SUBSCRIPTIONS_ARE,

	//General
	PREFIX,
	NONE,
	GET,
	GOAL,
	REWARD,
	MUST_NOT_BE_CONVERSING,
	MATERIAL_NOT_FOUND,
	NEW_UPDATE_AVAILABLE,
	CURRENCY_SYMBOL,
	PLUGIN_RELOADED,

	
	
	/*
	 * Items
	 */

	//Job Icon
	JOB_ICON_NAME,
	JOB_ICON_GOAL_INSTRUCTIONS,
	JOB_ICON_ENCHANT_DESCRIPTION,
	JOB_ICON_MONEY_PAYMENT_DESCRIPTION,
	JOB_ICON_ITEMS_PAYMENT_DESCRIPTION,

	
	
	/*
	 * GUIs
	 */

	//Job Containers GUI
	INVENTORY_JOB_CONTAINERS_TITLE,
	INVENTORY_JOB_CONTAINERS_ITEMS_CONTAINER_NAME,
	INVENTORY_JOB_CONTAINERS_ITEMS_CONTAINER_LORE,
	INVENTORY_JOB_CONTAINERS_REWARDS_CONTAINER_NAME,
	INVENTORY_JOB_CONTAINERS_REWARDS_CONTAINER_LORE,
	CONTAINER_CLAIM_INSTRUCTION,

	//Player Container GUI
	INVENTORY_PLAYER_CONTAINER_NEXT_PAGE_NAME,
	INVENTORY_PLAYER_CONTAINER_NEXT_PAGE_LORE,
	INVENTORY_PLAYER_CONTAINER_PREVIOUS_PAGE_NAME,
	INVENTORY_PLAYER_CONTAINER_PREVIOUS_PAGE_LORE,

	//Job Board GUI
	INVENTORY_JOB_BOARD_TITLE,
	INVENTORY_JOB_BOARD_OFFER_COMPLETED,
	INVENTORY_JOB_BOARD_OFFER_NOT_COMPLETED,
	INVENTORY_JOB_BOARD_PERSONAL_JOBS_ITEM_NAME,
	INVENTORY_JOB_BOARD_PERSONAL_JOBS_ITEM_LORE,
	INVENTORY_JOB_BOARD_NEXT_PAGE_NAME,
	INVENTORY_JOB_BOARD_NEXT_PAGE_LORE,
	INVENTORY_JOB_BOARD_PREVIOUS_PAGE_NAME,
	INVENTORY_JOB_BOARD_PREVIOUS_PAGE_LORE,

	//Player Jobs GUI
	INVENTORY_PLAYER_JOBS_TITLE,
	INVENTORY_PLAYER_JOBS_NEXT_PAGE_NAME,
	INVENTORY_PLAYER_JOBS_NEXT_PAGE_LORE,
	INVENTORY_PLAYER_JOBS_PREVIOUS_PAGE_NAME,
	INVENTORY_PLAYER_JOBS_PREVIOUS_PAGE_LORE,

	//Job Deletion GUI
	INVENTORY_JOB_DELETION_TITLE,
	INVENTORY_JOB_DELETION_DELETE_INSTRUCTION,
	JOB_SUCCESSFULLY_DELETED,

	//Job Creation GUI
	INVENTORY_JOB_CREATION_TITLE,
	INVENTORY_JOB_CREATION_MONEY_JOB_ICON_NAME,
	INVENTORY_JOB_CREATION_MONEY_JOB_ICON_LORE,
	INVENTORY_JOB_CREATION_ITEMS_JOB_ICON_NAME,
	INVENTORY_JOB_CREATION_ITEMS_JOB_ICON_LORE,
	MONEY_PAYMENT_AMOUNT_QUESTION,
	MONEY_REWARD_ERROR_NEGATIVE,
	MONEY_REWARD_NOT_ENOUGH,
	MONEY_REWARD_NOT_A_NUMBER,

	//Items Reward Preview GUI
	INVENTORY_ITEMS_REWARD_PREVIEW_TITLE,

	//Items Reward Offer GUI
	INVENTORY_ITEMS_REWARD_OFFER_TITLE,
	INVENTORY_ITEMS_JOB_NO_ITEMS_WARNING,
	INVENTORY_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_NAME,
	INVENTORY_ITEMS_REWARD_OFFER_CONFIRMATION_ITEM_LORE,

	//Goal Enchantment Selection GUI
	INVENTORY_GOAL_ENCHANTMENT_SELECTION_TITLE,
	INVENTORY_GOAL_ENCHANTMENT_SELECTION_ITEM_LORE,
	ENTER_ENCHANTMENT_LEVEL,
	ENCHANTMENT_LEVEL_NOT_A_NUMBER,
	ENCHANTMENT_LEVEL_OUT_OF_BOUNDS,

	//Goal Customization GUI
	INVENTORY_GOAL_CUSTOMIZATION_TITLE,
	INVENTORY_GOAL_CUSTOMIZATION_CURRENT_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_NO_CURRENT_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_FINISH_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_TYPE_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_TYPE_ITEM_LORE,
	INVENTORY_GOAL_CUSTOMIZATION_AMOUNT_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_AMOUNT_ITEM_LORE,
	INVENTORY_GOAL_CUSTOMIZATION_ENCHANTMENTS_ITEM_NAME,
	INVENTORY_GOAL_CUSTOMIZATION_ENCHANTMENTS_ITEM_LORE,
	ITEM_GOAL_FORMAT_QUESTION,
	ITEM_GOAL_INVALID,

	//Item Palette GUI
	INVENTORY_ITEM_PALETTE_TITLE,
	INVENTORY_ITEM_PALETTE_NEXT_ITEM_NAME,
	INVENTORY_ITEM_PALETTE_BACK_ITEM_NAME,
	INVENTORY_ITEM_PALETTE_ENGLISH_SEARCH_ITEM_NAME,

	//Goal Amount GUI
	INVENTORY_GOAL_AMOUNT_TITLE,
	INVENTORY_GOAL_AMOUNT_FINISH_ITEM_NAME,
	INVENTORY_GOAL_AMOUNT_FINISH_ITEM_LORE,
	INVENTORY_GOAL_AMOUNT_NUMERIC_AMOUNT_TITLE,
	
	//Job Added Notifiers GUI
	INVENTORY_JOB_ADDED_NOTIFIERS_TITLE,
	INVENTORY_JOB_ADDED_NOTIFIERS_ALL_ITEM_NAME,
	INVENTORY_JOB_ADDED_NOTIFIERS_ALL_ITEM_LORE,
	INVENTORY_JOB_ADDED_NOTIFIERS_SUBSCRIPTIONS_ITEM_NAME,
	INVENTORY_JOB_ADDED_NOTIFIERS_SUBSCRIPTIONS_ITEM_LORE,
	INVENTORY_JOB_ADDED_NOTIFIERS_NONE_ITEM_NAME,
	INVENTORY_JOB_ADDED_NOTIFIERS_NONE_ITEM_LORE,
	INVENTORY_JOB_ADDED_NOTIFIERS_SELECTED,
	
	//Player Subscriptions GUI
	INVENTORY_PLAYER_SUBSCRIPTIONS_TITLE,
	INVENTORY_PLAYER_SUBSCRIPTIONS_YOUR_SUBSCRIPTIONS_ITEM_NAME,
	INVENTORY_PLAYER_SUBSCRIPTIONS_YOUR_SUBSCRIPTIONS_ITEM_LORE,
	INVENTORY_PLAYER_SUBSCRIPTIONS_SUBSCRIBE_ITEM_NAME,
	INVENTORY_PLAYER_SUBSCRIPTIONS_SUBSCRIBE_ITEM_LORE,
	INVENTORY_PLAYER_SUBSCRIPTIONS_UNSUBSCRIBE_ITEM_NAME,
	INVENTORY_PLAYER_SUBSCRIPTIONS_UNSUBSCRIBE_ITEM_LORE,
	
	//Subscribe Item Palette
	INVENTORY_SUBSCRIBE_ITEM_PALETTE_TITLE,
	INVENTORY_SUBSCRIBE_ITEM_PALETTE_SUBSCRIBE_QUESTION,
	INVENTORY_SUBSCRIBE_ITEM_PALETTE_SUBSCRIBE_ITEM_NAME,
	INVENTORY_SUBSCRIBE_ITEM_PALETTE_SUBSCRIBE_ITEM_LORE,
	
	//Unsubscribe from Items Item Palette,
	INVENTORY_UNSUBSCRIBE_ITEM_PALETTE_TITLE,
	INVENTORY_UNSUBSCRIBE_ITEM_PALETTE_UNSUBSCRIBE_QUESTION,
	INVENTORY_UNSUBSCRIBE_ITEM_PALETTE_UNSUBSCRIBE_ITEM_NAME,
	INVENTORY_UNSUBSCRIBE_ITEM_PALETTE_UNSUBSCRIBE_ITEM_LORE;
	

	public static final MessageKey[] VALUES = values();
}