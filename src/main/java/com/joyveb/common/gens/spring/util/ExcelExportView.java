package com.joyveb.common.gens.spring.util;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.joyveb.common.gens.bizcom.vo.ListVO;

public class ExcelExportView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tmplpath = (String) model.get("tmplpath");
		HSSFSheet sheet = workbook.createSheet("list");
		sheet.setDefaultColumnWidth(12);
		HSSFWorkbook tmplbook = null;
		HSSFSheet tmplsheet = null;

		if (tmplpath != null) {
			try {
				tmplbook = new HSSFWorkbook(new FileInputStream(new File(
						new File(tmplpath), (String) model.get("filename")
								+ ".xls")));
				tmplsheet = tmplbook.getSheetAt(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");

		String filename = (String) model.get("filename") + "_"
				+ df2.format(new Date());
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Content-type", "application/xls");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ filename + ".xls\"");
		HSSFCell cell = getCell(sheet, 0, 0);
		ListVO vo = (ListVO) model.get("listvo");

		Class clazz = (Class) model.get("clazz");

		HSSFCellStyle dateStyle = workbook.createCellStyle();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));
		getCell(sheet, 0, 0).setCellValue("创建日期");
		getCell(sheet, 0, 1).setCellValue(df.format(new Date()));
		getCell(sheet, 1, 0).setCellValue("总数");
		getCell(sheet, 1, 1).setCellValue(vo.getItems().size());
		Method[] methods = clazz.getMethods();
		{
			// cell.setCellStyle(dateStyle);
			HSSFRow sheetRow = sheet.createRow(3);

			if (tmplsheet != null) {
				Method[] tmplmethods = new Method[methods.length];

				HSSFRow tmplsheetRow = tmplsheet.getRow(3);
				HSSFRow tmplValueRow = tmplsheet.getRow(4);
				sheetRow.setHeight(tmplsheetRow.getHeight());
				if (tmplsheetRow.getRowStyle() != null) {
					HSSFCellStyle style = workbook.createCellStyle();
					System.out.println("row style="
							+ tmplsheetRow.getRowStyle());
					style.cloneStyleFrom(tmplsheetRow.getRowStyle());
					sheetRow.setRowStyle(style);
				}
				// copy 1
				tmplmethods[0]=null;
				for (int ci = 0; ci < methods.length; ci++) {
					HSSFCell tmplcell = tmplsheetRow.getCell(ci );
					if (tmplcell == null)
						continue;
					HSSFCell ncell = sheetRow.createCell(ci);
					System.out.println("cellstyle=" + tmplcell.getCellStyle());
					if (tmplcell.getCellStyle() != null) {
						HSSFCellStyle style = workbook.createCellStyle();
						style.cloneStyleFrom(tmplcell.getCellStyle());
						ncell.setCellStyle(style);
					}
					ncell.setCellType(tmplcell.getCellType());
					if (tmplcell.getCellComment() != null)
						ncell.setCellComment(tmplcell.getCellComment());
					ncell.setCellValue(tmplcell.getStringCellValue());
					if (ci < 0 || tmplValueRow.getCell(ci) == null)
						continue;
					String field = tmplValueRow.getCell(ci)
							.getStringCellValue();
					tmplmethods[ci]=null;
					if (ci>0&&field != null && field.length() > 0) {
						String methodname = "get" + field;
						for (int mi = 0; mi < methods.length; mi++) {
							Method method = methods[mi];
							if (method.getName().equals(methodname)) {
								tmplmethods[ci-1] = method;
								break;
							}
						}
					}
				}
				methods = tmplmethods;

			} else {
				for (int i = 0, mi = 0; i < methods.length; i++) {
					Method method = methods[i];
					if (method.getName().startsWith("get")
							&& !method.getName().equals("getClass")&& !method.getName().equals("getUuid")) {
						sheetRow.createCell(mi + 1).setCellValue(
								method.getName().substring(3));
						mi++;
					}
				}
			}
		}
		int rowid = 0;
		int cellstartrow = 4;

		HSSFRow tmplValueRow = null;

		HSSFCellStyle tmplrowstyle = null;
		if (tmplsheet != null) {
			tmplValueRow = tmplsheet.getRow(4);
			tmplrowstyle = tmplValueRow.getRowStyle();
		}
		for (Object item : vo.getItems()) {
			HSSFRow sheetRow = sheet.createRow(rowid + cellstartrow);
			rowid++;
			sheetRow.createCell(0).setCellValue(rowid);

			if (tmplsheet != null) {

				HSSFCell tmplcell = tmplValueRow.getCell(0);
				if (tmplcell != null) {
					HSSFCell ncell = sheetRow.getCell(0);
					if (tmplcell.getCellStyle() != null) {
						HSSFCellStyle style = workbook.createCellStyle();
						style.cloneStyleFrom(tmplcell.getCellStyle());
						ncell.setCellStyle(style);
					}
				}
			}

			if (tmplrowstyle != null) {
				HSSFCellStyle style = workbook.createCellStyle();
				style.cloneStyleFrom(tmplrowstyle);
				sheetRow.setRowStyle(style);
			}
			if (tmplValueRow != null) {
				sheetRow.setHeight(tmplValueRow.getHeight());
			}
			
			for (int i = 1,mi=0; i < methods.length&&mi<methods.length; i++) {
				Method method = methods[mi];
				Object value = null;
				
				if (method != null && method.getName().startsWith("get")
						&& !method.getName().equals("getClass")&& !method.getName().equals("getUuid")) {
					value = method.invoke(item);
				}
				else{
					if (tmplsheet == null) {
						System.out.println(i+":method:"+method+" not applicable");
						i--;
						mi++;
						continue;
					}
				}
				System.out.println(i+":method:"+method+":value="+value);
				mi++;
				if (value == null) {
					sheetRow.createCell(i).setCellValue("");
				} else if (value.getClass().equals(Date.class)) {
					sheetRow.createCell(i)
							.setCellValue(df.format((Date) value));
				} else {
					sheetRow.createCell(i).setCellValue(value.toString());
				}

				if (tmplsheet != null) {
					HSSFCell tmplcell = tmplValueRow.getCell(i);
					if (tmplcell != null) {
						HSSFCell ncell = sheetRow.getCell(i);
						if (tmplcell.getCellStyle() != null) {
							HSSFCellStyle style = workbook.createCellStyle();
							style.cloneStyleFrom(tmplcell.getCellStyle());
							ncell.setCellStyle(style);
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");

		System.out.println(df2.format(new Date()));
	}
}
