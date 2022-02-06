package com.comet.gamequeuemanager.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "queue")
@Data
public class QueueObject {
    @Id
    private UUID id;
    @Column
    private String cid;

    public QueueObject(UUID id, String cid) {
        this.id = id;
        this.cid = cid;
    }

    public QueueObject() {
    }
}
