package jpabook3.jpashop3.service;

import jakarta.persistence.EntityManager;
import jpabook3.jpashop3.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    void updateTest(){

        //변경감지 dirty checking
        Book book = em.find(Book.class, 1L);
        book.setName("kim");


    }
}
