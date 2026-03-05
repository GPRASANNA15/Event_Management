package com.example.Event_Management.Repository;

import com.example.Event_Management.Dto.RegisteredEventDto;
import com.example.Event_Management.Entity.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity,Long> {
Optional<List<RegisterEntity>> findByEventId(long id);
Optional<List<RegisterEntity>> findByUserId(long id);
}
