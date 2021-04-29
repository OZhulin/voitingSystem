package ru.zhulin.oleg.restsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.zhulin.oleg.restsystem.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByLocalDate(LocalDate localDate);
    Vote getByUserIdAndLocalDate(Long userId, LocalDate localDate);
    Vote getById(Long voteId);
}
