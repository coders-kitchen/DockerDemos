package com.coders_kitchen.tyche.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

@Data
public class BookOrder {

	@Id
	private String id;

	@NotEmpty(message = "First name is required.")
	private String firstName;

	@NotEmpty(message = "Last name is required.")
	private String lastName;

	@NotEmpty(message = "Title is required.")
	private String title;

	@NotEmpty(message = "E-Mail is required.")
	@Email(message = "E-Mail must be valid.")
	private String email;

	private Long timestamp;

	public BookOrder() {
		this.timestamp = System.currentTimeMillis();
	}

}
