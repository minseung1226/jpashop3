package jpabook3.jpashop3.repository.queryrepository;

import jakarta.persistence.EntityManager;
import jpabook3.jpashop3.repository.querydto.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {

    private final EntityManager em;


    public List<OrderSimpleQueryDto> findOrderDto() {
        return em.createQuery(
                "select new jpabook3.jpashop3.repository.querydto.OrderSimpleQueryDto" +
                        "(o.id,m.name,o.orderDate,o.status,d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d",OrderSimpleQueryDto.class
        ).getResultList();
    }
}
