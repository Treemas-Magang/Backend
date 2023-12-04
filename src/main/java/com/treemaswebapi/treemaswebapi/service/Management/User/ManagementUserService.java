package com.treemaswebapi.treemaswebapi.service.Management.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.treemaswebapi.treemaswebapi.config.JwtService;
import com.treemaswebapi.treemaswebapi.controller.Management.User.request.ManagementUserRequest;
import com.treemaswebapi.treemaswebapi.entity.SysUserEntity.SysUserEntity;
import com.treemaswebapi.treemaswebapi.repository.SysUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagementUserService {
    private final JwtService jwtService;
    private final SysUserRepository sysUserRepository;

    public ResponseEntity<Map<String, Object>> unlockAcc(
        String nik
        ) {
        try {
            Optional<SysUserEntity> userOptional = sysUserRepository.findByUserId(nik);
            if (userOptional.isPresent()) {

                if ("1".equals(userOptional.get().getIsLocked())) {
                    SysUserEntity user = userOptional.get();

                    user.setIsLocked("0");
                    user.setWrongPassCount((short) 0);

                    // Save the updated user
                    sysUserRepository.save(user);

                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Success");
                    response.put("message", "Account Unlocked");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                } else {
                    SysUserEntity user = userOptional.get();
                    Integer timesLocked = user.getTimesLocked();

                    user.setIsLocked("1");
                    user.setTimesLocked(timesLocked + 1);
                    // Save the updated user
                    sysUserRepository.save(user);

                    Map<String, Object> response = new HashMap<>();
                    response.put("status", "Success");
                    response.put("message", "Account Locked");
                    return ResponseEntity.status(HttpStatus.OK).body(response);
                }

                
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "Success");
                response.put("message", "Account not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // TODO: handle exception
            Map<String, Object> response = new HashMap<>();
            response.put("status", "Failed");
            response.put("message", "Failed To Unlock Account");
            response.put("error", e.getMessage());
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
