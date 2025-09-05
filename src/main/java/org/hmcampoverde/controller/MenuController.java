package org.hmcampoverde.controller;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmcampoverde.dto.MenuDto;
import org.hmcampoverde.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Validated
public class MenuController {

	private final MenuService menuService;

	@GetMapping("/findByRole/{role}")
	public ResponseEntity<List<MenuDto>> findByRole(@PathVariable("role") @Size(min = 5) String role) {
		return ResponseEntity.ok(menuService.findByRole(role));
	}
}
