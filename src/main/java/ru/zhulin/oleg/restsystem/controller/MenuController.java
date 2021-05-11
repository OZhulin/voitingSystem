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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.model.Menu;
import ru.zhulin.oleg.restsystem.service.MenuService;
import ru.zhulin.oleg.restsystem.validation.RestSystemValidationErrorBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Log4j2
@Tag(name = "Menu", description = "The menu API")
@RestController
@RequestMapping("/api/v1/menus")
public class MenuController extends AbstractController<Menu> {
    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @Operation(summary = "Gets all menus by date", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menus",
                        content = {@Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Menu.class)))
                        }
            ),
            @ApiResponse( responseCode = "404", description = "menu not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Menu>> getAll(@Parameter(description = "Date of menu creation", required = true)
                                             @RequestParam(value = "date")
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("Getting all menu by date {}",date);
        return ResponseEntity.ok(menuService.getAllByDate(date));
    }

    @Operation(summary = "Get menu by menu_id and restaurant_id", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menu",
                        content = {@Content(mediaType = "application/json",
                                            schema = @Schema(implementation = Menu.class))
                        }
            ),
            @ApiResponse(responseCode = "404", description = "menu not found")
    })
    @GetMapping(value = "/restaurant/{restaurantId}/menu/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> get(@Parameter(description = "Id of restaurant", required = true)
                                    @PathVariable Long restaurantId,
                                    @Parameter(description = "Id of menu", required = true)
                                    @PathVariable Long id){
        log.info("Getting menu by id {} and restaurant id {}", id, restaurantId);
        return ResponseEntity.ok(menuService.get(id, restaurantId));
    }

    @Operation(summary = "Get all menus by restaurant_id and date", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the menu",
                        content = {@Content(mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Menu.class)))
                        }
            ),
            @ApiResponse(responseCode = "404", description = "menu not found")
    })
    @GetMapping(value = "/restaurant/{restaurantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Menu>> getAllMenusByRestaurant(@Parameter(description = "Id of restaurants", required = true)
                                                                  @PathVariable Long restaurantId,
                                                                  @Parameter(description = "Date of menu creation")
                                                                  @RequestParam(value = "date")
                                                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("Getting all menus by restaurant id {} and date {}", restaurantId, date);
        return ResponseEntity.ok(menuService.getAllByDateAndRestaurantId(date, restaurantId));
    }

    @Operation(summary = "Create menu for restaurant", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created successful",
                    content = {@Content(mediaType = "application/json",
                                        schema = @Schema(implementation = Menu.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "409", description = "already exists")
    })
    @PostMapping(value = "/restaurant/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Parameter(description = "Menu to create", required = true,
                                                  schema = @Schema(implementation = Menu.class))
                                       @RequestBody Menu menu,
                                       @Parameter(description = "Restaurant for menu", required = true)
                                       @PathVariable Long restaurantId,
                                       Errors errors){
        if(errors.hasErrors()){
            log.warn("Bad request. Request has errors: {}", errors.getAllErrors());
            return ResponseEntity.badRequest().body(RestSystemValidationErrorBuilder.fromBindingErrors(errors));
        }
        menu.setId(null);
        menu.setRestaurant(null);
        log.info("Creating menu {}", menu);
        Menu newMenu = menuService.create(menu, restaurantId);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/menus/restaurant/{restaurantId}/menu/{menuId}")
                .buildAndExpand(restaurantId, newMenu.getId())
                .toUri();
        return ResponseEntity.created(location).body(newMenu);
    }

    @Operation(summary = "Update menu_id for restaurant", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Menu.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "404", description = "menu not found"),
            @ApiResponse(responseCode = "405", description = "validation exception")
    })
    @PutMapping(value = "/restaurant/{restaurantId}/menu/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Parameter(description = "menu to update", required = true,
                                                  schema = @Schema(implementation = Menu.class))
                                       @Valid @RequestBody Menu menu,
                                       @Parameter(description = "id of restaurant", required = true)
                                       @PathVariable Long restaurantId,
                                       @Parameter(description = "id of menu", required = true)
                                       @PathVariable Long id,
                                       Errors errors){
        if(errors.hasErrors()){
            log.warn("Bad request. Request has errors: {}", errors.getAllErrors());
            return ResponseEntity.badRequest().body(RestSystemValidationErrorBuilder.fromBindingErrors(errors));
        }
        menu.setId(id);
        log.info("Updating menu with id {} by body menu {}", id, menu);
        Menu updatedMenu = menuService.update(menu, restaurantId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(updatedMenu.getId())
                .toUri();
        return ResponseEntity.ok().header("Location", location.toString()).build();
    }

    @Operation(summary = "delete menu by id and restaurant_id", tags = {"menu"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful"),
            @ApiResponse(responseCode = "404", description = "menu not found")
    })
    @DeleteMapping(value = "/restaurant/{restaurantId}/menu/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "id of restaurant", required = true)
                                       @PathVariable Long restaurantId,
                                       @Parameter(description = "id of menu", required = true)
                                       @PathVariable Long id){
        log.info("Deleting menu by id {} and restaurant id {}", id, restaurantId);
        menuService.delete(id, restaurantId);
        return ResponseEntity.noContent().build();
    }
}
