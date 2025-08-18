package org.hmcampoverde.configuration;

import lombok.RequiredArgsConstructor;
import org.hmcampoverde.security.SecurityEntryPoint;
import org.hmcampoverde.security.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final SecurityEntryPoint securityEntryPoint;
	private final SecurityFilter securityFilter;
	private final CorsConfigurationSource corsConfigurationSource;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource))
			.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizeHttpRequests ->
				authorizeHttpRequests.requestMatchers("/auth/**").permitAll().anyRequest().authenticated()
			)
			.exceptionHandling(handling -> handling.authenticationEntryPoint(securityEntryPoint))
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}
}
