package com.infinity.ProductiveIO.dailyHistory.service;

import java.util.ArrayList;
import java.util.List;

import com.infinity.ProductiveIO.dailyHistory.model.HistoryItem;
import com.infinity.ProductiveIO.report.model.ReportColumns;
import com.infinity.ProductiveIO.report.service.ReportBuilder;

public class DailyHistoryReportService {
	
	private List<HistoryItem> historyItems;
	
	public void buildReport() {
		ReportBuilder reportBuilder = new ReportBuilder();
		reportBuilder.setReportName("Daily History");
		reportBuilder.setOutputFileName("DailyHistory.xlsx");
		
		List<ReportColumns> reportColumns = new ArrayList<>();
		
		reportColumns.add(new ReportColumns("Machine Id","machineid"));
		reportColumns.add(new ReportColumns("Date","countdate",ReportColumns.CellType.DATE));		
		reportColumns.add(new ReportColumns("Count","countamount"));
		reportBuilder.setReportColumns(reportColumns);
		reportBuilder.setValues(historyItems);
		
		reportBuilder.buildReport();
	}

	public List<HistoryItem> getHistoryItems() {
		return historyItems;
	}

	public void setHistoryItems(List<HistoryItem> historyItems) {
		this.historyItems = historyItems;
	}
	
	

}
