package com.animeSite.repo;

import com.animeSite.persist.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, UUID> {
    Optional<Anime> findByMalId(Integer malId);
    boolean existsByMalId(Integer malId);
}
