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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zhulin.oleg.restsystem.RestSystemApp;
import ru.zhulin.oleg.restsystem.model.Vote;
import ru.zhulin.oleg.restsystem.security.AuthorizedUser;
import ru.zhulin.oleg.restsystem.service.VoteService;
import ru.zhulin.oleg.restsystem.validation.RestSystemValidationErrorBuilder;

import java.net.URI;
import java.time.LocalDate;

@Log4j2
@Tag(name = "Vote", description = "The vote API")
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController extends AbstractController<Vote> {
    private VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @Operation(summary = "Gets all votes by date", tags = {"vote"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the votes",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Vote.class)))
                    }
            ),
            @ApiResponse( responseCode = "404", description = "vote not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Vote>> getAll(@Parameter(description = "Date of votes", required = true)
                                                 @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        log.info("Get all votes by date {}", date);
        return ResponseEntity.ok(voteService.getAllByDate(date));
    }

    @Operation(summary = "Create vote", tags = {"vote"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vote.class))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "409", description = "already exists")
    })
    @PostMapping(value = "/restaurant/{restaurantId}")
    public ResponseEntity<Vote> create(@Parameter(description = "Id of restaurant", required = true)
                                    @PathVariable Long restaurantId,
                                    @Parameter(description = "User who authorized for voting", required = true,
                                               schema = @Schema(implementation = Vote.class))
                                    @AuthenticationPrincipal AuthorizedUser authorizedUser){
        Vote newVote = new Vote(null, null, null, LocalDate.now());
        log.info("Create vote for restaurant {} by authorized users {}", restaurantId, authorizedUser.getUsername());
        Vote vote = voteService.create(newVote, authorizedUser.getId(), restaurantId);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/votes/restaurant/{id}/vote/{voteId}")
                .buildAndExpand(restaurantId, vote.getId()).toUri();
       return ResponseEntity.created(location).body(vote);
    }
}
