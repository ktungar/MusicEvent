package com.eventful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventful.requestresponce.MusicEventRequestResponce;
import com.eventful.service.EventService;
import com.eventful.service.impl.EventServiceImpl;

@RestController
public class EventRestController {
	@Autowired
	EventService eventService;
	@GetMapping(produces="application/json",path="/event")
	public List<MusicEventRequestResponce> getEvent() {
		return	eventService.getEvent();

	}
}
