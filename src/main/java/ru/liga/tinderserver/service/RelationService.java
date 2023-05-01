package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.exception.RelationNotFoundException;
import ru.liga.tinderserver.repository.RelationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    /**
     * Метод получения отношений пользователя
     * к другим
     *
     * @param userId
     * @return список отношений
     */
    public List<Relation> findAllByUserId(Long userId) {
        return relationRepository.findAllByUserId(userId);
    }

    /**
     * Метод возвращает отношения других пользователей
     * к данному пользователю
     *
     * @param userId
     * @return список отношений
     */
    public List<Relation> findAllBySelectedUserId(Long userId) {
        return relationRepository.findAllBySelectedUserId(userId);
    }

    /**
     * Метод возвращает отношения,
     * кто понравился  пользователю
     *
     * @param userId
     * @return список отношений
     */
    public List<Relation> findLikeByUserId(Long userId) {
        return relationRepository.findAllByUserIdAndSympathyTrue(userId);
    }

    /**
     * Метод возвращает отношения
     * с взаимной симпатией для данного пользователя
     *
     * @param userId
     * @return список отношений
     */
    //TODO: тесты
    public List<Relation> findMutualFeeling(Long userId) {
        List<Relation> result = new ArrayList<>();
        for (Relation relation : findLikeByUserId(userId)) {
            if (isMutualLike(relation.getSelectedUserId(), userId)) {
                result.add(relation);
            }
        }
        return result;
    }

    /**
     * Метод определяет является ли
     * симпатия взаимной
     *
     * @param userId         пользователь, у которого проверяем симпатию
     * @param selectedUserId пользователь, к которому проверяем симпатию
     * @return список отношений
     */

    private boolean isMutualLike(Long userId, Long selectedUserId) {
        List<Relation> relations = findAllByUserId(userId).stream()
                .filter(mutualLike -> mutualLike.getSelectedUserId().equals(selectedUserId)
                        && mutualLike.isSympathy())
                .toList();
        return !relations.isEmpty();
    }

    @Transactional
    public Relation create(Relation relation) {
       return relationRepository.save(relation);
    }

    /**
     * Метод возвращает оношение первого пользователя
     * ко второму
     *
     * @param userId         пользователь, у которого проверяем отношения
     * @param selectedUserId пользователь, к которому отношения
     * @return список отношений
     */
    public List<Relation> findRelationByUsers(Long userId, Long selectedUserId) {
        return findAllByUserId(userId).stream()
                .filter(relation -> Objects.equals(relation.getSelectedUserId(), selectedUserId))
                .collect(Collectors.toList());
    }

    @Transactional
    public Relation update(Long id, Relation relation) {
        relation.setId(id);
        return relationRepository.save(relation);
    }
}
