package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.service.Invoice_Service;

@RestController
@RequestMapping(path="/Generate/Bill")
public class Invoice_Controller {

	@Autowired
	private Invoice_Service serv;
	
	@PostMapping("/Invoice")
	public String generateBillPDF() throws DocumentException{
		return serv.generateBillPDF();
	}
}
