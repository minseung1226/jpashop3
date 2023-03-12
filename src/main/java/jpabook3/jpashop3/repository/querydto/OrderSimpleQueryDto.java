package jpabook3.jpashop3.repository.querydto;

import jpabook3.jpashop3.domain.Address;
import jpabook3.jpashop3.domain.Order;
import jpabook3.jpashop3.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderSimpleQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public OrderSimpleQueryDto(Long id,String name,LocalDateTime orderDate,OrderStatus status,Address address) {
        this.orderId=id;
        this.name=name;
        this.orderDate=orderDate;
        this.orderStatus=status;
        this.address=address;
    }
}
