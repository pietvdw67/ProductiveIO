package com.infinity.ProductiveIO.dailyTotal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.infinity.ProductiveIO.dailyDetail.service.DetailService;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.model.ItemDetailView;
import com.infinity.ProductiveIO.report.model.ReportColumns;
import com.infinity.ProductiveIO.report.service.ReportBuilder;

public class DailyTotalReportService {

	@Autowired
	DetailService detailService;

	private List<ItemDetail> itemDetails;

	public void buildReport() {
		ReportBuilder reportBuilder = new ReportBuilder();
		reportBuilder.setReportName("Item Count");
		reportBuilder.setOutputFileName("DailyTotal.xlsx");

		List<ReportColumns> reportColumns = new ArrayList<>();

		reportColumns.add(new ReportColumns("Date", "countdate", ReportColumns.CellType.DATE));
		reportColumns.add(new ReportColumns("Machine Id", "machineid"));
		reportColumns.add(new ReportColumns("Operator", "operatorName"));
		reportColumns.add(new ReportColumns("Time Updated", 5000, "counttime", ReportColumns.CellType.TIME));
		reportColumns.add(new ReportColumns("Count", "countamount"));
		reportBuilder.setReportColumns(reportColumns);
		
		List<ItemDetailView> itemDetailViews = detailService.ItemDetailToItemDetailView(itemDetails);

		reportBuilder.setValues(itemDetailViews);

		reportBuilder.buildReport();
	}

	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

}
