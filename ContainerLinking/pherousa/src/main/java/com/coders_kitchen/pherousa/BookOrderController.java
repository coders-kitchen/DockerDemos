package com.coders_kitchen.pherousa;

import com.coders_kitchen.pherousa.atom.AtomFeedProvisioningService;
import com.coders_kitchen.pherousa.entity.BookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookOrderController {

	private static Logger logger = LoggerFactory.getLogger(BookOrderController.class);

	@Autowired
	private BookOrderService bookOrderService;

	@Autowired
	private PollTimerService pollTimerService;

	@Autowired
	private AtomFeedProvisioningService atomFeedProvisioningService;

	@Scheduled(fixedRate = 10000)
	public void find() {
		Long lastPollTimestamp = pollTimerService.getLastPollTimestamp();
		List<BookOrder> allBookOrders = bookOrderService.findAllDemoAccountRequestsSince(lastPollTimestamp);

		Long nextPollTimestamp = lastPollTimestamp;
		for (BookOrder bookOrder : allBookOrders) {
			if (bookOrder.getTimestamp() > nextPollTimestamp) {
				nextPollTimestamp = bookOrder.getTimestamp();
			}
			logger.info("New order found. Adding order {} to feed", bookOrder.getId());
			atomFeedProvisioningService.provisionAtomFeed(bookOrder);
		}

		pollTimerService.setNextPollTimestamp(nextPollTimestamp);
	}

}
