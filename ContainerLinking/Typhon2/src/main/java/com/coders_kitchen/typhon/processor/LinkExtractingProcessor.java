package com.coders_kitchen.typhon.processor;

import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Link;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class LinkExtractingProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Entry entry = exchange.getIn().getBody(Entry.class);
		if (entry == null)
			return;
		Link related = entry.getLink("related");
		exchange.getIn().setBody(related.getHref());
	}
}