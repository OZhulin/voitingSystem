package ru.zhulin.oleg.restsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> getAllByDate(LocalDate localDate);
    List<Menu> getAllByDateAndRestaurant(LocalDate localDate, Restaurant restaurant);
    Menu getById(Long menuId);
    Menu getByIdAndAndRestaurant_Id(Long menuId, Long restaurantId);
    void deleteByIdAndRestaurant_Id(Long menuId, Long restaurantId);
}
