package com.joyveb.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.joyveb.common.gens.bizcom.vo.ListVO;

public class PDFExportView extends AbstractIText5PdfView {

	public void addCell(PdfPTable table, String value, int fontsize) {
		Phrase ph = new Phrase(new Chunk(value, getChineseFont(fontsize)));
		table.addCell(ph);

	}

	static String fontpath = null;

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (fontpath == null) {
			ServletContext sc = request.getSession().getServletContext();
			fontpath = sc.getRealPath("/../../");
			System.out.println("font path: " + fontpath);
		}

		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");

		String filename = (String) model.get("filename") + "_"
				+ df2.format(new Date());
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-type", "application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + ".pdf\"");

		Paragraph title = new Paragraph(new Chunk(
				(String) model.get("filename"), getChineseFont(24)));
		document.add(title);
		PdfPTable header = new PdfPTable(2);

		ListVO vo = (ListVO) model.get("listvo");

		Class clazz = (Class) model.get("clazz");
		addCell(header, "创建日期", 18);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));
		addCell(header, df.format(new Date()), 18);

		addCell(header, "总数", 18);

		addCell(header, "" + vo.getItems().size(), 18);
		Method[] methods = clazz.getMethods();
		List<Method> amethods = new ArrayList<Method>();
		{
			// cell.setCellStyle(dateStyle);
			for (int i = 0, mi = 0; i < methods.length; i++) {
				Method method = methods[i];
				if (method.getName().startsWith("get")
						&& !method.getName().equals("getClass")) {
					// sheetRow.createCell(mi +
					// 1).setCellValue(method.getName().substring(3));
					mi++;
					amethods.add(method);
				}
			}
		}
		// http://localhost:8080/WebAdmin/rest/user/exportlist.do?start=0&count=20&format=pdf
		document.add(header);
		PdfPTable table = new PdfPTable(amethods.size() + 1);
		table.addCell("");

		for (Method method : amethods) {
			addCell(table, method.getName().substring(3), 8);
		}

		int rowid = 0;
		for (Object item : vo.getItems()) {
			rowid++;
			table.addCell("" + rowid);
			for (Method method : amethods) {
				Object value = method.invoke(item);
				String str = "";
				if (value == null) {
				} else if (value.getClass().equals(Date.class)) {
					str = (df.format((Date) value));
				} else {
					str = (value.toString());
				}
				addCell(table, str, 8);

			}
		}
		document.add(table);

	}

	private static final Font getChineseFont(float size) {
		Font FontChinese = null;
		try {
			BaseFont bfChinese = BaseFont.createFont(fontpath+File.separator+"simsun.ttc,1",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			FontChinese = new Font(bfChinese, size, Font.NORMAL);
		} catch (DocumentException de) {
			de.printStackTrace();
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
		return FontChinese;
	}
	public static void main(String[] args) {
		fontpath="/Users/tany/Documents/ipwork/rdpshared";
		System.out.println(getChineseFont(12));
	}
}