package com.abhinendra;

import java.io.IOException;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.abhinendra.domain.Person;
import com.abhinendra.services.SanctionService;
import com.abhinendra.services.TransactionService;
import com.abhinendra.services.Implementation.PersonServiceImpl;

@SpringBootApplication
@EntityScan(basePackages = { "com.abhinendra.domain" })
@ComponentScan(basePackages = "com.abhinendra")
public class ServerApplication {

	@Autowired
	TransactionService transactionservice;
	
	
	@PostConstruct
    public void combine()
	{
	try {
		//sanctionObj.readSanctionList("sanctionList.txt");
		 transactionservice.Polling();
		 
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
	
	/*@Autowired
	PersonServiceImpl psl;
	
	@PostConstruct
	public void showstuff()
	{
	psl.showall();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
