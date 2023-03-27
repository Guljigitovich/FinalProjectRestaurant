package project.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.config.jwt.JwtUtil;
import project.dto.request.AcceptRequest;
import project.dto.request.RegisterRequest;
import project.dto.request.UserRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.UserInfoResponse;
import project.dto.response.UserResponse;
import project.entity.User;
import project.enums.Role;
import project.exception.AlreadyExistException;
import project.exception.BadRequestException;
import project.exception.NotFoundException;
import project.exception.handler.NoValidException;
import project.repositories.RestaurantRepository;
import project.repositories.UserRepository;
import project.service.inter.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;
    private  final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           RestaurantRepository restaurantRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public SimpleResponse register(RegisterRequest registerRequest) {
        if (userRepository.getAllUsers().size() == 15) {
            log.error("There are currently no open vacancies");
            throw new AlreadyExistException("There are currently no open vacancies");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.error(String.format("User with login: %s is exists", registerRequest.getEmail()));
            throw new BadRequestException(String.format(
                    "User with login: %s is exists", registerRequest.getEmail()
            ));
        }
        userValid(registerRequest);
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .dateOfBirth(registerRequest.getDateOfBirth())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(registerRequest.getRole())
                .phoneNumber(registerRequest.getPhoneNumber())
                .experience(registerRequest.getExperience())
                .accepted(false)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your application has been sent!")
                .build();


    }

    @Override
    public UserInfoResponse authenticate(UserRequest userRequest) {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              userRequest.getEmail(),
              userRequest.getPassword()
      ));
        User user = userRepository.findUsersByEmail(userRequest.getEmail()).orElseThrow(() -> {
                    log.error(String.format("User %s is not found!", userRequest.getEmail()));
                    throw new NotFoundException(String.format("User %s is not found!", userRequest.getEmail()));
                }
        );
        if (user.getAccepted()) {
            return UserInfoResponse.builder()
                    .email(userRequest.getEmail())
                    .token(jwtUtil.generateToken(user))
                    .build();
        }
        return UserInfoResponse.builder()
                .email(userRequest.getEmail())
                .build();

    }



    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public SimpleResponse updateUser(Long userId, RegisterRequest registerRequest) {
        for (User user :userRepository.findAll()) {
        if(!user.getId().equals(userId) && user.getEmail().equals(registerRequest.getEmail())){
            log.error(String.format("User with login: %s is exists", registerRequest.getEmail()));
            throw new BadRequestException(String.format(
                    "User with login: %s is exists", registerRequest.getEmail()
            ));
        }
        }
        userValid(registerRequest);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error(String.format("User with id - %s is not found!", userId));
            throw new NotFoundException(String.format("User with id - %s is not found!", userId));
        });
         user.setFirstName(registerRequest.getFirstName());
         user.setLastName(registerRequest.getLastName());
         user.setPhoneNumber(registerRequest.getPhoneNumber());
         user.setDateOfBirth(registerRequest.getDateOfBirth());
         user.setExperience(registerRequest.getExperience());
         user.setEmail(registerRequest.getEmail());
         user.setRole(registerRequest.getRole());
        return SimpleResponse.builder().status(HttpStatus.CREATED).
                message(String.format("This is : %s : user successfully updated",user.getFirstName())).build();
    }

    @Override
    public UserResponse getByIdUser(Long userId) {
        log.error("User doesn't exist");
        return userRepository.getUserById(userId).orElseThrow(() ->
                new NotFoundException("Restaurant with id " + userId + "  doesn't exist"));

    }

    @Override
    public String deleteUser(Long userId) {
        boolean exists  = userRepository.existsById(userId);
        if(!exists){
            throw  new NotFoundException("Restaurant with id "+userId+" doesn't exist");
        }userRepository.deleteById(userId);
        log.info("User successfully deleted");
        return "Restaurant with id "+userId+" is deleted";
    }

    @Override
    public List<UserResponse> getApplications() {
        return userRepository.getAllApplication();
    }

    @Override
    public SimpleResponse acceptUserResponse(Long restaurantId, AcceptRequest acceptRequest) {
        User user = userRepository.findById(acceptRequest.userId()).
                orElseThrow(()->{log.error(String.format("User with id - %s is not found!",acceptRequest.userId()));
                   throw  new NotFoundException(String.format("User with id - %s is not found!",acceptRequest.userId()));
                });
        if(acceptRequest.accept()){
           user.setAccepted(true);
            restaurantRepository.findById(restaurantId).
                    orElseThrow(()->{log.error("Restaurant not Found");
                        throw  new NotFoundException("Restaurant not found");
                    });
            log.info(String.format("User : %s : is successfully accepted",user.getFirstName()));
            return new SimpleResponse(HttpStatus.ACCEPTED,
                    String.format("User : %s : is successfully accepted",user.getFirstName()));
        }else{
            log.info(String.format("User : %s : is rejected",user.getFirstName()));
         userRepository.delete(user);
        }
        return new SimpleResponse(HttpStatus.NOT_ACCEPTABLE,
                String.format("User : %s : is rejected",user.getFirstName()));
    }

    public void userValid(RegisterRequest registerRequest){

        int age = LocalDate.now().minusYears(registerRequest.getDateOfBirth().getYear()).getYear();
        if(registerRequest.getRole() == Role.CHEF){
          if(age < 25 || age > 45){
          log.error("Chef must be between 25 and 45 years old");
          throw new NoValidException("Chef must be between 25 and 45 years old");
          }
          if(registerRequest.getExperience() < 2){
              log.error("Chef experience must be more than 2 years");
              throw new NoValidException("Chef experience must be more than 2 years");
          }
              }else if (registerRequest.getRole() == Role.WATER) {
            if(age < 18 || age > 30 ){
                log.error("Waiter must be between 18 and 30 years old");
                throw new NoValidException("Waiter must be between 18 and 30 years old");
          }if(registerRequest.getExperience() < 1){
                log.error("Waiter experience must be more than 1 year");
                throw new NoValidException("Waiter experience must be more than 1 year");
            }
        }
    }
    @PostConstruct
    public void init(){
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setRole(Role.ADMIN);
        if(!userRepository.existsByEmail(user.getEmail())){
            userRepository.save(user);
        }
    }

}
