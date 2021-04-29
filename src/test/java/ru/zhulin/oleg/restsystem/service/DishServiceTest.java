package ru.zhulin.oleg.restsystem.service;


import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import ru.zhulin.oleg.restsystem.data.DishData;
import ru.zhulin.oleg.restsystem.data.MenuData;
import ru.zhulin.oleg.restsystem.model.Dish;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.repository.DishRepository;
import ru.zhulin.oleg.restsystem.repository.MenuRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DishServiceTest {
    DishRepository dishRepository;
    MenuRepository menuRepository;

    DishService dishService;

    List<Dish> dishList = DishData.DISHES_LIST;
    List<Menu> menuList = MenuData.MENU_LIST;

    @BeforeEach
    void setUp() {
        dishRepository = mock(DishRepository.class);
        menuRepository = mock(MenuRepository.class);
        dishService = new DishService(dishRepository, menuRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));
        when(dishRepository.getByIdAndMenu(100001L, menuRepository.getById(100301L))).thenReturn(dishList.get(0));

        Dish dish = dishService.get(100001L, 100301L);
        Assertions.assertEquals(100001L, dish.getId());
        Assertions.assertEquals("Dish_01", dish.getName());
    }

    @Test
    void getAll() {
        when(dishRepository.findAll()).thenReturn(dishList);

        List<Dish> getList = dishService.getAll();
        Assertions.assertEquals(20, getList.size());
        verify(dishRepository, times(1)).findAll();
    }

    @Test
    void getAllByMenuId() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));
        Menu menu = menuRepository.getById(100301L);
        when(dishRepository.getAllByMenu(menu)).thenReturn(new ArrayList<>(DishData.DISHES_SET_01));

        List<Dish> getList = dishService.getAllByMenuId(100301L);
        Assertions.assertEquals(5, getList.size());
        verify(dishRepository, times(1)).getAllByMenu(menu);
    }

    @Test
    void create() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));
        when(dishRepository.save(dishList.get(0))).thenReturn(dishList.get(0));

        Dish dish = dishService.create(dishList.get(0), 100301L);

        verify(menuRepository, times(1)).getById(100301L);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void update() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));
        when(dishRepository.save(dishList.get(0))).thenReturn(dishList.get(0));

        Dish dish = dishService.update(dishList.get(0), 100301L);

        verify(menuRepository, times(1)).getById(100301L);
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void delete() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));
        when(dishRepository.getByIdAndMenu(100001L, menuList.get(0))).thenReturn(dishList.get(0));


        dishService.delete(100001L, 100301L);

        verify(dishRepository, times(1)).delete(any(Dish.class));
        verify(dishRepository, times(1)).getByIdAndMenu(anyLong(), any(Menu.class));
        verify(menuRepository, times(1)).getById(100301L);
    }

    @Test
    void deleteAllByMenuId() {
        when(menuRepository.getById(100301L)).thenReturn(menuList.get(0));

        dishService.deleteAllByMenuId(100301L);

        verify(dishRepository, times(1)).deleteAllByMenu(any(Menu.class));
        verify(menuRepository, times(1)).getById(100301L);
    }
}