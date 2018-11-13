package ru.job4j.models;

public class Item {
	public static final Item EMPTY = new Item();
	private String id;
	private String name;
	private String description;
	private long create;
	private String author;

	public Item() {
		
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Item item = (Item) o;

		if (create != item.create) {
			return false;
		}
		if (id != null ? !id.equals(item.id) : item.id != null) {
			return false;
		}
		if (name != null ? !name.equals(item.name) : item.name != null) {
			return false;
		}
		if (description != null ? !description.equals(item.description) : item.description != null) {
			return false;
		}
		return author != null ? author.equals(item.author) : item.author == null;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (int) (create ^ (create >>> 32));
		result = 31 * result + (author != null ? author.hashCode() : 0);
		return result;
	}

	public Item(String name, String description) {
		this.name = name;
		this.description = description;
		//this.create = create;
	}

	public Item(String name, String description, String author) {
		this.name = name;
		this.description = description;
		this.author = author;
		//this.create = create;
	}

	public Item(String id, String name, String description, String author) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.author = author;
		//this.create = create;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreate() {
		return this.create; 
	}
	
	public String getId() {
		return this.id; 
	}
	
	public void setId(String id) {
		this.id = id;
	}

    public void setDescription(String description) {
        this.description = description;
    }
}