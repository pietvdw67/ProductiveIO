package com.infinity.ProductiveIO.dailyDetail.service;

import java.util.ArrayList;
import java.util.List;

import com.infinity.ProductiveIO.model.ItemDetailView;
import com.infinity.ProductiveIO.report.model.ReportColumns;
import com.infinity.ProductiveIO.report.service.ReportBuilder;

public class DailyDetailReportService {
	private List<ItemDetailView> itemDetailViews;

	public void buildReport() {
		ReportBuilder reportBuilder = new ReportBuilder();
		reportBuilder.setReportName("Detail");
		reportBuilder.setOutputFileName("Detail.xlsx");
		
		List<ReportColumns> reportColumns = new ArrayList<>();
		
		reportColumns.add(new ReportColumns("Machine Name",8000,"machinename",ReportColumns.CellType.STRING));
		reportColumns.add(new ReportColumns("Date","countdate",ReportColumns.CellType.DATE));
		reportColumns.add(new ReportColumns("Time","counttime",ReportColumns.CellType.TIME));		
		reportColumns.add(new ReportColumns("Count","countamount"));
		reportBuilder.setReportColumns(reportColumns);
		reportBuilder.setValues(itemDetailViews);
		
		reportBuilder.buildReport();
	}

	public List<ItemDetailView> getItemDetailViews() {
		return itemDetailViews;
	}

	public void setItemDetails(List<ItemDetailView> itemDetailViews) {
		this.itemDetailViews = itemDetailViews;
	}

}
