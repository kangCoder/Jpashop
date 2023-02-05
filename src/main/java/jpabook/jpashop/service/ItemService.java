package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품 서비스는 단순히 리포지토리에 일을 위임만 하는 클래스. 정말 필요한지는 생각해볼 필요가 있다...
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public void updateItem(Long itemId, String name, Integer price, Integer quantity) {
        Item findItem = itemRepository.findOne(itemId); //엔티티 조회. (준영속 컨텍스트)

        //변경 감지 (dirty checking)
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(quantity);
        findItem.setId(itemId);
        //사실 setter 를 이런 식으로 사용하면 안되고, findItem.change(price, name, quantity, ...) 등 의미있는 메서드를 만들어서 해야한다.
        //코드를 역추적하기 쉽기 때문..

        //flush를 하거나 다른 동작을 하지 않아도 됨. 변경을 감지하고 데이터가 알아서 수정된다.
        //트랜잭션이 커밋될 시점에 변경을 감지하고, 동작.

    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
