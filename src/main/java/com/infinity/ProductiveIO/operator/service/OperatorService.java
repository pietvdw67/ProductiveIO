package com.infinity.ProductiveIO.operator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infinity.ProductiveIO.operator.repository.OperatorRepository;

@Service
public class OperatorService {
	
	@Autowired
	OperatorRepository operatorRepository;

}
