package com.infinity.ProductiveIO.dataGenerator.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.infinity.ProductiveIO.dailyDetail.repository.DailyDetailRepository;
import com.infinity.ProductiveIO.dataGenerator.service.MachineEmulateRunnable;

@Component
public class MachineEmulateCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	DailyDetailRepository repository;
	
	private static final boolean DO_RUN = true;
	
	@Override
	public void run(String... args) throws Exception {
		if (!DO_RUN) {
			return;
		}
		
		MachineEmulateRunnable runnable = new MachineEmulateRunnable();
		runnable.setRepository(repository);
		
		new Thread(runnable).start();		
	}

}
