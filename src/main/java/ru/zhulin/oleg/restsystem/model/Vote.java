package ru.zhulin.oleg.restsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(name = "votes_user_date_idx", columnNames = {"user_id", "date"})})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Vote extends ParentEntity{
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "date", columnDefinition = "timestamp default now()")
    private LocalDate localDate;

    public Vote(Long id, User user, Restaurant restaurant, LocalDate localDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.localDate = localDate;
    }
}
