package project.service.inter;

import project.dto.request.AcceptRequest;
import project.dto.request.RegisterRequest;
import project.dto.request.UserRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.UserInfoResponse;
import project.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    SimpleResponse register(RegisterRequest registerRequest);

    UserInfoResponse authenticate(UserRequest userRequest);

    List<UserResponse> getAllUsers();


    SimpleResponse updateUser(Long userId, RegisterRequest registerRequest);

    UserResponse getByIdUser(Long userId);

    String deleteUser(Long userId);

    List<UserResponse> getApplications();

    SimpleResponse acceptUserResponse(Long restaurantId, AcceptRequest acceptRequest);
}
