package ru.zhulin.oleg.restsystem.service;

import com.mysema.commons.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ru.zhulin.oleg.restsystem.model.Restaurant;
import ru.zhulin.oleg.restsystem.repository.RestaurantRepository;

import java.util.List;

@Service("restaurantService")
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    public Restaurant get(Long id){
        return restaurantRepository.getById(id);
    }

    @CacheEvict(value = "restaurants")
    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant){
        Assert.notNull(restaurant,
                "The [restaurant] argument cannot be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant update(Restaurant restaurant){
        Assert.notNull(restaurant,
                "The [restaurant] argument cannot be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void delete(Long id){
        restaurantRepository.deleteById(id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void deleteAll(){
        restaurantRepository.deleteAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void evictCache(){};
}
