package com.coders_kitchen.pherousa.repository;

import com.coders_kitchen.pherousa.entity.BookOrder;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@Import(MongoDBConfiguration.class)
@RepositoryRestResource
public interface BookOrderRepository extends MongoRepository<BookOrder, String> {

		@Query("{ 'timestamp' : { $gt : ?0} }")
		List<BookOrder> findbookOrdersSince(Long lastPoll);

}
