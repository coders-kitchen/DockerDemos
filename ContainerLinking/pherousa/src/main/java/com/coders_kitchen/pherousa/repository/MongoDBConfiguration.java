package com.coders_kitchen.pherousa.repository;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Configuration
@EnableMongoRepositories("com.coders_kitchen.pherousa")
@Import(RepositoryRestMvcConfiguration.class)
@PropertySource({ "classpath:mongodb.properties" })
public class MongoDBConfiguration extends AbstractMongoConfiguration {

	@Value("${databaseName}")
	private String databaseName;

	@Value("${host:localhost}")
	private String host;

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public Mongo mongo() throws Exception {
		Mongo mongo = new Mongo(host);
		return mongo;
	}
}
