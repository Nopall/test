package id.keyta.testad.model;

import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ItemResponse implements Serializable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private Integer id;

	public String getName(){
		return name;
	}

	public Integer getId(){
		return id;
	}
}