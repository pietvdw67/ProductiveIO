package com.infinity.ProductiveIO.totalDashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.machineDetail.model.MachineDetail;
import com.infinity.ProductiveIO.machineDetail.service.MachineDetailService;
import com.infinity.ProductiveIO.model.ItemDetail;
import com.infinity.ProductiveIO.totalDashboard.dto.TotalDashboardJDBC;
import com.infinity.ProductiveIO.totalDashboard.model.TotalDashboardItem;

@Service
public class TotalDashboardService {
	
	@Autowired
	DailyDetailRepository dailyDetailRepository;
	
	@Autowired
	MachineDetailService machineDetailService;
	
	@Autowired
	TotalDashboardJDBC jdbc;
	
	public List<TotalDashboardItem> getTotalDashboard(){
		
		List<TotalDashboardItem> totalDashboardItems = new ArrayList<>();
		List<ItemDetail> itemTotals = dailyDetailRepository.findTotalPerDay(new java.sql.Date(System.currentTimeMillis()));
		
		Map<Long,MachineDetail> machineDetailsMap = machineDetailService.getMachineDetailsMap();
		
		for (ItemDetail itemDetail : itemTotals) {
			TotalDashboardItem totalDashboardItem = new TotalDashboardItem();
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

			} else {
				totalDashboardItem.setMachineName(String.valueOf(itemDetail.getId()));
				totalDashboardItem.setAverage(0);
				totalDashboardItem.setMargin(0);
			}
			
			int lastUploadAmount = jdbc.lastUploadAmount(itemDetail.getId());
			totalDashboardItem.setLastUpdateAmount(lastUploadAmount);
			
			totalDashboardItem.setProductionPersentage((int) totalDashboardItem.getLastUpdateAmount() * 100 / totalDashboardItem.getAverage());			
			
			totalDashboardItems.add(totalDashboardItem);
			
			totalDashboardItems.sort( (item1,item2) -> item1.getProductionPersentage() - item2.getProductionPersentage() );
		}
		
		
		return totalDashboardItems;
	}

}
