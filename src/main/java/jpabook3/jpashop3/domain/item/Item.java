package jpabook3.jpashop3.domain.item;

import jakarta.persistence.*;
import jpabook3.jpashop3.domain.Category;
import jpabook3.jpashop3.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
public abstract class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories=new ArrayList<>();


    public void addQuantity(int quantity){
        this.stockQuantity+=quantity;
    }

    public void removeQuantity(int quantity){
        int restStock=this.stockQuantity-=quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity=restStock;
    }
}
