package com.comet.gamequeuemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueueFile {
    private UUID id;
    private Integer capacity;
    private List<PlayerDto> playersAssigned = new ArrayList<>();

    public QueueFile(UUID id, Integer capacity) {
        this.id = id;
        this.capacity = capacity;
    }
}
