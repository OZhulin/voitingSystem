package ru.zhulin.oleg.restsystem.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ParentEntity {
    public static final int START_SEQ = 100000;
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Access(AccessType.PROPERTY)
    private Long id;
    public boolean isNew(){
        return (getId() == null);
    }

}
