package treemas.backend.Controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import treemas.backend.Model.User;
import treemas.backend.Repo.UserRepository;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/logindulu")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByNik(user.getId());
    
        if (existingUser == null) {
            // User not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("User not found"));
        }
    
        String storedSalt = userRepository.findSaltByNik(user.getId());
    
        if (storedSalt == null) {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Authentication failed"));
        }
    
        String saltedPassword = storedSalt + user.getPassword();
        String hashedPasswordFromDatabase = userRepository.findPasswordByNik(user.getId());
    
        if (hashedPasswordFromDatabase == null) {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Authentication failed"));
        }
    
        if (passwordEncoder.matches(saltedPassword, hashedPasswordFromDatabase)) {
            // Authentication successful
            String token = Jwts.builder()
                    .setSubject(existingUser.getId())
                    .signWith(SignatureAlgorithm.HS256, "yourSecretKey")
                    .compact();
    
            // Return the token as a JSON response
            return ResponseEntity.ok(new TokenResponse(token));
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Authentication failed"));
        }
    }

class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    // Other API 
}
}