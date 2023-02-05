package jpabook.jpashop.service;

import javax.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception {
        Book book = em.find(Book.class, 1L); //준영속 엔티티! (이미 DB에 저장되어서 식별자가 존재함.)

        /**
         * 준영속 엔티티를 수정하는 2가지 방법
         * 1. 변경 감지 (dirty checking)
         * 2. 병합 (merge)
         */

        book.setName("asdasd"); //변경감지 (dirty checking)
        //변경감지: 영속성 컨텍스트에서 엔티티를 다시 조회하고 데이터를 수정.

    }
}
