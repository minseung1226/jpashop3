package jpabook3.jpashop3.repository.querydto;

import jpabook3.jpashop3.domain.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;


    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
