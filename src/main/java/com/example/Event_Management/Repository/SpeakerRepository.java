package com.example.Event_Management.Repository;

import com.example.Event_Management.Entity.SpeakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpeakerRepository extends JpaRepository<SpeakerEntity,Long> {
    Optional<List<SpeakerEntity>> findByEventId(long id);
}
