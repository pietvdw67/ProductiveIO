package com.infinity.ProductiveIO.dailyHistory.service;

import java.util.ArrayList;
import java.util.List;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItemView;
import com.infinity.ProductiveIO.report.model.ReportColumns;
import com.infinity.ProductiveIO.report.service.ReportBuilder;

public class DailyHistoryReportService {
	
	private List<HistoryItemView> historyItemsView;
	
	public void buildReport() {
		ReportBuilder reportBuilder = new ReportBuilder();
		reportBuilder.setReportName("History");
		reportBuilder.setOutputFileName("History.xlsx");
		
		List<ReportColumns> reportColumns = new ArrayList<>();
		
		reportColumns.add(new ReportColumns("Machine Name",8000,"machinename",ReportColumns.CellType.STRING));
		reportColumns.add(new ReportColumns("Operator",8000,"operatorname",ReportColumns.CellType.STRING));
		reportColumns.add(new ReportColumns("Date","countdate",ReportColumns.CellType.DATE));	
		reportColumns.add(new ReportColumns("Amount","countamount"));
		reportColumns.add(new ReportColumns("Average","average"));	
		reportColumns.add(new ReportColumns("Inactive","inactive"));
		reportColumns.add(new ReportColumns("Offline","offline"));
		reportColumns.add(new ReportColumns("Note",10000,"note",ReportColumns.CellType.STRING));
		reportBuilder.setReportColumns(reportColumns);
		reportBuilder.setValues(historyItemsView);
		
		reportBuilder.buildReport();
	}

	public List<HistoryItemView> getHistoryItemsView() {
		return historyItemsView;
	}

	public void setHistoryItemsView(List<HistoryItemView> historyItemsView) {
		this.historyItemsView = historyItemsView;
	}
	
	

}
