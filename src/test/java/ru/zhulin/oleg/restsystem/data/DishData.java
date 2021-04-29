package ru.zhulin.oleg.restsystem.data;

import ru.zhulin.oleg.restsystem.model.Dish;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DishData {
    public static final List<Dish> DISHES_LIST = new ArrayList<>(Arrays.asList(
            new Dish(100001L, "Dish_01", BigDecimal.valueOf(51)),
            new Dish(100002L, "Dish_02", BigDecimal.valueOf(52)),
            new Dish(100003L, "Dish_03", BigDecimal.valueOf(53)),
            new Dish(100004L, "Dish_04", BigDecimal.valueOf(54)),
            new Dish(100005L, "Dish_05", BigDecimal.valueOf(55)),
            new Dish(100006L, "Dish_06", BigDecimal.valueOf(56)),
            new Dish(100007L, "Dish_07", BigDecimal.valueOf(57)),
            new Dish(100008L, "Dish_08", BigDecimal.valueOf(58)),
            new Dish(100009L, "Dish_09", BigDecimal.valueOf(59)),
            new Dish(100010L, "Dish_10", BigDecimal.valueOf(60)),
            new Dish(100011L, "Dish_11", BigDecimal.valueOf(61)),
            new Dish(100012L, "Dish_12", BigDecimal.valueOf(62)),
            new Dish(100013L, "Dish_13", BigDecimal.valueOf(63)),
            new Dish(100014L, "Dish_14", BigDecimal.valueOf(64)),
            new Dish(100015L, "Dish_15", BigDecimal.valueOf(65)),
            new Dish(100016L, "Dish_16", BigDecimal.valueOf(66)),
            new Dish(100017L, "Dish_17", BigDecimal.valueOf(67)),
            new Dish(100018L, "Dish_18", BigDecimal.valueOf(68)),
            new Dish(100019L, "Dish_19", BigDecimal.valueOf(69)),
            new Dish(100020L, "Dish_20", BigDecimal.valueOf(70))));

    public static final Set<Dish> DISHES_SET_20 = Set.copyOf(DISHES_LIST);

    public static final Set<Dish> DISHES_SET_01 = Set.of(
            DISHES_LIST.get(0),
            DISHES_LIST.get(1),
            DISHES_LIST.get(2),
            DISHES_LIST.get(3),
            DISHES_LIST.get(4));

    public static final Set<Dish> DISHES_SET_02 = Set.of(
            DISHES_LIST.get(5),
            DISHES_LIST.get(6),
            DISHES_LIST.get(7),
            DISHES_LIST.get(8));

    public static final Set<Dish> DISHES_SET_03 = Set.of(
            DISHES_LIST.get(9),
            DISHES_LIST.get(10));

    public static final Set<Dish> DISHES_SET_04 = Set.of(
            DISHES_LIST.get(11),
            DISHES_LIST.get(12),
            DISHES_LIST.get(13));

    public static final Set<Dish> DISHES_SET_05 = Set.of(
            DISHES_LIST.get(14),
            DISHES_LIST.get(15),
            DISHES_LIST.get(16),
            DISHES_LIST.get(17),
            DISHES_LIST.get(18),
            DISHES_LIST.get(19));

    public static final Set<Dish> DISHES_SET_06 = Set.of(
            DISHES_LIST.get(2),
            DISHES_LIST.get(4),
            DISHES_LIST.get(19),
            DISHES_LIST.get(5),
            DISHES_LIST.get(12),
            DISHES_LIST.get(15),
            DISHES_LIST.get(6));
}
