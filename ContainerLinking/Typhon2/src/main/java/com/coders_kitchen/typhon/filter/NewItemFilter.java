package com.coders_kitchen.typhon.filter;

import org.apache.abdera.model.Entry;
import org.apache.camel.Exchange;

import java.util.Date;

public class NewItemFilter {

	Date lastEvent = null;

	/**
	 * Tests the blogs if its a good blog entry or not
	 */
	public boolean isNewBlog(Exchange exchange) {
		Entry entry = exchange.getIn().getBody(Entry.class);
		Date updated = entry.getUpdated();
		if (lastEvent == null || lastEvent.getTime() < updated.getTime()) {
			lastEvent = updated;
			return true;
		}
		return false;
	}
}
