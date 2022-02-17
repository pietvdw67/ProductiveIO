package com.infinity.ProductiveIO.report.service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.infinity.ProductiveIO.report.model.ReportColumns;
import com.infinity.ProductiveIO.report.model.ReportColumns.CellType;

public class ReportBuilder {
	
	private List<ReportColumns> reportColumns;	
	private String reportName;
	private List<String> valueMappings;
	private List<?> values;
	private String outputFileName;
	
	private XSSFWorkbook workbook;
	private XSSFCreationHelper createHelper;
	
	private CellStyle headerStyle() {
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = ((XSSFWorkbook) workbook).createFont();
		font.setFontHeightInPoints((short) 16);
		font.setBold(true);
		style.setFont(font);
		
		return style;
	}
	
	private CellStyle dateStyle() {
		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy")); 
		return style;
	}
	
	private CellStyle timeStyle() {
		CellStyle style = workbook.createCellStyle();
		style.setDataFormat(createHelper.createDataFormat().getFormat("HH:mm")); 
		return style;
	}
	
	private CellStyle stringStyle() {
		CellStyle style = workbook.createCellStyle();	
		style.setWrapText(true);
		return style;
	}
	
	public void buildReport() {
		
		workbook = new XSSFWorkbook();
		createHelper = workbook.getCreationHelper();

		Sheet sheet = workbook.createSheet(reportName);
		
		Row header = sheet.createRow(0);	
		
		for (int index = 0; index < reportColumns.size();index++) {
			Cell headerCell = header.createCell(index);
			headerCell.setCellValue(reportColumns.get(index).getHeadingTitle());
			headerCell.setCellStyle(headerStyle());
			sheet.setColumnWidth(index, reportColumns.get(index).getHeadingWidth());
		}	
		
		int rowCount = 2;
		for (Object value : values) {
			Object cellValue = "";
			CellStyle style = workbook.createCellStyle();			
			style.setWrapText(true);
			Row row = sheet.createRow(rowCount);
			
			for (int index = 0; index < reportColumns.size();index++) {
				String objectValueName = reportColumns.get(index).getValueObjectName();
				Class<?> clazz = value.getClass();
				
				try {
					Field field = clazz.getDeclaredField(objectValueName);
					field.setAccessible(true);
					cellValue = field.get(value);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
								
				Cell cell = row.createCell(index);
				if (String.valueOf(cellValue).equalsIgnoreCase("null")) {
					cell.setCellValue("");
				} else {
					cell.setCellValue(String.valueOf(cellValue));	
				}
				
				
				if (reportColumns.get(index).getCellType() == CellType.STRING) {
					cell.setCellStyle(stringStyle());					
				} else if (reportColumns.get(index).getCellType() == CellType.DATE) {
					cell.setCellStyle(dateStyle());	
				} else if (reportColumns.get(index).getCellType() == CellType.TIME) {
					cell.setCellStyle(timeStyle());	
				}
			}
			
			rowCount++;
			
		}
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "reports/" + outputFileName;

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		
	}

	public List<ReportColumns> getReportColumns() {
		return reportColumns;
	}

	public void setReportColumns(List<ReportColumns> reportColumns) {
		this.reportColumns = reportColumns;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public List<?> getValues() {
		return values;
	}

	public void setValues(List<?> values) {
		this.values = values;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	
	
}
