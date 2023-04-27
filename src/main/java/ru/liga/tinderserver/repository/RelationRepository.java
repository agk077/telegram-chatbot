package ru.liga.tinderserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.tinderserver.entity.Relation;

import java.util.List;

@Repository
public interface RelationRepository extends JpaRepository<Relation, Long> {

    public List<Relation> findAllBySelectedUserId(Long selectedUserId);

    public List<Relation> findAllByUserId(Long userId);

    public List<Relation> findAllByUserIdAndSympathyTrue(Long userId);


}


