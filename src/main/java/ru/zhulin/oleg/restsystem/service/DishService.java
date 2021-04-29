package ru.zhulin.oleg.restsystem.service;

import com.mysema.commons.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhulin.oleg.restsystem.model.Dish;
import ru.zhulin.oleg.restsystem.repository.DishRepository;
import ru.zhulin.oleg.restsystem.repository.MenuRepository;

import java.util.List;

@Service("dishService")
public class DishService {
    private DishRepository dishRepository;
    private MenuRepository menuRepository;

    @Autowired
    public DishService(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    public Dish get(Long dishId, Long menuId){
        return dishRepository.getByIdAndMenu(dishId, menuRepository.getById(menuId));
    }

    public List<Dish> getAll(){
        return dishRepository.findAll();
    }

    public List<Dish> getAllByMenuId(Long menuId){
        return dishRepository.getAllByMenu(menuRepository.getById(menuId));
    }

    public Dish create(Dish dish, Long menuId){
        dish.setMenu(menuRepository.getById(menuId));
        return dishRepository.save(dish);
    }

    public Dish update(Dish dish, Long menuId){
        Assert.notNull(dish,
                "The [dish] argument cannot be null");
        return create(dish, menuId);
    }

    public void delete(Long dishId, Long menuId){
        dishRepository.delete(dishRepository.getByIdAndMenu(dishId, menuRepository.getById(menuId)));
    }

    public void deleteAllByMenuId(Long menuId){
        dishRepository.deleteAllByMenu(menuRepository.getById(menuId));
    }


}
