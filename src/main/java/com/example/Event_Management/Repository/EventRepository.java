package com.example.Event_Management.Repository;

import com.example.Event_Management.Entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity,Long> {
    Optional<EventEntity> findByTitle(String name);
    Optional<List<EventEntity>> findByVenue(String venue);
    Optional<List<EventEntity>> findByCategory(String category);

}
