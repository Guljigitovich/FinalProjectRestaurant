package project.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.AcceptRequest;
import project.dto.request.RegisterRequest;
import project.dto.request.UserRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.UserInfoResponse;
import project.dto.response.UserResponse;
import project.entity.User;
import project.service.inter.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
   private  final UserService userService;
    @PostMapping("/register")
    public SimpleResponse register (@RequestBody RegisterRequest registerRequest){
        return userService.register(registerRequest);
    }
    @PostMapping("/authenticate")
    public UserInfoResponse authenticate(@RequestBody UserRequest userRequest){
        return userService.authenticate(userRequest);
    }
    @ApiOperation(value = "Get a list of all users", response = List.class)
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<UserResponse>getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> getAllApplications(){
     return userService.getApplications();
    }

    @PostMapping("/accept/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse acceptResponse (@PathVariable Long restaurantId,
                                   @RequestBody AcceptRequest acceptRequest){
    return  userService.acceptUserResponse(restaurantId,acceptRequest);
    }

    @PutMapping("/{userId}")
    public SimpleResponse updateUser (@PathVariable Long userId,
                                    @RequestBody RegisterRequest registerRequest){
        return userService.updateUser(userId,registerRequest);
    }

    @ApiOperation(value = "Get a user by ID", response = User.class)
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse getByIdUser(@PathVariable Long userId){
        return userService.getByIdUser(userId);
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long userId){
        return userService.deleteUser(userId);
    }

}
