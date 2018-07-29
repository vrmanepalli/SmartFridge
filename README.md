# SmartFridge is capable of keeping track of items and their fill status with respect to user activities.
# SmartFridge class is using DBInstanse helper class to perform all the operations that were in the Interface SmartFridgeManager.
# Item utility class is created with the implementation of Comparable Interface whose hashCode, equals and compareTo methods.
# A Map in DBInstance keeps the keys as UUID and values as Item which holds UUID, type, fillFactor, and name.
# Items TreeSet in DBInstance keeps the items with itemType as key but sorted by fillFactor.
# Forgotten Set in DBInstance keeps the items that were stopped tracking by user.
# Time complexity for addition or removal or item is O(n). But this can be made better O(logn) by replacing Items TreeSet<Item> with Items TreeMap<Item, Item> which takes additional O(n) memory. n is number of itemTypes.
# Space complexity is O(n). n is number of UUID items.
# Time Complexity for getItems is O(1).
