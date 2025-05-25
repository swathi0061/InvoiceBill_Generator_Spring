package com.service;

import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;

@Service
public interface Invoice_Service {

	public String generateBillPDF() throws DocumentException;
}
