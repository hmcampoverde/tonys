package org.hmcampoverde.controller;

import jakarta.validation.Valid;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.LoginDto;
import org.hmcampoverde.dto.TokenDto;
import org.hmcampoverde.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
		return ResponseEntity.ok(userService.login(loginDto.getUsername(), loginDto.getPassword()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenDto> refresh(@RequestBody TokenDto toketDto) throws ParseException {
		return ResponseEntity.ok(userService.refresh(toketDto.getToken()));
	}
}
