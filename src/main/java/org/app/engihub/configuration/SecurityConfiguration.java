package org.app.engihub.configuration;

import jakarta.servlet.http.HttpServletResponse;
import org.app.engihub.filter.AuthFilter;
import org.app.engihub.filter.JwtFilter;
import org.app.engihub.model.UserModel;
import org.app.engihub.repository.UserRepository;
import org.app.engihub.service.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtFilter jwtFilter;
     
     @Bean
     public UserDetailsService userDetailsService() {
         return myUserDetailsService;
     }

     @Bean
     public AuthFilter authFilter(AuthenticationManager authenticationManager) {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setFilterProcessesUrl("");
        authFilter.setAuthenticationManager(authenticationManager);
        authFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {

            response.setStatus(HttpServletResponse.SC_OK);

            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            UserModel userModel = userRepository.findByEmail(userPrincipal.getUsername()).
                    orElseThrow(() -> new RuntimeException("User not found"));
            String accessToken = jwtService.createAccessToken(userModel.getEmail());
            Map<String,Object> data = new HashMap<>();
            data.put("token", accessToken);
            data.put("role",userModel.getRole());
            data.put("userId",userModel.getId());

            response.getWriter().write(objectMapper.writeValueAsString(data));
        }));
        authFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String,Object> data = new HashMap<>();
            data.put("error", exception.getMessage());
            data.put("status", response.getStatus());
            data.put("path",request.getRequestURI());

            response.getWriter().write(objectMapper.writeValueAsString(data));
        }));
        return authFilter;
     }
     
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) {
         return httpSecurity.cors(cors -> cors.disable())
                 .csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(requests -> requests.requestMatchers("")
                         .permitAll().anyRequest().authenticated())
                 .addFilterAt(authFilter(authenticationManager),UsernamePasswordAuthenticationFilter.class)
                 .addFilterAfter(jwtFilter,UsernamePasswordAuthenticationFilter.class)
                 .build();
     }
}
