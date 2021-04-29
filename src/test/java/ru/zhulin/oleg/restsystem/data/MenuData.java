package ru.zhulin.oleg.restsystem.data;

import ru.zhulin.oleg.restsystem.model.Menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuData {
    public static final List<Menu> MENU_LIST = new ArrayList<>(Arrays.asList(
            new Menu(100301L, RestaurantData.RESTAURANTS_LIST.get(0), LocalDate.of(2021,1,10),DishData.DISHES_SET_01 ),
            new Menu(100302L, RestaurantData.RESTAURANTS_LIST.get(0), LocalDate.of(2021, 2,10),DishData.DISHES_SET_02 ),
            new Menu(100303L, RestaurantData.RESTAURANTS_LIST.get(1), LocalDate.of(2021,1,10),DishData.DISHES_SET_03 ),
            new Menu(100304L, RestaurantData.RESTAURANTS_LIST.get(2), LocalDate.of(2021,2,12),DishData.DISHES_SET_01 ),
            new Menu(100305L, RestaurantData.RESTAURANTS_LIST.get(3), LocalDate.of(2021,3,13),DishData.DISHES_SET_04 )));
}
