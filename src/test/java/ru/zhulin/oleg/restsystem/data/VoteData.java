package ru.zhulin.oleg.restsystem.data;

import ru.zhulin.oleg.restsystem.model.Role;
import ru.zhulin.oleg.restsystem.model.User;
import ru.zhulin.oleg.restsystem.model.Vote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoteData {
    public static final List<Vote> VOTES_LIST = new ArrayList<>(Arrays.asList(
            new Vote(100201L, UserData.USERS_LIST.get(0), RestaurantData.RESTAURANTS_LIST.get(0), LocalDate.of(2021, 3, 19)),
            new Vote(100202L, UserData.USERS_LIST.get(1), RestaurantData.RESTAURANTS_LIST.get(1), LocalDate.of(2021, 3,20)),
            new Vote(100203L, UserData.USERS_LIST.get(2), RestaurantData.RESTAURANTS_LIST.get(2), LocalDate.of(2021, 3, 21))));
}
