package com.comet.gamequeuemanager.repository;

import com.comet.gamequeuemanager.entity.QueueObject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueueRepository extends CrudRepository<QueueObject, UUID> {
}
