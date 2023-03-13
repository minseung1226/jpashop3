package jpabook3.jpashop3.repository.queryrepository;

import jakarta.persistence.EntityManager;
import jpabook3.jpashop3.repository.querydto.OrderFlatDto;
import jpabook3.jpashop3.repository.querydto.OrderItemQueryDto;
import jpabook3.jpashop3.repository.querydto.OrderQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;


    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();

        result.forEach(o->{
            List<OrderItemQueryDto> list=findOrderItems(o.getOrderId());
            o.setOrderItems(list);
        });

        return result;



    }
    public List<OrderQueryDto> findAllByDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        List<Long> orderIds = result.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select " +
                                "new jpabook3.jpashop3.repository.querydto.OrderItemQueryDto" +
                                "(oi.order.id,i.name,oi.orderPrice,oi.count) from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds).getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(oi -> oi.getOrderId()));

        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }
    

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook3.jpashop3.repository.querydto.OrderQueryDto" +
                        "(o.id,m.name,o.orderDate,o.status,d.address)" +
                        " from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderQueryDto.class).getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                "select " +
                        "new jpabook3.jpashop3.repository.querydto.OrderItemQueryDto" +
                        "(oi.order.id,i.name,oi.orderPrice,oi.count) from OrderItem oi " +
                        "join oi.item i " +
                        "where oi.order.id=:orderId",OrderItemQueryDto.class)
                .setParameter("orderId",orderId).getResultList();

    }

    public List<OrderFlatDto> findAllByDto_flat() {

        return em.createQuery(
                "select new jpabook3.jpashop3.repository.querydto.OrderFlatDto" +
                        "(o.id,m.name,o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join o.orderItems oi " +
                        "join oi.item i", OrderFlatDto.class).getResultList();
    }
}
