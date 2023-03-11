package jpabook3.jpashop3.service;

import jpabook3.jpashop3.domain.item.Book;
import jpabook3.jpashop3.domain.item.Item;
import jpabook3.jpashop3.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public Long save(Item item){
        itemRepository.save(item);
        return item.getId();
    }

    @Transactional
    public void updateItem(Long itemId, Book param){
        Book book =(Book) itemRepository.findOne(itemId);

        book.setName(param.getName());
        book.setIsbn(param.getIsbn());
        book.setAuthor(param.getAuthor());
        book.setStockQuantity(param.getStockQuantity());
        book.setPrice(param.getPrice());
    }

    @Transactional
    public void updateItem2(Long itemId,String name,String isbn,String author,int stockQuantity,int price){
        Book book =(Book) itemRepository.findOne(itemId);

        book.setName(name);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
    }



    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }





}
