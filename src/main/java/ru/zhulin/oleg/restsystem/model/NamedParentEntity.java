package ru.zhulin.oleg.restsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@Getter
@Setter
@ToString
@NoArgsConstructor
public class NamedParentEntity extends ParentEntity{
    @NotBlank
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public NamedParentEntity(Long id, String name) {
        super(id);
        this.name = name;
    }
}
