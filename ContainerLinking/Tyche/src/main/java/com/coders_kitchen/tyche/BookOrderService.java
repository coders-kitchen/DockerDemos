package com.coders_kitchen.tyche;

import com.coders_kitchen.tyche.entity.BookOrder;
import com.coders_kitchen.tyche.repository.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("demoAccountRequestService")
public class BookOrderService {

	@Autowired
	BookOrderRepository bookOrderRepository;

	public void insertDemoAccountRequest(BookOrder bookOrder) {
		bookOrderRepository.save(bookOrder);
	}
}
