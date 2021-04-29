package ru.zhulin.oleg.restsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(name = "menus_restaurant_date_idx", columnNames = {"restaurant_id", "date"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Menu extends ParentEntity{
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @Column(name = "date", columnDefinition = "timestamp default now()")
    private LocalDate date;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu")
    private Set<Dish> dishSet;

    public Menu(Long id, Restaurant restaurant, LocalDate date, Set<Dish> dishSet) {
        super(id);
        this.restaurant = restaurant;
        this.date = date;
        this.dishSet = dishSet;
    }

}
