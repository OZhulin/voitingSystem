package ru.zhulin.oleg.restsystem.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.zhulin.oleg.restsystem.data.DishData;
import ru.zhulin.oleg.restsystem.data.MenuData;
import ru.zhulin.oleg.restsystem.data.RestaurantData;
import ru.zhulin.oleg.restsystem.model.Dish;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.model.Restaurant;
import ru.zhulin.oleg.restsystem.repository.DishRepository;
import ru.zhulin.oleg.restsystem.repository.MenuRepository;
import ru.zhulin.oleg.restsystem.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
class MenuServiceTest {
    DishRepository dishRepository;
    MenuRepository menuRepository;
    RestaurantRepository restaurantRepository;

    MenuService menuService;

    List<Dish> dishList = DishData.DISHES_LIST;
    List<Menu> menuList = MenuData.MENU_LIST;
    List<Restaurant> restaurantList = RestaurantData.RESTAURANTS_LIST;

    @BeforeEach
    void setUp() {
        dishRepository = mock(DishRepository.class);
        menuRepository = mock(MenuRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        menuService = new MenuService(menuRepository, restaurantRepository, dishRepository);
    }

    @Test
    void get() {
        when(menuRepository.getByIdAndAndRestaurant_Id(100301L, 1004001L)).thenReturn(menuList.get(0));

        Menu menu = menuService.get(100301L, 1004001L);
        Assertions.assertEquals(100301L, menu.getId());
    }

    @Test
    void getAll() {
        when(menuRepository.findAll()).thenReturn(menuList);

        List<Menu> menus = menuService.getAll();
        Assertions.assertEquals(5, menus.size());
    }

    @Test
    void getAllByDate() {
        when(menuRepository.getAllByDate(LocalDate.of(2021, 1, 10)))
                .thenReturn(new ArrayList<>(Arrays.asList(menuList.get(0), menuList.get(2))));

        List<Menu> menus = menuService.getAllByDate(LocalDate.of(2021, 1, 10));
        Assertions.assertEquals(2, menus.size());
    }

    @Test
    void getAllByDateAndRestaurantId() {
        LocalDate localDate = LocalDate.of(2021, 1, 10);
        when(restaurantRepository.getById(100402L)).thenReturn(restaurantList.get(1));
        when(menuRepository.getAllByDateAndRestaurant(localDate, restaurantList.get(1)))
                .thenReturn(new ArrayList<>(Arrays.asList(menuList.get(2))));

        List<Menu> menus = menuService.getAllByDateAndRestaurantId(localDate, 100402L);
        Assertions.assertEquals(1, menus.size());

    }

    @Test
    void create() {
        when(restaurantRepository.getById(100402L)).thenReturn(restaurantList.get(1));
        when(menuRepository.save(menuList.get(2))).thenReturn(menuList.get(2));
        when(dishRepository.save(dishList.get(9))).thenReturn(dishList.get(9));
        when(dishRepository.save(dishList.get(10))).thenReturn(dishList.get(10));

        Menu menu = menuService.create(menuList.get(2), 100402L);
        Assertions.assertEquals(100402L, menu.getRestaurant().getId());
        Assertions.assertEquals(2, menu.getDishSet().size());
    }

    @Test
    @Disabled
    void update() {
        when(restaurantRepository.getById(100402L)).thenReturn(restaurantList.get(1));
        when(menuRepository.save(menuList.get(2))).thenReturn(menuList.get(2));
        when(dishRepository.save(dishList.get(9))).thenReturn(dishList.get(9));
        when(dishRepository.save(dishList.get(10))).thenReturn(dishList.get(10));

        Menu menu = menuService.update(menuList.get(2), 100402L);
        Assertions.assertEquals(100402L, menu.getRestaurant().getId());
        Assertions.assertEquals(2, menu.getDishSet().size());
    }

    @Test
    void delete() {
        menuService.delete(100303L, 100402L);

        verify(menuRepository, times(1)).deleteByIdAndRestaurant_Id(100303L, 100402L);
    }

    @Test
    void deleteAll() {
        menuService.deleteAll();

        verify(menuRepository, times(1)).deleteAll();
    }
}