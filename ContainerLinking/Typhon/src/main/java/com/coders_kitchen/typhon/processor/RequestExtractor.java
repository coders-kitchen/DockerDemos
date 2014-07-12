package com.coders_kitchen.typhon.processor;

import com.coders_kitchen.typhon.entity.BookOrder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class RequestExtractor implements Processor {

	private static Logger logger = LoggerFactory.getLogger(RequestExtractor.class);

	public void process(Exchange exchange) throws Exception {
		String itemURL= exchange.getIn().getBody(String.class);
		if (itemURL == null)
			return;
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Resource<BookOrder>> responseEntity =
		restTemplate.exchange(itemURL, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<BookOrder>>() {}, Collections.emptyMap());
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			Resource<BookOrder> userResource = responseEntity.getBody();
			BookOrder demoAccountRequest = userResource.getContent();
			exchange.getIn().setBody(demoAccountRequest);
		} else {
			logger.error(responseEntity.getStatusCode().toString());
		}

	}
}