package com.webflix.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

final class ResponseLists {

	private ResponseLists() {
	}

	static ResponseEntity<?> okOrNotFound(List<?> body) {
		if (body.isEmpty()) {
			return ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.NOT_FOUND)).build();
		}
		return ResponseEntity.ok(body);
	}

}
