package franxx.code.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
    }).build();
  }

}
