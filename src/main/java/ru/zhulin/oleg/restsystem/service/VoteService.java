package ru.zhulin.oleg.restsystem.service;

import com.mysema.commons.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.zhulin.oleg.restsystem.model.Vote;
import ru.zhulin.oleg.restsystem.repository.RestaurantRepository;
import ru.zhulin.oleg.restsystem.repository.UserRepository;
import ru.zhulin.oleg.restsystem.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service("voteService")
public class VoteService {
    private VoteRepository voteRepository;
    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote get(Long voteId){
        return voteRepository.getById(voteId);
    }

    public Vote getByUserIdAndLocalDate(Long userId, LocalDate localDate){
        return voteRepository.getByUserIdAndLocalDate(userId, localDate);
    }

    public List<Vote> getAll(){
        return voteRepository.findAll();
    }

    public List<Vote> getAllByDate(LocalDate localDate){
        return voteRepository.findAllByLocalDate(localDate);
    }

    public Vote create(Vote vote, Long userId, Long restaurantId){
        Assert.notNull(vote,
                "The [vote] argument cannot be null");
        Vote oldVote = voteRepository.getByUserIdAndLocalDate(userId, vote.getLocalDate());
        if(oldVote == null){
            vote.setUser(userRepository.getById(userId));
            vote.setRestaurant(restaurantRepository.getById(restaurantId));
            return voteRepository.save(vote);
        }
        if(LocalTime.now().isBefore(LocalTime.of(11,0))){
            oldVote.setUser(userRepository.getById(userId));
            oldVote.setRestaurant(restaurantRepository.getById(restaurantId));
            return voteRepository.save(oldVote);
        }else{
            return  null;
        }
    }

    public void delete(Long voteId){
        voteRepository.deleteById(voteId);
    }

    public void deleteAll(){
        voteRepository.deleteAll();
    }
}
