package com.coders_kitchen.tyche;

import com.coders_kitchen.tyche.entity.BookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class BookOrderController {

	private static Logger logger = LoggerFactory.getLogger(BookOrderController.class);

	@Autowired
	private BookOrderService bookOrderService;

	@RequestMapping
	public ModelAndView initModel() {
		return new ModelAndView("layout", "bookOrder", new BookOrder());
	}

	/*
	* curl --data "protocol=HTTPS&requestMode=POST&sourceIP=127.0.0.1&url=http://www.example.com/dlr&firstName=Peter&lastName=Panski&company=Firma&email=panski@example.com" 127.0.0.1:8080/create
	*/

	@RequestMapping("create")
	public ModelAndView create(@Valid BookOrder bookOrder, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ModelAndView("layout", "formErrors", bindingResult.getAllErrors());
		}
		logger.info("New request incoming. Adding book order {} to request store", bookOrder);
		bookOrderService.insertDemoAccountRequest(bookOrder);
		return new ModelAndView("response", "bookOrder", bookOrder);
	}
}
