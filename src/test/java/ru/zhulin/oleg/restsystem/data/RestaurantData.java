package ru.zhulin.oleg.restsystem.data;

import ru.zhulin.oleg.restsystem.model.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantData {
    public static final List<Restaurant> RESTAURANTS_LIST = new ArrayList<>(Arrays.asList(
        new Restaurant(100401L, "FirstRestaurant"),
        new Restaurant(100402L, "SecondRestaurant"),
        new Restaurant(100403L, "ThreeRestaurant"),
        new Restaurant(100404L, "FourRestaurant"),
        new Restaurant(100405L, "FiveRestaurant")));
}
