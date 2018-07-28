package com.discover.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.discover.fridge.utilities.Item;

public class DBInstance {

	Set<Item> items;
	Set<Item> forgottenItems;
	Map<String, Item> uuidMap;

	public DBInstance() {
		reload();
	}

	// This reload will pull the items from persistent database and add them to
	// our maps.
	protected void reload() {
		items = new TreeSet<>();
		forgottenItems = new HashSet<>();
		uuidMap = new HashMap<>();
	}

	public Item getItemByUUID(String itemUUID) {
		return uuidMap.get(itemUUID);
	}

	public void removeFromItems(Item item) {
		removeFromUUIDMap(item.itemUUID);
		Optional<Item> key = getKey(item.itemType, forgottenItems);
		// If it is present in our forgotten items then check and remove from
		// Forgotten set.
		if (key.isPresent()) {
			checkAndRemoveForgotten(item, key);
			return;
		}
		// If it is a valid item and quantity then update total quantity and
		// fillfactor in tracking set.
		key = getKey(item.itemType, items);
		if (key.isPresent()) {
			checkAndRemoveItems(item, key);
		}
	}

	public void addItemTypeAndUUID(Item item) {
		addToUUIDMap(item);
		Optional<Item> key = getKey(item.itemType, items);
		// If this item is not available in our tracking set.
		if (!key.isPresent()) {
			// If this item is in the forgotten Set then just update it and
			// return.
			key = getKey(item.itemType, forgottenItems);
			if (key.isPresent()) {
				checkAndAddToForgotten(item, key);
			} else { // Otherwise just add it to our tracking set.
				items.add(item);
			}
			return;
		}
		// If it is a valid item and quantity then update total quantity and
		// fillfactor.
		checkAndAddToItems(item, key);
	}

	public Object[] getItems(Double fillFactor) {
		Iterator<Item> it = items.iterator();
		List<Item> list = new ArrayList<>();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.fillFactor > fillFactor) {
				break;
			}
			list.add(item);
		}
		return list.toArray();
	}

	public Double getFillFactor(Item key) {
		Optional<Item> item = getKey(key.itemType, items);
		if (item.isPresent()) {
			return item.get().fillFactor;
		}
		return 0.0;
	}

	public void moveItemToForgotten(Item key) {
		Optional<Item> item = getKey(key.itemType, items);
		if (item.isPresent()) {
			items.remove(item.get());
			forgottenItems.add(item.get());
		}
	}

	private void checkAndAddToItems(Item item, Optional<Item> key) {
		checkAndAdd(item, key, items);
	}

	private void checkAndAddToForgotten(Item item, Optional<Item> key) {
		checkAndAdd(item, key, forgottenItems);
	}

	private void checkAndRemoveItems(Item item, Optional<Item> key) {
		checkAndRemove(item, key, items);
	}

	private void checkAndRemoveForgotten(Item item, Optional<Item> key) {
		checkAndRemove(item, key, forgottenItems);
	}

	protected void checkAndRemove(Item item, Optional<Item> key,
			Set<Item> inputSet) {
		if (item.quantity > 0) {
			Item keyItem = key.get();
			keyItem.quantity -= item.quantity;
			keyItem.fillFactor = calculateFillFactor(keyItem.fillFactor
					- item.fillFactor, keyItem.quantity);
		}
	}

	protected void checkAndAdd(Item item, Optional<Item> key, Set<Item> inputSet) {
		if (item.quantity > 0) {
			Item keyItem = key.get();
			keyItem.quantity += item.quantity;
			keyItem.fillFactor = calculateFillFactor(keyItem.fillFactor
					+ item.fillFactor, keyItem.quantity);
		}
	}

	private void removeFromUUIDMap(String itemUUID) {
		uuidMap.remove(itemUUID);
	}

	private void addToUUIDMap(Item item) {
		uuidMap.put(item.itemUUID, item);
	}

	private Optional<Item> getKey(long itemType, Set<Item> inputSet) {
		return inputSet.stream().filter(e -> e.itemType == itemType)
				.findFirst();
	}

	private Double calculateFillFactor(double d, int quantity) {
		if (quantity == 0) {
			return 0.0;
		}
		return Double.valueOf(d / (double) quantity);
	}

}