package ru.zhulin.oleg.restsystem.data;

import ru.zhulin.oleg.restsystem.model.Role;
import ru.zhulin.oleg.restsystem.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserData {
    public static final List<User> USERS_LIST = new ArrayList<>(Arrays.asList(
            new User(100101L, "User_01", "password_01", Role.ROLE_USER),
            new User(100102L, "User_02", "password_02", Role.ROLE_ADMIN),
            new User(100103L, "User_03", "password_03", Role.ROLE_ADMIN, Role.ROLE_USER)));
}
