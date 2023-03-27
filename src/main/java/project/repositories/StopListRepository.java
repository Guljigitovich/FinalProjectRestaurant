package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.StopListResponse;
import project.entity.StopList;

import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    Optional<StopListResponse> getStopListsById(Long stopListId);

    List<StopListResponse>findAllByMenuItem(Long menuItemId);
}