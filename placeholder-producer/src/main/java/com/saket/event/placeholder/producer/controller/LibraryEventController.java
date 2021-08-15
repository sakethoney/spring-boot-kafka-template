package com.saket.event.placeholder.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saket.event.placeholder.producer.domain.LibraryEvent;
import com.saket.event.placeholder.producer.service.LibraryEventService;

@RestController
public class LibraryEventController {

	@Autowired
	LibraryEventService libraryEventService;
	
	@PostMapping("/v1/libraryevent")
	public ResponseEntity<LibraryEvent> postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		libraryEventService.sendLibraryEevent(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	@PostMapping("/v1/libraryeventsync")
	public ResponseEntity<LibraryEvent> postLibraryEventSync(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		libraryEventService.sendLibraryEeventSynchronous(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	@PostMapping("/v1/libraryeventsyncTimeout")
	public ResponseEntity<LibraryEvent> postLibraryEventSyncTimeout(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		libraryEventService.sendLibraryEeventSyncTimeout(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
	
	@PostMapping("/v1/libraryeventwithtopic")
	public ResponseEntity<LibraryEvent> postLibraryEventWithTopic(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException{
		
		libraryEventService.sendLibraryEventWithTopic(libraryEvent);
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
	}
}
