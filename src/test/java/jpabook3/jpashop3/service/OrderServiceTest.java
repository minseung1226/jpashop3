package jpabook3.jpashop3.service;

import jakarta.persistence.EntityManager;
import jpabook3.jpashop3.domain.Address;
import jpabook3.jpashop3.domain.Member;
import jpabook3.jpashop3.domain.Order;
import jpabook3.jpashop3.domain.OrderStatus;
import jpabook3.jpashop3.domain.item.Book;
import jpabook3.jpashop3.domain.item.Item;
import jpabook3.jpashop3.exception.NotEnoughStockException;
import jpabook3.jpashop3.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;




    @Test
    @Rollback(value = false)
    void 상품주문(){
        Member member = createMember();
        Item book = createBook("책1", 10000, 10);

        int orderCount=2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(findOrder.getOrderItems().size()).isEqualTo(1);
        assertThat(findOrder.getTotalPrice()).isEqualTo(20000);
        assertThat(book.getStockQuantity()).isEqualTo(8);


    }



    @Test
    void 상품주문_재고수량초과(){
        Member member = createMember();
        Item book = createBook("책1", 10000, 10);

        int orderCount=11;

        assertThatThrownBy(()->orderService.order(member.getId(),book.getId(),orderCount))
                .isInstanceOf(NotEnoughStockException.class);
    }

    @Test
    void 주문취소(){
        Member member = createMember();
        Item book = createBook("책1", 10000, 10);
        int orderCount=2;

        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        orderService.cancel(orderId);

        Order findOrder = orderRepository.findOne(orderId);

        assertThat(findOrder.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    private Item createBook(String name, int price, int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

}