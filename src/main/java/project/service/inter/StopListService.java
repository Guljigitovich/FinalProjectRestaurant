package project.service.inter;

import project.dto.request.StopListRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse saveStopList(Long menuItemId,StopListRequest stopListRequest);

    StopListResponse getStopListById(Long stopListId);

    List<StopListResponse> getAllStopList(Long menuItemId);

    SimpleResponse updateStopList(Long menuItemId,Long stopListId,StopListRequest stopListRequest);

    String deleteStopList(Long stopListId);
}
