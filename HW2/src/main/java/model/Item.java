package model;

import java.sql.Timestamp;

public class Item {
	private int itemID;
	private String type;
	private String itemName;
	private int price;
	private int recorderID;
	private Timestamp time;

	public Item() {
	}

	public Item(String type, String itemName, int price, int recorderID, Timestamp time) {
		this.type = type;
		this.itemName = itemName;
		this.price = price;
		this.recorderID = recorderID;
		this.time = time;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRecorderID() {
		return recorderID;
	}

	public void setRecorderID(int recorderID) {
		this.recorderID = recorderID;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

}
