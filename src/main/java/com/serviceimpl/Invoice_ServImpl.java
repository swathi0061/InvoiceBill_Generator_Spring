package com.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dao.Invoice_DAO;
import com.itextpdf.text.DocumentException;
import com.service.Invoice_Service;

@Component
public class Invoice_ServImpl implements Invoice_Service {

	@Autowired
	private Invoice_DAO dao;
	@Override
	public String generateBillPDF() throws DocumentException{
		return dao.generateBillPDF();
	}

	
	
}
