package com.practice.tacos.controller;

import com.practice.tacos.model.Order;
import com.practice.tacos.model.repository.OrderRepository;
import com.practice.tacos.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController
{
  @Autowired
  private OrderRepository orderRepository;

  /**
   * TO show order form to get info aabout user and order.
   * @param model
   * @return
   */
  @GetMapping("/current")
  public String orderForm(Model model)
  {
    //model.addAttribute("order", new Order());
    return "orderForm";
  }

  /**
   * TO save order once order form is submitted and order is complete.
   * @param order
   * @param errors
   * @param sessionStatus
   * @return
   */
  @PostMapping()
  public  String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus, @AuthenticationPrincipal User user)
  {
    if (errors.hasErrors())
    {
      return "orderForm";
    }

    order.setUser(user);
    orderRepository.save(order);
    sessionStatus.setComplete();
    log.info("Order submitted: " + order);
    return "redirect:/";
  }
}
