package ru.zhulin.oleg.restsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhulin.oleg.restsystem.model.Dish;
import ru.zhulin.oleg.restsystem.model.Menu;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Long> {
    void deleteAllByMenu(Menu menu);
    List<Dish> getAllByMenu(Menu menu);
    List<Dish> getAllByIdAndMenu(Long id, Menu menu);
    Dish getByIdAndMenu(Long id, Menu menu);
    void deleteByIdAndMenu_Id(Long id, Long menuId);
    Dish getById(Long menuId);

}
