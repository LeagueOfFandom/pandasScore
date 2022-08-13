package com.server.pandascore.repository;

import com.server.pandascore.entity.ChampionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<ChampionEntity, Long> {
}
