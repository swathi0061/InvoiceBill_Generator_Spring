package com.dao;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import java.util.StringTokenizer;
import org.springframework.stereotype.Repository;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;


@Repository
public class Invoice_DAO {
	private static BaseFont italic;
	private static BaseFont bf;
	private static BaseFont font;
	int y = 450;
	int y1 = 580;
	int y2 = 455;
	int x = 388;
	int carton_no = 0;
	int x1 = 375;
	int x2 = 368;
	int finalY = 455;
	int si_no = 1;
	int row_count = 0;

	public String generateBillPDF() throws DocumentException {
		y = 450;
		y1 = 580;
		y2 = 455;
		x = 388;
		finalY = 455;
		si_no = 1;
		row_count = 0;
		Document doc = new Document(PageSize.A4);
		PdfWriter docWriter = null;
		String FilePath = "";
		String filename = "Invoice_Print.pdf";
		initializeFonts();
		try {

			FilePath = "D:\\java_workspace\\InvoiceBill_Generator_Spring\\ExportFiles/" + filename;
			docWriter = PdfWriter.getInstance(doc, new FileOutputStream(FilePath));
			doc.open();
			PdfContentByte cb = docWriter.getDirectContent();
			generateLayout(doc, cb);
			generateHeaderDetails(doc, cb);
			for (int i = 0; i < 25; i++) {
				row_count++;
				generateDetails(doc, cb);
			}

			getImage(doc, cb);
			doc.newPage();
			y = 450;
			y1 = 580;
			y2 = 455;
			x = 388;
			carton_no = 0;
			x1 = 375;
			x2 = 368;
			finalY = 455;
			si_no = 1;
			row_count = 0;

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		} finally {

			try {
				if (doc != null) {
					doc.close();

				}
				if (docWriter != null) {
					docWriter.close();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return filename;

	}

	private void initializeFonts() throws DocumentException, DocumentException {
		try {
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			font = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			italic = BaseFont.createFont(BaseFont.HELVETICA_BOLDOBLIQUE, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}

	}

	private void generateDetails(Document doc, PdfContentByte cb) {
		try {

			

			if (row_count > 15) {
				cb.setRGBColorFill(0, 0, 0);
				subHeadingFont(cb, 303, y2, "" + si_no);
				subHeadingFont(cb, 340, y2, "Dress,Chocolates,Gifts");
				subHeadingFont(cb, 496, y2, "" + Math.round(Math.random()));
				y2-=20;
				cb.stroke();
			} else {
				cb.setRGBColorFill(0, 0, 0);
				subHeadingFont(cb, 15, finalY, "" + si_no);
				subHeadingFont(cb, 45, finalY, "Dress,Chocolates,Gifts");
				subHeadingFont(cb, 198, finalY, "" + Math.round(Math.random()));
				finalY -= 20;
				cb.stroke();
			}
			
			si_no++;

		} catch (Exception ex) {

			ex.getMessage();
		}

	}

	private void generateHeaderDetails(Document doc, PdfContentByte cb) {
		try {
			cb.setRGBColorFill(0, 0, 0);
			createSubTitle(cb, 65, 750, "8952746745");
			subHeadingFont(cb, 16, 704, "Bangalore");
			subHeadingFont(cb, 420, 760, "43567210");
			subHeadingFont(cb, 104, 704, "Dubai");
			subHeadingFont(cb, 212, 707, "25/05/2025");
			subHeadingFont(cb, 212, 696, "11:48");
			createSubTitle(cb, 320, 700, "9786543210");

			subHeadingFont(cb, 320, 638, "ABC");
			subHeadingFont(cb, 470, 638, "XYZ");

			subHeadingFont(cb, 340, 605, "MNO");

			subHeadingFont(cb, 320, 560, "10");
			subHeadingFont(cb, 470, 560, "69.50");

			AddressAlignment(cb, 16, 657,
					"Mohammed Abdul, MG Road, Sector 45 Bangalore, Karnataka - 560001, India, Phone: +91-8004567890",
					45, 12);
			AddressAlignment(cb, 16, 576,
					"Sharif, Suite 15, Al Quoz Industrial Area 3, Dubai, United Arab Emirates, P.O. Box: 789012, Phone: +971-50-987-6543",
					45, 12);

			contentInputFont(cb, 68, 158, "Good");
			subHeadingFont(cb, 528, 147, "0.0");
			subHeadingFont(cb, 528, 132, "0.0");
			subHeadingFont(cb, 528, 102, "0.0");
			subHeadingFont(cb, 528, 117, "10");
			subHeadingFont(cb, 528, 87, "28000.00");

			String carton_no_weight = "10.7,12.2,4.2,5.5,7.8,6.5,3.2,1.6,8.8,9.0";
			String[] values = carton_no_weight.split(",");
			for (String value : values) {
				contentInputFont(cb, x2, 510, value);
				x2 += 21;
			}
			cb.stroke();
		} catch (Exception ex) {

			ex.getMessage();
		}

	}

	private void generateLayout(Document doc, PdfContentByte cb) {
		try {

			cb.setLineWidth(1);
			cb.setRGBColorStroke(255, 0, 0);
			cb.rectangle(10, 690, 280, 50); // origin rectangle
			cb.rectangle(10, 610, 280, 75); // shipper rectangle
			cb.rectangle(10, 500, 280, 105); // receiver rectangle

			cb.rectangle(295, 670, 290, 70); // inv no rectangle
			cb.rectangle(295, 630, 148, 35); // packed by rectangle
			cb.rectangle(448, 630, 138, 35); // issued by rectangle
			cb.rectangle(295, 590, 290, 35); // type rectangle
			cb.rectangle(295, 550, 148, 35); // no of pcs rectangle
			cb.rectangle(448, 550, 138, 35); // weight rectangle

			cb.rectangle(295, 500, 290, 45); // carton weight rectangle

			cb.rectangle(10, 96, 240, 61); // REMARKS rectangle
			cb.rectangle(10, 32, 575, 52); // terms rectangle
			cb.rectangle(370, 84, 215, 73); // amount rectangle

			cb.moveTo(90, 740);// origin ver line
			cb.lineTo(90, 690);

			cb.moveTo(209, 740);// destination ver line
			cb.lineTo(209, 690);

			cb.moveTo(10, 720);// origin hor line
			cb.lineTo(290, 720);

			cb.moveTo(365, 545);// carton weight ver line
			cb.lineTo(365, 500);

			for (int i = 1; i < 10; i++) {
				cb.moveTo(x, 545);// carton weight ver line
				cb.lineTo(x, 500);
				x += 21;
			}
			for (int i = 1; i <= 10; i++) {
				carton_no++;
				subHeadingFont(cb, x1, 529, "" + carton_no);
				x1 += 21;
			}
			cb.moveTo(365, 523);// carton weight hor line
			cb.lineTo(585, 523);

			cb.rectangle(10, 170, 575, 325); // packing rectangle

			cb.moveTo(10, 470);// si no hor line
			cb.lineTo(585, 470);

			cb.moveTo(35, 470);// si no ver line1
			cb.lineTo(35, 170);

			cb.moveTo(190, 470);// item ver line1
			cb.lineTo(190, 170);

			cb.moveTo(225, 470);// qty ver line1
			cb.lineTo(225, 170);

			cb.moveTo(295, 470);// value ver line 1
			cb.lineTo(295, 170);

			cb.moveTo(325, 470);// si no ver line2
			cb.lineTo(325, 170);

			cb.moveTo(488, 470);// item ver line2
			cb.lineTo(488, 170);

			cb.moveTo(523, 470);// qty ver line2
			cb.lineTo(523, 170);

			cb.moveTo(523, 157);// amount ver line
			cb.lineTo(523, 84);

			cb.moveTo(370, 142);// amount hor line
			cb.lineTo(585, 142);

			cb.moveTo(370, 127);// other charges hor line
			cb.lineTo(585, 127);

			cb.moveTo(370, 112);// discount hor line
			cb.lineTo(585, 112);

			cb.moveTo(370, 97);// vat hor line
			cb.lineTo(585, 97);
			cb.stroke();

			cb.setLineWidth(0.1f);
			cb.setRGBColorStroke(212, 7, 38);
			for (int i = 1; i <= 14; i++) {
				cb.moveTo(10, y);// si no hor line
				cb.lineTo(585, y);
				y -= 20;
			}
			cb.stroke();

			cb.setLineWidth(27f);
			cb.setRGBColorStroke(212, 7, 38);
			cb.moveTo(10, 481);// si no hor line
			cb.lineTo(585, 481);
			cb.stroke();

			cb.setLineWidth(11f);
			cb.setRGBColorStroke(212, 7, 38);
			cb.moveTo(10, 89.6f);// terms hor line
			cb.lineTo(250, 89.6f);
			cb.stroke();

			// copy
			cb.setLineWidth(1);
			cb.setRGBColorStroke(255, 255, 255);
			cb.moveTo(35, 495);// si no ver line1
			cb.lineTo(35, 468);

			cb.moveTo(190, 495);// item ver line1
			cb.lineTo(190, 468);

			cb.moveTo(225, 495);// qty ver line1
			cb.lineTo(225, 468);

			cb.moveTo(295, 495);// value ver line 1
			cb.lineTo(295, 468);

			cb.moveTo(325, 495);// si no ver line2
			cb.lineTo(325, 468);

			cb.moveTo(488, 495);// item ver line2
			cb.lineTo(488, 468);

			cb.moveTo(523, 495);// qty ver line2
			cb.lineTo(523, 468);

			cb.stroke();
			cb.setRGBColorFill(0, 0, 0);
			subHeadingFont(cb, 270, 760, "Packing List");
			cb.stroke();

			cb.setRGBColorFill(0, 0, 139);
			createSubTitle(cb, 10, 750, "VAT NO.:");
			createArabicText(cb, 270, 774, "قائمة التعبئة");// packing list
			subHeadingFont(cb, 380, 760, "CR NO :");

			subHeadingFont(cb, 12, 727, "Origin");
			createArabicText(cb, 60, 727, "الأصل");// origin
			subHeadingFont(cb, 91, 727, "Destination");
			createArabicText(cb, 148, 727, "المكان المقصود");// Destination
			subHeadingFont(cb, 212, 727, "Date");
			createArabicText(cb, 260, 727, "تاريخ");// date
			subHeadingFont(cb, 12, 675, "Shipper");
			createArabicText(cb, 255, 675, "الشاحن :");// shipper
			subHeadingFont(cb, 12, 614, "VAT No :");
			subHeadingFont(cb, 12, 592, "Receiver");
			createArabicText(cb, 246, 592, "المتلقي :");// Receiver

			subHeadingFont(cb, 300, 727, "Invoice No.:");
			createArabicText(cb, 535, 727, "رقم الفاتورة");// invoice no
			subHeadingFont(cb, 300, 655, "Packed By:");
			createArabicText(cb, 380, 655, "معبأة بواسطة :");// packed by
			subHeadingFont(cb, 450, 655, "Issued By:");
			createArabicText(cb, 545, 655, "صادرة عن :");// issued by
			subHeadingFont(cb, 300, 605, "Type:");
			createArabicText(cb, 565, 605, "نوع :");// type
			subHeadingFont(cb, 300, 575, "No. of Pcs :");
			createArabicText(cb, 395, 575, "عدد القطع :");// no of pcs
			subHeadingFont(cb, 450, 575, "Weight:");
			createArabicText(cb, 560, 575, "الوزن :");// weight
			subHeadingFont(cb, 310, 525, "Carton");
			subHeadingFont(cb, 310, 513, "Weight");
			createArabicText(cb, 306, 535, "وزن الكرتون");// carton weight

			subHeadingFont(cb, 16, 158, "Remarks : ");
			createArabicText(cb, 553, 161, "ملاحظات :");// Remarks

			titleTextFont(cb, 16, 20, "Shipper's Signature");
			createArabicText(cb, 90, 20, "توقيع الشاحن");
			titleTextFont(cb, 380, 20, "Checked & Received by");
			createArabicText(cb, 470, 20, "تم الفحص والاستلام بواسطة");

			subHeadingFont(cb, 375, 147, "Amount");
			createArabicText(cb, 500, 147, "كمية");
			subHeadingFont(cb, 375, 132, "Other Charge");
			createArabicText(cb, 485, 132, "تهمة أخرى");
			subHeadingFont(cb, 375, 117, "Discount");
			createArabicText(cb, 485, 117, "تخفيض");
			subHeadingFont(cb, 375, 102, "VAT     %");
			createArabicText(cb, 429, 102, "ضريبة القيمة المضافة");
			subHeadingFont(cb, 375, 87, "Net Total");
			createArabicText(cb, 467, 87, "صافي المجموع");
			cb.stroke();

			cb.setRGBColorFill(255, 0, 0);
			titleTextFont(cb, 16, 147, "PROHIBITED & RESTRICTED GOODS");
			createArabicTextFont(cb, 170, 147, "البضائع المحظورة والمقيدة");// PROHIBITED & RESTRICTED GOODS
			cb.stroke();
			
			cb.setRGBColorFill(0, 0, 0);
			contentTermsFont(cb, 16, 138, "1. Explosives (fireworks, flares, ammunition)");
			contentTermsFont(cb, 16, 128, "2. Flammable liquids (gasoline, alcohol, paint thinner)");
			contentTermsFont(cb, 16, 118, "3. Animal carcasses or remains");
			contentTermsFont(cb, 16, 108, "4. Firearms, parts of guns");
			contentTermsFont(cb, 16, 98, "5. Chemicals (corrosive, oxidizing agents)");
			cb.stroke();
			
			cb.setRGBColorFill(255, 255, 255);
			subHeadingFont(cb, 13, 480, "S.N.");
			subHeadingFont(cb, 45, 480, "Item Description");
			createArabicText(cb, 130, 480, "وصف السلعة");// item descrition
			subHeadingFont(cb, 200, 476, "Qty.");
			createArabicText(cb, 200, 487, "كمية");// qty
			subHeadingFont(cb, 235, 480, "Value");
			createArabicText(cb, 270, 480, "قيمة");// value

			subHeadingFont(cb, 300, 480, "S.N.");
			subHeadingFont(cb, 340, 480, "Item Description");
			createArabicText(cb, 436, 480, "وصف السلعة");// item descrition
			subHeadingFont(cb, 498, 476, "Qty.");
			createArabicText(cb, 498, 487, "كمية");// qty
			subHeadingFont(cb, 533, 480, "Value");
			createArabicText(cb, 563, 480, "قيمة");// value

			content11(cb, 16, 87, "Terms And Conditions");
			
			cb.setRGBColorFill(0, 0, 0);
			contentTermsFont(cb, 16, 77, "1. The logistics provider reserves the right to inspect and refuse any package suspected of containing prohibited or dangerous goods.");
			contentTermsFont(cb, 16, 67, "2. If the recipient is unavailable, the company may attempt re-delivery or return the package.");
			contentTermsFont(cb, 16, 57, "3. Shippers are responsible for ensuring their shipment complies with the prohibited/restricted goods policy.");
			contentTermsFont(cb, 16, 47, "4. Any disputes must be raised in writing within a set time period (e.g., 7 days from delivery). Jurisdiction will be as per the company’s registered location.");
			contentTermsFont(cb, 16, 37, "5. The company is not liable for delays due to weather, customs clearance, or force majeure. Liability for lost/damaged items is limited unless insurance is purchased.");
			cb.stroke();
			
			cb.stroke();
		}

		catch (Exception ex) {

			ex.getMessage();
		}

	}

	public static void createSubTitle(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(font, 12);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	public static void contentInputFont(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(font, 7.5f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}


	private static void getImage(Document doc, PdfContentByte cb)
			throws MalformedURLException, IOException, DocumentException {
		try {

			Image companyLogo = Image.getInstance(
					"https://img.freepik.com/premium-vector/template-truck-logo-cargo-delivery-logistic_712682-1133.jpg");
			companyLogo.setAbsolutePosition(1, 675);
			companyLogo.scalePercent(40);

			doc.add(companyLogo);
		} catch (Exception e) {
			System.out.println("Image not Supported" + e.getMessage());
		}
	}

	public static void titleTextFont(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(italic, 7f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	public static void createArabicTextFont(PdfContentByte cb, float x, float y, String text) {
		try {
			// Load an Arabic-capable font, such as Amiri (you can change this to a font on
			// your system)
			BaseFont arabicFont = BaseFont.createFont(
					"D:\\java_workspace\\InvoiceBill_Generator_Spring\\Asset\\Amiri Bold\\Amiri Bold.ttf",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			String reversedText = new StringBuilder(text).reverse().toString();
			// Begin text with the new font
			cb.beginText();
			cb.setFontAndSize(arabicFont, 6f);
			cb.setTextMatrix(x, y);
			cb.showText(reversedText.trim()); // Display Arabic text
			cb.endText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void createArabicText(PdfContentByte cb, float x, float y, String text) {
		try {

			BaseFont arabicFont = BaseFont.createFont(
					"D:\\java_workspace\\InvoiceBill_Generator_Spring\\Asset\\Amiri Bold\\Amiri Bold.ttf",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			String reversedText = new StringBuilder(text).reverse().toString();
			// Begin text with the new font
			cb.beginText();
			cb.setFontAndSize(arabicFont, 8f);
			cb.setTextMatrix(x, y);
			cb.showText(reversedText.trim()); // Display Arabic text
			cb.endText();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void content20(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(italic, 6f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	public static void content11(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(italic, 10f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	public static void subHeadingFont(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(font, 10f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	public static void contentTermsFont(PdfContentByte cb, float x, float y, String text) {
		cb.beginText();
		cb.setFontAndSize(bf, 7f);
		cb.setTextMatrix(x, y);
		cb.showText(text.trim());
		cb.endText();
	}

	private void AddressAlignment(PdfContentByte cb, int x, float y, String stringData, int totalChar,
			int newLineSpace) {
		String space = ", ";
		String beginingAddressLine = "";
		String finalAddressLine = "";
		String delimeter = "#,~, ";
		StringTokenizer st = new StringTokenizer(stringData, delimeter);

		while (st.hasMoreElements()) {
			beginingAddressLine = st.nextToken();
			finalAddressLine = finalAddressLine + beginingAddressLine + space;
			if (finalAddressLine.length() >= totalChar) {
				if (finalAddressLine.length() == totalChar) {
					contentAddressFont(cb, (float) x, (float) y, finalAddressLine.substring(0,
							finalAddressLine.length() - (beginingAddressLine.length() + 2)), 4);
					y -= newLineSpace;
				} else {
					contentAddressFont(cb, (float) x, (float) y, finalAddressLine.substring(0,
							finalAddressLine.length() - (beginingAddressLine.length() + 2)), 4);
					y -= newLineSpace;
				}

				finalAddressLine = "";
				finalAddressLine = finalAddressLine + beginingAddressLine + space;
			}
		}

		if (finalAddressLine.length() != 0) {
			contentAddressFont(cb, x, y, finalAddressLine, 3);
		}

	}

	private static void contentAddressFont(PdfContentByte cb, float x, float y2, String text, int alignJustified) {
		// TODO Auto-generated method stub
		cb.beginText();
		cb.setFontAndSize(font, 10);
		cb.setTextMatrix(x, y2);
		cb.showText(text.trim());
		cb.endText();
	}
}
