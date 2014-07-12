package com.coders_kitchen.typhon.entity;

import lombok.Data;

@Data
public class BookOrder {

	private String id;

	private String firstName;

	private String lastName;

	private String title;

	private String email;

	private Long timestamp;

	public BookOrder() {
		this.timestamp = System.currentTimeMillis();
	}

}
