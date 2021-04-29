package ru.zhulin.oleg.restsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(name = "restaurants_name_idx", columnNames = "name")})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Restaurant extends NamedParentEntity{
    public Restaurant(Long id, String name) {
        super(id, name);
    }
}
