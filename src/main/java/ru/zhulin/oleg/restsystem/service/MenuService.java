package ru.zhulin.oleg.restsystem.service;

import com.mysema.commons.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ru.zhulin.oleg.restsystem.model.Dish;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.repository.DishRepository;
import ru.zhulin.oleg.restsystem.repository.MenuRepository;
import ru.zhulin.oleg.restsystem.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service("menuService")
public class MenuService {
    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;
    @Autowired
    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository, DishRepository dishRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    public Menu get(Long menuId, Long restaurantId){
        return menuRepository.getByIdAndAndRestaurant_Id(menuId, restaurantId);
    }

    public List<Menu> getAll(){
        return menuRepository.findAll();
    }

    @CacheEvict(value = "menus")
    public List<Menu> getAllByDate(LocalDate localDate){
        return menuRepository.getAllByDate(localDate);
    }

    public List<Menu> getAllByDateAndRestaurantId(LocalDate localDate, Long restaurantId){
        return menuRepository.getAllByDateAndRestaurant(localDate, restaurantRepository.getById(restaurantId));
    }

    @CacheEvict(value = "menus", allEntries = true)
    public Menu create(Menu menu, Long restaurantId){
        Assert.notNull(menu,
                "The [menu] argument cannot be null");
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        Menu newMenu = menuRepository.save(menu);
        Set<Dish> setOfDishes = newMenu.getDishSet();
        if(setOfDishes != null){
            setOfDishes.forEach(dish -> {
                dish.setId(null);
                dish.setMenu(newMenu);
                dishRepository.save(dish);
            });
        }
        return newMenu;
    }

    @CacheEvict(value = "menus", allEntries = true)
    public Menu update(Menu menu, Long restaurantId){
        Assert.notNull(menu,
                "The [menu] argument cannot be null");
        Set<Dish> oldSetOfDishes = get(menu.getId(), restaurantId).getDishSet();
        if(oldSetOfDishes != null){
            oldSetOfDishes.forEach(dish -> {
                dishRepository.deleteByIdAndMenu_Id(dish.getId(), menu.getId());
            });
        }
        Set<Dish> setOfDishes = menu.getDishSet();
        if(setOfDishes != null){
            setOfDishes.forEach(dish -> {
                dish.setId(null);
                dish.setMenu(menu);
                dishRepository.save(dish);
            });
        }
        menu.setRestaurant(restaurantRepository.getById(restaurantId));
        return menuRepository.save(menu);
    }

    public void delete(Long menuId, Long restaurantId){
        menuRepository.deleteByIdAndRestaurant_Id(menuId, restaurantId);
    }

    public void deleteAll(){
        menuRepository.deleteAll();
    }
    @CacheEvict(value = "menus", allEntries = true)
    public void evictCache(){}


}
