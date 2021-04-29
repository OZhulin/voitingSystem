package ru.zhulin.oleg.restsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Dish extends NamedParentEntity{
    @NotNull
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @JsonIgnore
    private Menu menu;

    public Dish(Long id, String name, BigDecimal price){
        super(id, name);
        this.price = price;
        this.menu = null;
    }
}
