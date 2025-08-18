package org.hmcampoverde.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

	@GetMapping("/findById")
	public ResponseEntity<String> findById() {
		return ResponseEntity.ok("Es una prueba");
	}
}
