package com.discover.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.discover.fridge.utilities.Item;

public class SmartFridge {

	@Test
	public void handleItemAddedTest() {
		com.discover.fridge.SmartFridge fridge = new com.discover.fridge.SmartFridge();

		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		assertEquals("The fillFactor of Water is 0.8d", 0.8d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemAdded(44762480055L, "0KML8", "Water", 0.9d);
		assertEquals("The fillFactor of Water is 0.85d", 0.85d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemAdded(54762480055L, "0LML8", "Oil", 0.5d);
		assertEquals("The fillFactor of Water is 0.85d", 0.85d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemAdded(0L, null, null, 0.0d);
		assertEquals("The fillFactor of Invalid item is 0.00d", 0.00d,
				fridge.getFillFactor(0L), 0.0d);

		assertEquals("The fillFactor of non existing item is 0.00d", 0.00d,
				fridge.getFillFactor(1L), 0.0d);
	}

	@Test
	public void handleItemRemovedTest() {
		com.discover.fridge.SmartFridge fridge = new com.discover.fridge.SmartFridge();

		fridge.handleItemRemoved("0KHL8");
		assertEquals("The fillFactor of Water item is 0 in Empty Fridge", 0.00d,
				fridge.getFillFactor(44762480055L), 0.0d);
		
		fridge.handleItemAdded(0L, null, null, 0.1d);
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		fridge.handleItemAdded(0L, null, null, 0.0d);
		fridge.handleItemAdded(44762480055L, "0KML8", "Water", 0.9d);
		fridge.handleItemRemoved("0KML8");
		assertEquals("The fillFactor of Water item is 0.80d", 0.80d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemAdded(54762480055L, "0LML8", "Oil", 0.5d);
		assertEquals("The fillFactor of Oil item is 0.50d", 0.50d,
				fridge.getFillFactor(54762480055L), 0.0d);

		fridge.handleItemRemoved("0KHL8");
		assertEquals("The fillFactor of Water item is 0.00d", 0.00d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemRemoved("0KHL8");
		assertEquals("The fillFactor of Water item is 0.00d", 0.00d,
				fridge.getFillFactor(44762480055L), 0.0d);

		fridge.handleItemRemoved("0LML8");
		assertEquals("The fillFactor of Oil item is 0.00d", 0.00d,
				fridge.getFillFactor(54762480055L), 0.0d);

		assertEquals("The fillFactor of invalid item is 0.10d", 0.10d,
				fridge.getFillFactor(0L), 0.0d);
		fridge.handleItemRemoved(null);
		assertEquals("The fillFactor of invalid item is 0.10d", 0.10d,
				fridge.getFillFactor(0L), 0.0d);
	}

	@Test
	public void getItemsTest() {
		com.discover.fridge.SmartFridge fridge = new com.discover.fridge.SmartFridge();

		assertEquals("The number of items is equal for 2.0 fillFactor on Empty Fridge", 0,
				fridge.getItems(2.0d).length);
		
		fridge.handleItemAdded(0L, null, null, 0.1d);
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		fridge.handleItemAdded(0L, null, null, 0.0d);
		fridge.handleItemAdded(44762480055L, "0KML8", "Water", 0.9d);

		assertEquals("The number of items is 2 for 2.0 fillFactor", 2,
				fridge.getItems(2.0d).length);
		assertEquals("The number of items is 21for 0.0 fillFactor", 1,
				fridge.getItems(0.1d).length);
		assertEquals("The number of items is 0 for 0.0 fillFactor", 0,
				fridge.getItems(0.0d).length);
		assertEquals("The first item's fillFactor is 0.1d for 0.1d fillFactor",
				0.10d, ((Item) fridge.getItems(0.1d)[0]).fillFactor, 0.0d);
	}
	
	@Test
	public void getFillFactorTest() {
		com.discover.fridge.SmartFridge fridge = new com.discover.fridge.SmartFridge();
		
		assertEquals("The fillFactor of non existing item is 0.00d in Empty Fridge.", 0.00d,
				fridge.getFillFactor(97293L), 0.0d);
		fridge.handleItemAdded(0L, null, null, 0.1d);
		assertEquals("The fillFactor of invalid item is 0.10d", 0.10d,
				fridge.getFillFactor(0L), 0.0d);
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		assertEquals("The fillFactor of Water item is 0.80d", 0.80d,
				fridge.getFillFactor(44762480055L), 0.0d);
		fridge.handleItemAdded(0L, null, null, 0.0d);
		assertEquals("The fillFactor of invalid item is 0.10d", 0.10d,
				fridge.getFillFactor(0L), 0.0d);
		fridge.handleItemAdded(44762480055L, "0KML8", "Water", 0.9d);
		assertEquals("The fillFactor of Water item is 0.85d", 0.85d,
				fridge.getFillFactor(44762480055L), 0.0d);
	}
	
	@Test
	public void forgetItemTest() {
		com.discover.fridge.SmartFridge fridge = new com.discover.fridge.SmartFridge();
		
		fridge.forgetItem(0L);
		assertEquals("The array length is 0 for empty fridge",
				0, fridge.getItems(2.0d).length);
		
		fridge.handleItemAdded(0L, null, null, 0.1d);
		fridge.handleItemAdded(44762480055L, "0KHL8", "Water", 0.8d);
		fridge.handleItemAdded(0L, null, null, 0.0d);
		fridge.handleItemAdded(44762480055L, "0KML8", "Water", 0.9d);
		
		fridge.forgetItem(0L);
		assertEquals("The first item's fillFactor is 0.85d for 2.0 fillFactor",
				1.7d, ((Item) fridge.getItems(2.0d)[0]).fillFactor, 0.0d);
	}
}
