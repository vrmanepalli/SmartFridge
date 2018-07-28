package com.discover.fridge;

import com.discover.db.DBInstance;
import com.discover.fridge.utilities.Item;

public class SmartFridge implements SmartFridgeManager {
	
	public static void main(String[] args) {
		SmartFridge fridge = new SmartFridge();
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		System.out.println("**********************");
		printItems(fridge);
		System.out.println("**********************");
		fridge.handleItemAdded(44962480055L, "0MHL8", "Bear", 0.5d);
		printItems(fridge);
		System.out.println("**********************");
		fridge.handleItemRemoved("0KHL8");
		printItems(fridge);
		System.out.println("**********************");
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.5d);
		printItems(fridge);
		System.out.println("**********************");
		fridge.forgetItem(44962480055L);
		printItems(fridge);
		System.out.println("**********************");
		fridge.handleItemAdded(44762480055L, "0LHL8", "Water", 0.2d);
		printItems(fridge);
		System.out.println("**********************");
		fridge.forgetItem(44762480055L);
		printItems(fridge);
		System.out.println("**********************");
		fridge.handleItemAdded(44962480065L, "0MYL8", "Chicken", 1.5d);
		printItems(fridge);
		System.out.println("**********************");
		System.out.println(fridge.getFillFactor(44762480055L));
		System.out.println("**********************");
	}

	protected static void printItems(SmartFridge fridge) {
		for(Object obj:fridge.getItems(1.9)) {
			Item item = (Item) obj;
			System.out.println(item.toString());
		}
	}

	DBInstance db;

	public SmartFridge() {
		db = new DBInstance();
	}

	@Override
	public void handleItemRemoved(String itemUUID) {
		Item item = db.getItemByUUID(itemUUID);
		if (item == null) {
			return;
		}
		db.removeFromItems(item);
	}

	@Override
	public void handleItemAdded(long itemType, String itemUUID, String name,
			Double fillFactor) {
		Item item = new Item(itemType, itemUUID, name, fillFactor);
		db.addItemTypeAndUUID(item);
	}

	@Override
	public Object[] getItems(Double fillFactor) {
		return db.getItems(fillFactor);
	}

	@Override
	public Double getFillFactor(long itemType) {
		Item key = new Item(itemType, null, null, 0.0);
		return db.getFillFactor(key);
	}

	@Override
	public void forgetItem(long itemType) {
		Item key = new Item(itemType, null, null, 0.0);
		db.moveItemToForgotten(key);
	}

}
