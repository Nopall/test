package id.keyta.testad.model;

import com.google.gson.annotations.SerializedName;

public class ItemRequest {
	private String id;
	private String name;

	public ItemRequest(String id, String name) {
		this.id = id;
		this.name = name;
	}
}
