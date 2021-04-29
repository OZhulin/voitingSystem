package ru.zhulin.oleg.restsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zhulin.oleg.restsystem.model.Vote;
import ru.zhulin.oleg.restsystem.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rest/admin/votes")
public class VoteController {
    private VoteService voteService;
    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll(@RequestParam(value = "localDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        return voteService.getAllByDate(localDate);
    }
}
