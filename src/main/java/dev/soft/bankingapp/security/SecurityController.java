package dev.soft.bankingapp.security;

import lombok.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.oauth2.jose.jws.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {


    private AuthenticationManager authenticationManager;
    private JwtEncoder jwtEncoder;

    @GetMapping("/profile")
    public  Authentication authentication(Authentication authentication){
        return authentication;
    }

  //To authenticate user will return a token to sent to client
    @PostMapping("/login")
    public Map<String, String> login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String scope = authentication.getAuthorities().stream().map(au -> au.getAuthority()).collect(Collectors.joining(" "));
        Instant instant = Instant.now();

        //jwt token generation
        JwtClaimsSet jwtClainsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("authorities", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
               JwsHeader.with(MacAlgorithm.HS512).build(), jwtClainsSet
        );
        String jwt  = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token", jwt);

    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {

        Map<String, Object> map = new HashMap<>();

        map.put("name", authentication.getName());
        map.put(
                "authorities",
                authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

        return map;
    }

}
