package com.infinity.ProductiveIO.totalDashboard.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.service.MachineDetailService;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.operator.model.OperatorItem;
import com.infinity.ProductiveIO.operator.repository.OperatorRepository;
import com.infinity.ProductiveIO.totalDashboard.dto.TotalDashboardJDBC;
import com.infinity.ProductiveIO.totalDashboard.model.TotalDashboardItem;

@Service
public class TotalDashboardService {

	@Autowired
	DailyDetailRepository dailyDetailRepository;

	@Autowired
	MachineDetailService machineDetailService;

	@Autowired
	OperatorRepository operatorRepository;

	@Autowired
	TotalDashboardJDBC jdbc;

	public List<TotalDashboardItem> getTotalDashboard() {

		List<TotalDashboardItem> totalDashboardItems = new ArrayList<>();
		List<ItemDetail> itemTotals = dailyDetailRepository
				.findTotalPerDay(new java.sql.Date(System.currentTimeMillis()));

		Map<Long, MachineDetail> machineDetailsMap = machineDetailService.getMachineDetailsMap();
		List<OperatorItem> operatorsList = operatorRepository.findAll();

		for (ItemDetail itemDetail : itemTotals) {
			//ItemDetail itemDetail;
			TotalDashboardItem totalDashboardItem = new TotalDashboardItem();
			int uploadMinute = 0;
			
			totalDashboardItem.setId(itemDetail.getId());
			totalDashboardItem.setTotalForDay(itemDetail.getCountamount());
			totalDashboardItem.setLastUpdate(itemDetail.getCounttime());

			if (machineDetailsMap.containsKey(itemDetail.getId())) {

				if (Objects.nonNull(machineDetailsMap.get(itemDetail.getId()).getName())) {
					totalDashboardItem.setMachineName(machineDetailsMap.get(itemDetail.getId()).getName());
				} else {
					totalDashboardItem.setMachineName(String.valueOf(itemDetail.getId()));
				}

				if (Objects.nonNull(machineDetailsMap.get(itemDetail.getId()).getAverageval())) {
					totalDashboardItem.setAverage(machineDetailsMap.get(itemDetail.getId()).getAverageval());
				} else {
					totalDashboardItem.setAverage(0);
				}

				if (Objects.nonNull(machineDetailsMap.get(itemDetail.getId()).getMarginval())) {
					totalDashboardItem.setMargin(machineDetailsMap.get(itemDetail.getId()).getMarginval());
				} else {
					totalDashboardItem.setMargin(0);
				}
				if (Objects.nonNull(machineDetailsMap.get(itemDetail.getId()).getUploadmin())) {
					uploadMinute = machineDetailsMap.get(itemDetail.getId()).getUploadmin();
				}				
				if (Objects.nonNull(machineDetailsMap.get(itemDetail.getId()).getNote())) {
					totalDashboardItem.setNote(machineDetailsMap.get(itemDetail.getId()).getNote());
				} else {
					totalDashboardItem.setNote("");
				}

			} else {
				totalDashboardItem.setMachineName(String.valueOf(itemDetail.getId()));
				totalDashboardItem.setAverage(0);
				totalDashboardItem.setMargin(0);
			}

			int lastUploadAmount = jdbc.lastUploadAmount(itemDetail.getId());
			totalDashboardItem.setLastUpdateAmount(lastUploadAmount);

			if (totalDashboardItem.getAverage() > 0) {
				totalDashboardItem.setProductionPersentage(
						(int) totalDashboardItem.getLastUpdateAmount() * 100 / totalDashboardItem.getAverage());
			} else {
				totalDashboardItem.setProductionPersentage(0);
			}
			
			int currentAverage = jdbc.currentAverage(itemDetail.getId());
			totalDashboardItem.setCurrentAverage(currentAverage);
			
			int amountInactiveMinutes = jdbc.amountInactive(itemDetail.getId());			
			int inactiveMinutes = amountInactiveMinutes * uploadMinute;
			
			DecimalFormat df = new DecimalFormat("0.##");
			double value = (double) inactiveMinutes / 60;
			String valueString = df.format(value);
			totalDashboardItem.setInativeHours(Double.parseDouble(valueString.replace(",",".")));									

			Optional<OperatorItem> operatorItemOptional = operatorsList.stream().filter(operator -> {
				if (Objects.isNull(operator.getMachineid()))
					return false;
				if (operator.getMachineid() == itemDetail.getId())
					return true;

				return false;

			}).findFirst();
			if (operatorItemOptional.isPresent()) {
				totalDashboardItem.setOperatorName(operatorItemOptional.get().getOperatorname());
			} else {
				totalDashboardItem.setOperatorName("Not Set");
			}

			totalDashboardItems.add(totalDashboardItem);

			totalDashboardItems
					.sort((item1, item2) -> item1.getProductionPersentage() - item2.getProductionPersentage());
		}

		return totalDashboardItems;
	}

}
