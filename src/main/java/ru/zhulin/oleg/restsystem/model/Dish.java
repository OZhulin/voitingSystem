package ru.zhulin.oleg.restsystem.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
public class Dish extends ParentEntity{
    private BigDecimal price;
}
