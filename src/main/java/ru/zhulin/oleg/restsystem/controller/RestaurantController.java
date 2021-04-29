package ru.zhulin.oleg.restsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.model.Restaurant;
import ru.zhulin.oleg.restsystem.service.MenuService;
import ru.zhulin.oleg.restsystem.service.RestaurantService;

import java.time.LocalDate;
import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/rest/admin/restaurants")
public class RestaurantMenuController {
    private RestaurantService restaurantService;
    private MenuService menuService;
    @Autowired
    public RestaurantMenuController(RestaurantService restaurantService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> get(@PathVariable Long id){
        return ResponseEntity.ok(restaurantService.get(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Restaurant>> getAll(){
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant){
        restaurant.setId(null);
        Restaurant newRestaurant = restaurantService.create(restaurant);
        URI uriOfNewRestaurant = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/rest/admin/restaurants/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewRestaurant).body(newRestaurant);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@RequestBody Restaurant restaurant, @PathVariable Long id){
        restaurant.setId(id);
        Restaurant updatedRestaurant = restaurantService.update(restaurant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedRestaurant.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> delete(@PathVariable Long id){
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/{restaurantId}/menus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> getMenu(@PathVariable Long restaurantId, @PathVariable Long id){
        return ResponseEntity.ok(menuService.get(id, restaurantId));
    }

    @GetMapping(value = "/{restaurantId}/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Menu>> getAllMenus(@PathVariable Long restaurantId, @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(menuService.getAllByDateAndRestaurantId(date, restaurantId));
    }

    @PostMapping(value = "/{restaurantId}/menus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu, @PathVariable Long restaurantId){
        menu.setId(null);
        menu.setRestaurant(null);
        Menu newMenu = menuService.create(menu, restaurantId);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/rest/admin/restaurants/{restaurantId}/menus/{menuId}")
                .buildAndExpand(restaurantId, newMenu.getId())
                .toUri();
        return ResponseEntity.created(location).body(newMenu);
    }

    @PutMapping(value = "/{restaurantId}/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> updateMenu(@RequestBody Menu menu, @PathVariable Long restaurantId, @PathVariable Long id){
        menu.setId(id);
        Menu updatedMenu = menuService.update(menu, restaurantId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedMenu.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @DeleteMapping(value = "/{restaurantId}/menus/{id}")
    public ResponseEntity<Menu> deleteMenu(@PathVariable Long restaurantId, @PathVariable Long id){
        menuService.delete(id, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
