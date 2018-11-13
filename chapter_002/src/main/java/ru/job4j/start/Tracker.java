package ru.job4j.start;

import ru.job4j.models.*;
import java.util.*;

public class Tracker extends Item implements ITracker {
	private static final Random RN = new Random();
	private List<Item> items = new ArrayList<>();

	public Item add(Item item) {
		item.setId(generateId());
		this.items.add(item);
		return item;
	}

	public void replace(String id, Item item) {
		if (findById(id) != Item.EMPTY) {
			for (int index = 0; index != this.items.size(); index++) {
				if (this.items.get(index) != null && this.items.get(index).getId().equals(id)) {
					this.items.set(index, item);
					break;
				}
			}
		}
	}

	public void edit(Item fresh) {
		for (int index = 0; index != items.size(); index++) {
			Item item = items.get(index);
			if (item != null && item.getId().equals(fresh.getId())) {
				String author = items.get(index).getAuthor();
				items.set(index, fresh);
				fresh.setAuthor(author);
				break;
			}
		}
	}

	public void delete(String id) {
		for (int index = 0; index != this.items.size(); index++) {
			if (this.items.get(index) != null && items.get(index).getId().equals(id)) {
				this.items.remove(index);
				break;
			}
		}
	}

	public List<Item> getAll() {
		return this.items;
	}

	public Item findById(String id) {
		Item result = Item.EMPTY;
		for (Item item : items) {
			if (item != null && item.getId().equals(id)) {
				result = item;
				break;
			}
		}
		return result;
	}

	public Item findByName(String name) {
		Item result = Item.EMPTY;
		for (Item item : items) {
			if (item != null && item.getName().equals(name)) {
				result = item;
				break;
			}
		}
		return result;
	}

	private String generateId() {
		return String.valueOf(System.currentTimeMillis() + RN.nextInt(100));
	}
}