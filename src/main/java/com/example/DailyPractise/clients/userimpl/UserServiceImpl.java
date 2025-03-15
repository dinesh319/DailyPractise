package com.example.DailyPractise.clients.userimpl;

import com.example.DailyPractise.clients.UserService;
import com.example.DailyPractise.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RestClient restClient;
    private final ModelMapper modelMapper;

    @Override
    public List<?> getAllUsers() {

        try{
            Map response = restClient.get().uri("users").retrieve().body(Map.class);
            assert response != null;
            return (List<?>) response.get("data");
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public UserDto getUserById(Long userId) {
        try{
            Map response = restClient.get().uri("users/{userId}",userId).retrieve().body(Map.class);
            return  modelMapper.map(response.get("data"),UserDto.class);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(UserDto user) {
        try{
            ResponseEntity<Map> response = restClient.post().uri("users").body(user).retrieve().toEntity(Map.class);
            if(response.getStatusCode().is2xxSuccessful() ){
                System.out.println("user is created");
            }else{
                System.out.println("something went wrong");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Long userId) {
            try{
            ResponseEntity<Map> response =    restClient.delete().uri("users/{userId}",userId).retrieve().toEntity(Map.class);
            if(response.getStatusCode().is2xxSuccessful()){
                System.out.println("user deleted successfully");
            }else{
                System.out.println(response.getStatusCode());
            }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

}
