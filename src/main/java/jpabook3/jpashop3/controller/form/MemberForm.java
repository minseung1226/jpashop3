package jpabook3.jpashop3.controller.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class MemberForm {
    @NotEmpty
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
