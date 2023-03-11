package jpabook3.jpashop3.controller;

import jpabook3.jpashop3.domain.Member;
import jpabook3.jpashop3.domain.Order;
import jpabook3.jpashop3.domain.OrderSearch;
import jpabook3.jpashop3.domain.item.Item;
import jpabook3.jpashop3.service.ItemService;
import jpabook3.jpashop3.service.MemberService;
import jpabook3.jpashop3.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }


    @PostMapping("/order")
    public String order(Long memberId,Long itemId,int count){
         orderService.order(memberId, itemId, count);
         return "redirect:/orders";
    }


    @GetMapping("/orders")
    public String orderList(OrderSearch orderSearch,Model model){
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders",orders);
        log.info("order.getOrderItems.size={}",orders.get(0).getOrderItems().size());
        log.info("order.size={}",orders.size());
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String orderCancel(@PathVariable Long orderId){
        orderService.cancel(orderId);

        return "redirect:/orders";
    }


}
