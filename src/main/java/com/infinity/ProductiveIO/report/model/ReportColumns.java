package com.infinity.ProductiveIO.report.model;

public class ReportColumns {
	
	private static final int DEFAULT_HEADING_WIDHT = 4000;
	
	public enum CellType {
		STRING,
		DATE,
		TIME
	}

	private String headingTitle;
	private int headingWidth;
	private String valueObjectName;
	private CellType cellType;
	
	public ReportColumns() {}
	
	public ReportColumns(String headingTitle,int headingWidth,String valueObjectName,CellType cellType) {
		this.headingTitle = headingTitle;
		this.headingWidth = headingWidth;
		this.valueObjectName = valueObjectName;
		this.cellType = cellType;
	}
	
	public ReportColumns(String headingTitle,String valueObjectName,CellType cellType) {
		this(headingTitle,DEFAULT_HEADING_WIDHT,valueObjectName,cellType);
	}
	
	public ReportColumns(String headingTitle,String valueObjectName) {
		this(headingTitle,DEFAULT_HEADING_WIDHT,valueObjectName,CellType.STRING);
	}
	
	public String getHeadingTitle() {
		return headingTitle;
	}
	public void setHeadingTitle(String headingTitle) {
		this.headingTitle = headingTitle;
	}
	public int getHeadingWidth() {
		return headingWidth;
	}
	public void setHeadingWidth(int headingWidth) {
		this.headingWidth = headingWidth;
	}

	public String getValueObjectName() {
		return valueObjectName;
	}

	public void setValueObjectName(String valueObjectName) {
		this.valueObjectName = valueObjectName;
	}

	public CellType getCellType() {
		return cellType;
	}

	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

}
