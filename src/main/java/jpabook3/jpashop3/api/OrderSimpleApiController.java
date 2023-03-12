package jpabook3.jpashop3.api;

import jpabook3.jpashop3.domain.Address;
import jpabook3.jpashop3.domain.Order;
import jpabook3.jpashop3.domain.OrderSearch;
import jpabook3.jpashop3.domain.OrderStatus;
import jpabook3.jpashop3.repository.OrderRepository;
import jpabook3.jpashop3.repository.querydto.OrderSimpleQueryDto;
import jpabook3.jpashop3.repository.queryrepository.OrderSimpleQueryRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * xToOne 관계(OneToOne,ManyToOne)
 * Order
 * Order ->Member
 * Order ->Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository queryRepository;


    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1(){
        List<Order> list = orderRepository.findAllByString(new OrderSearch());

        for (Order order : list) {
            order.getMember().getName();
        }

        return list;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2(){
        List<SimpleOrderDto> list = orderRepository.findAllByString(new OrderSearch())
                .stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());

        return list;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3(){
        List<SimpleOrderDto> result = orderRepository.findAllWithMemberDelivery()
                .stream().map(SimpleOrderDto::new).collect(Collectors.toList());

        return result;
    }


    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {

        return queryRepository.findOrderDto();
//        return orderRepository.findOrderDto();
    }
    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order o) {
            orderId=o.getId();
            name=o.getMember().getName();
            orderDate=o.getOrderDate();
            orderStatus=o.getStatus();
            address=o.getDelivery().getAddress();
        }
    }


}
