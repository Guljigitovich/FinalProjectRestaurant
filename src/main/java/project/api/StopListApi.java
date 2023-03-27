package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.StopListRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.StopListResponse;
import project.service.inter.StopListService;

import java.util.List;

@RequestMapping("/api/stopLists")
@RestController
public class StopListApi {
    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PostMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveStopList (@PathVariable Long menuItemId,
            @RequestBody StopListRequest stopListRequest){
        return stopListService.saveStopList(menuItemId,stopListRequest);
    }
    @GetMapping ("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public StopListResponse getStopListById(@PathVariable Long stopListId){
        return stopListService.getStopListById(stopListId);
    }
    @GetMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<StopListResponse>getAllStopList(@PathVariable Long menuItemId){
        return stopListService.getAllStopList(menuItemId);
    }
    @PutMapping("/{menuItemId}/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateStopList(@PathVariable Long menuItemId,
                                         @PathVariable Long stopListId,
                                         @RequestBody StopListRequest stopListRequest){
        return stopListService.updateStopList(menuItemId,stopListId, stopListRequest);
    }
    @DeleteMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public String deleteStopList(@PathVariable Long stopListId){
        return stopListService.deleteStopList(stopListId);
    }
}
