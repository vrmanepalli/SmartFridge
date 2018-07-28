package com.discover.fridge.utilities;

public class Item implements Comparable<Item> {

	public long itemType;
	public String itemUUID;
	public String name;
	public Double fillFactor;
	public int quantity = 0;

	public Item(long itemType, String itemUUID, String name, Double fillFactor) {
		this.itemType = itemType;
		this.itemUUID = itemUUID;
		this.name = name;
		this.fillFactor = fillFactor;
		if (fillFactor.doubleValue() > 0.0) {
			quantity = 1;
		}
	}

	public long getItemType() {
		return itemType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Item)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		return hashCode() == ((Item) obj).hashCode();
	}

	@Override
	public int hashCode() {
		return Long.valueOf(itemType).hashCode();
	}

	@Override
	public int compareTo(Item o) {
		if (o instanceof Item) {
			Item oCopy = (Item) o;
			if (hashCode() == oCopy.hashCode()) {
				return 0;
			}
			if (fillFactor <= oCopy.fillFactor) {
				return -1;
			}
		}
		// Return 1 if this item's fillfactor is greater than o's or not of Item
		// type.
		return 1;
	}

	@Override
	public String toString() {
		return itemType + ": " + itemUUID + " " + name + " " + fillFactor + " "
				+ quantity;
	}

}
