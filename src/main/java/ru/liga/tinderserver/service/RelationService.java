package ru.liga.tinderserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.liga.tinderserver.entity.Relation;
import ru.liga.tinderserver.exception.RelationNotFoundException;
import ru.liga.tinderserver.repository.RelationRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelationService {

    private final RelationRepository relationRepository;

    public List<Relation> findAll() {
        log.info("Поиск всех отношений");
        return relationRepository.findAll();
    }

    public Relation findById(Long id) {
        log.info("Поиск отношения с id = " + id);
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
        log.info("Поиск всех отношений для пользователя userId = " + userId);
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
        log.info("Поиск всех отношений к пользователю userId = " + userId);
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
        log.info("Поиск всех понравившихся  пользователю userId = " + userId);
        return relationRepository.findAllByUserIdAndSympathyTrue(userId);
    }

    /**
     * Метод возвращает отношения
     * с взаимной симпатией для данного пользователя
     *
     * @param userId
     * @return список отношений
     */
    public List<Relation> findMutualFeeling(Long userId) {
        List<Relation> result = new ArrayList<>();
        for (Relation relation : findLikeByUserId(userId)) {
            if (isMutualLike(relation.getSelectedUserId(), userId)) {
                result.add(relation);
            }
        }
        log.info("Поиск всех взаимных симпатий для пользователя userId = " + userId);
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

    public boolean isMutualLike(Long userId, Long selectedUserId) {
        List<Relation> relations = findAllByUserId(userId).stream()
                .filter(mutualLike -> mutualLike.getSelectedUserId().equals(selectedUserId)
                        && mutualLike.isSympathy())
                .toList();
        return !relations.isEmpty();
    }

    @Transactional
    public Relation create(Relation relation) {
        log.info("Создаем новые отношения:  " + relation);
        return relationRepository.save(relation);
    }

    /**
     * Метод возвращает есть ли оношение первого пользователя
     * ко второму
     *
     * @param userId         пользователь, у которого проверяем отношения
     * @param selectedUserId пользователь, к которому проверяем отношения
     * @return boolean
     */
    public boolean isRelationByUsers(Long userId, Long selectedUserId) {
        log.info("Поиск отношения пользователя с id = " + userId + " к пользователю с id = " + selectedUserId);
        return findAllByUserId(userId).stream()
                .anyMatch(relation -> relation.getSelectedUserId().equals(selectedUserId));
    }

    @Transactional
    public Relation update(Long id, Relation relationForChange) {
        Relation relation = findById(id);
        relation.setUserId(relationForChange.getUserId())
                .setSelectedUserId(relationForChange.getSelectedUserId())
                .setSympathy(relationForChange.isSympathy());
        log.info("Изменение отношений с id = " + id + " " + relation);
        return relationRepository.save(relation);
    }
}
