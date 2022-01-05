package com.adulger.bellapi;

import com.adulger.bellapi.bean.ApplicationBean;
import com.adulger.bellapi.dto.BellDTO;
import com.adulger.bellapi.dto.UserDTO;
import com.adulger.bellapi.service.BellService;
import com.adulger.bellapi.service.UserService;
import com.adulger.bellapi.service.UserServiceImpl;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@SpringBootApplication
@RestController
public class BellapiApplication {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserService userService;
    @Autowired
    BellService bellService;

    @Autowired
    ApplicationBean applicationBean;

    public static void main(String[] args) {
        SpringApplication.run(BellapiApplication.class, args);
    }

    // localhost:8080/hello?name=adulger
    @GetMapping("/hello")
    public String requestParam(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    // localhost:8080/hello/adulger
    @GetMapping("/hello/{name}")
    public String pathParam(@PathVariable String name) {
        return String.format("Hello %s!", name);
    }


    @GetMapping("/users")
    public ResponseEntity getUsers() {
        List<UserDTO> userDTOList = userService.findAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(userDTOList.size()));
        return new ResponseEntity(userDTOList, headers, OK);
    }

    @GetMapping("/user/{userUniqueId}")
    public UserDTO getUser(@PathVariable String userUniqueId) {
        return userServiceImpl.findBy(userUniqueId).toDTO();
    }

    /**
     * Spring Security'de auth olan kullanıcı geri dönülür.
     *
     * @return
     */
    @GetMapping("/login")
    public UserDTO login() {
        return userService.login();
    }

    @GetMapping("/userBells")
    public ResponseEntity getUserBellsBy() {
        List<BellDTO> bellDTOList = bellService.findBy();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(bellDTOList.size()));
        return new ResponseEntity(bellDTOList, headers, OK);
    }

    @GetMapping("/bell/{bellUniqueId}")
    public BellDTO getBellBy(@PathVariable String bellUniqueId) {
        return bellService.getBellBy(bellUniqueId);
    }

    @PostMapping("/changeNotificationStatus/{bellUniqueId}/{status}")
    public void changeNotificationStatus(@PathVariable String bellUniqueId, @PathVariable boolean status) {
        bellService.changeNotificationStatus(bellUniqueId, status);
    }

    @PostMapping("/bellRinging/{bellUniqueId}")
    public void bellRinging(@PathVariable String bellUniqueId) {
        try {
            bellService.bellRinging(bellUniqueId);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/bell/{bellUniqueId}")
    public void removeBellFromUser(@PathVariable String bellUniqueId) {
        try {
            bellService.removeBellFromUser(bellUniqueId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/init/{object}")
    public String init(@PathVariable String object) {
        switch (object) {
            case "user":
                userService.init(10);
                break;
            case "bell":
                bellService.init(20);
                break;
        }
        return "";
    }
}
