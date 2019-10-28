package com.ravindra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ravindra.service.CSVReaderService;

@RestController
public class CSVReaderController {
	
	@Autowired
	private CSVReaderService csvReaderService;
	
	@GetMapping(value="/csv")
	public String getCsvData()
	{
		return csvReaderService.getCsvData();
	}
}