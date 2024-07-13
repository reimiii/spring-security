package franxx.code.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConf {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.authorizeHttpRequests(registry -> {
      registry.requestMatchers("/home").permitAll();
      registry.requestMatchers("/admin/**").hasRole("ADMIN");
      registry.requestMatchers("/user/**").hasRole("USER");
      registry.anyRequest().authenticated();
    }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails normalUser = User.builder()
        .username("me")
        .password("$2a$12$wm8tQFFbYMSRaQtiB/io5.iaVMGlRgmMN7RpJhYcdk6/.dH6nIWee")
        .roles("USER")
        .build();
    UserDetails adminUser = User.builder()
        .username("admin")
        .password("$2a$12$wm8tQFFbYMSRaQtiB/io5.iaVMGlRgmMN7RpJhYcdk6/.dH6nIWee")
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(adminUser, normalUser);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
