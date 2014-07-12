package com.coders_kitchen.pherousa;

import com.coders_kitchen.pherousa.entity.BookOrder;
import com.coders_kitchen.pherousa.repository.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bookOrderService")
public class BookOrderService {

	@Autowired
	BookOrderRepository bookOrderRepository;

	public List<BookOrder> findAllDemoAccountRequestsSince(Long lastPoll) {
		return bookOrderRepository.findbookOrdersSince(lastPoll);
	}
}
