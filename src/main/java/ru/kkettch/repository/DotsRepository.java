package ru.kkettch.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.kkettch.entity.DotsEntity;

import java.util.List;

@Repository
public interface DotsRepository extends CrudRepository<DotsEntity, Long> {
   List<DotsEntity> getDotsEntitiesByUserId(Long userId);

   int deleteAllByUserId(Long userID);
}
