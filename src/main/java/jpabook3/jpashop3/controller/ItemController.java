package jpabook3.jpashop3.controller;

import jpabook3.jpashop3.controller.form.BookForm;
import jpabook3.jpashop3.domain.item.Book;
import jpabook3.jpashop3.domain.item.Item;
import jpabook3.jpashop3.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());

        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());

        itemService.save(book);

        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);

        return "items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String edit(@PathVariable("id")Long id,Model model){
        Book book =(Book) itemService.findOne(id);

        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());

        model.addAttribute("form",form  );

        return "items/updateItemForm";
    }


    @PostMapping("/items/{id}/edit")
    public String updateItem(BookForm form){
        Book book = new Book();
        book.setStockQuantity(form.getStockQuantity());
        book.setPrice(form.getPrice());
        book.setId(form.getId());
        book.setName(form.getName());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.save(book);

        return "redirect:/items";
    }

    @PostMapping("/items/{id}/edit2")
    public String updateItem2(BookForm form){
        itemService.updateItem2(form.getId(),form.getName(),form.getIsbn(),
                form.getAuthor(),form.getStockQuantity(), form.getPrice());

        return "redirect:/items";
    }
}
