package ru.zhulin.oleg.restsystem.service;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import ru.zhulin.oleg.restsystem.data.RestaurantData;
import ru.zhulin.oleg.restsystem.model.Restaurant;
import ru.zhulin.oleg.restsystem.repository.RestaurantRepository;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class RestaurantServiceTest {
    RestaurantService restaurantService;
    RestaurantRepository restaurantRepository;
    List<Restaurant> list = RestaurantData.RESTAURANTS_LIST;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void get() {
        when(restaurantRepository.getById(100401L)).thenReturn(list.get(0));

        Restaurant restaurant = restaurantService.get(100401L);
        Assertions.assertEquals(100401L, restaurant.getId());
        Assertions.assertEquals("FirstRestaurant", restaurant.getName());
    }

    @Test
    void getAll() {
        when(restaurantRepository.findAll()).thenReturn(list);

        List<Restaurant> restaurantList = restaurantService.getAll();
        Assertions.assertEquals(5, restaurantList.size());
        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void create() {
        when(restaurantRepository.save(list.get(0))).thenReturn(list.get(0));

        restaurantService.create(list.get(0));

        verify(restaurantRepository, times(1)).save(list.get(0));
    }

    @Test
    void update() {
        when(restaurantRepository.save(list.get(0))).thenReturn(list.get(0));

        restaurantService.create(list.get(0));

        verify(restaurantRepository, times(1)).save(list.get(0));
    }

    @Test
    void delete() {
        restaurantService.delete(100401L);

        verify(restaurantRepository, times(1)).deleteById(100401L);

    }

    @Test
    void deleteAll() {
        restaurantService.deleteAll();

        verify(restaurantRepository, times(1)).deleteAll();
    }
}