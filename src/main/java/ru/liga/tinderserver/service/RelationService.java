package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.Repository.RelationRepository;
import ru.liga.tinderserver.dto.RelationDto;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.exception.RelationNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationRepository relationRepository;

    public List<Relation> findAll() {
        return relationRepository.findAll();
    }

    public Relation findById(Long id) {
        return relationRepository.findById(id).orElseThrow(() -> new RelationNotFoundException(id));
    }

    public List<Relation> findAllByUserId(Long userId) {
        return relationRepository.findAllByUserId(userId);
    }

    public List<Relation> findAllBySelectedUserId(Long userId) {
        return relationRepository.findAllBySelectedUserId(userId);
    }

    public List<Relation> findLikeByUserId(Long userId) {
        return relationRepository.findAllByUserIdAndSympathyTrue(userId);
    }

    public List<Relation> findMutualFeeling(Long userId) {
        List<Relation> result = new ArrayList<>();
        for (Relation like : findLikeByUserId(userId)) {
            if (mutual(like.getSelectedUserId(), userId)) {
                result.add(like);
            }
        }
        return result;
    }

    private boolean mutual(Long userId1, Long userId2) {
        List<Relation> likes = findAllByUserId(userId1).stream()
                .filter(mutualLike -> mutualLike.getSelectedUserId().equals(userId2)
                        && mutualLike.isSympathy())
                .toList();
        return !likes.isEmpty();
    }

    @Transactional
    public void create(RelationDto relationDto) {
        Relation relation = new Relation();
        relation.setUserId(relationDto.getUserId());
        relation.setSelectedUserId(relationDto.getSelectedUserId());
        relation.setSympathy(relationDto.isSympathy());
        relationRepository.save(relation);
    }

    public List<Relation> findRelationByUsers(Long userId, Long selectedUserId) {
        return findAllByUserId(userId).stream().filter(relation -> relation.getSelectedUserId() == selectedUserId).collect(Collectors.toList());
    }

    @Transactional
    public void update(Long id, RelationDto relationDto) {
        Relation relation = findById(id);
        relation.setUserId(relationDto.getUserId());
        relation.setSelectedUserId(relationDto.getSelectedUserId());
        relation.setSympathy(relationDto.isSympathy());
        relationRepository.save(relation);
    }
}
