package ru.zhulin.oleg.restsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.model.Restaurant;
import ru.zhulin.oleg.restsystem.service.RestaurantService;

import javax.validation.Valid;
import java.util.List;
import java.net.URI;

@Log4j2
@Tag(name = "Restaurant", description = "The restaurant API")
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController extends AbstractController<Restaurant> {
    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Get restaurant by id", tags = {"restaurant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the restaurant",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class))
                    }
            ),
            @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> get(@Parameter(description = "id of restaurant", required = true)
                                          @PathVariable Long id){
        log.info("Getting restaurant by id {}", id);
        return ResponseEntity.ok(restaurantService.get(id));
    }

    @Operation(summary = "Gets all restaurants", tags = {"restaurant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the restaurants",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Restaurant.class)))
                    }
            ),
            @ApiResponse( responseCode = "404", description = "restaurants not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAll(){
        log.info("Getting all restaurants");
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @Operation(summary = "Create restaurant", tags = {"restaurant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "409", description = "already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Parameter(description = "body of restaurant", required = true)
                                             @Valid @RequestBody Restaurant restaurant){
        restaurant.setId(null);
        log.info("Creating restaurant");
        Restaurant newRestaurant = restaurantService.create(restaurant);
        URI uriOfNewRestaurant = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/restaurants/{id}")
                .buildAndExpand(newRestaurant.getId()).toUri();
        return ResponseEntity.created(uriOfNewRestaurant).body(newRestaurant);
    }

    @Operation(summary = "Update restaurant by id", tags = {"restaurant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Restaurant.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "404", description = "restaurant not found"),
            @ApiResponse(responseCode = "405", description = "validation exception")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> update(@Parameter(description = "restaurant body", required = true)
                                             @Valid @RequestBody Restaurant restaurant,
                                             @Parameter(description = "id of restaurant", required = true)
                                             @PathVariable Long id){
        restaurant.setId(id);
        log.info("Updating restaurant {} by body {}", id, restaurant);
        Restaurant updatedRestaurant = restaurantService.update(restaurant);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedRestaurant.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @Operation(summary = "delete restaurant by id", tags = {"restaurant"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "deleted successful"),
            @ApiResponse(responseCode = "404", description = "restaurant not found")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Restaurant> delete(@Parameter(description = "id of restaurant", required = true)
                                             @PathVariable Long id){
        log.info("Deleting restaurant by id {}", id);
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
