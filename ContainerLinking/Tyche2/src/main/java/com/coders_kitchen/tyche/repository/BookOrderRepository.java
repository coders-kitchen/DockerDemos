package com.coders_kitchen.tyche.repository;

import com.coders_kitchen.tyche.entity.BookOrder;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.MongoRepository;

@Import(MongoDBConfiguration.class)
public interface BookOrderRepository extends MongoRepository<BookOrder, String> {
}
