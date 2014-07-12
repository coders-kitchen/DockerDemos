package com.coders_kitchen.typhon;

import com.coders_kitchen.typhon.entity.BookOrder;
import com.coders_kitchen.typhon.filter.NewItemFilter;
import com.coders_kitchen.typhon.processor.LinkExtractingProcessor;
import com.coders_kitchen.typhon.processor.RequestExtractor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	private CamelContext context;


	public static void main(String[] args) throws Exception {

		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		Application bean = ctx.getBean(Application.class);
		bean.createCamelContext().start();


	}

	protected CamelContext createCamelContext() throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("linkExtractor", new LinkExtractingProcessor());
		registry.put("newItemFilter", new NewItemFilter());
		registry.put("requestExtractor", new RequestExtractor());
		registry.put("requestExtractor", new RequestExtractor());
		registry.put("soutProcessor", new SoutProcessor());


		context = new DefaultCamelContext(registry);
		RouteBuilder routeBuilder = new RouteBuilder() {
			public void configure() throws Exception {
				from("atom://http://localhost:18082/atomhopper/bookorder/feed/?splitEntries=true&consumer.delay=10").
						filter().
						method("newItemFilter", "isNewBlog").to("seda:newFeeds");
				from("seda:newFeeds?concurrentConsumers=10").beanRef("linkExtractor").to("seda:newItemLinks");
				from("seda:newItemLinks").beanRef("requestExtractor").end().to("soutProcessor");
			}
		};

		context.addRoutes(routeBuilder);
		return context;
	}

	public class SoutProcessor implements Processor {

		@Override
		public void process(Exchange exchange) throws Exception {
			BookOrder bookOrder = (BookOrder) exchange.getIn().getBody();
			if(bookOrder == null) {
				return;
			}
			logger.info(bookOrder.toString());
		}
	}


}
