package com.abhinendra.domain;

import java.util.ArrayList;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import com.abhinendra.services.SanctionService;
import org.springframework.beans.factory.annotation.Autowired;
import com.abhinendra.repositories.SanctionRepository;
import com.abhinendra.repositories.TransactionRepository;

public class TransactionListner
{
	@Autowired
	SanctionRepository sanctionRepository;
	
	@Autowired
	TransactionRepository tran;
	


	@SuppressWarnings("unchecked")
	@PostPersist// this method will automatically get called when a save operation is performed on the transaction object and hence act as a trigger
    public void transactionPostUpdate(Transaction trans)
    {
		/*String payerName = trans.getPayerName();
		
	//	String payeeName = transaction.getPayeeName();
		
		ArrayList arr=new ArrayList();
		arr.add(1);
		arr.add(2);
		
		//tran.count();
		System.out.println("Count of transaction: "+arr.size());
	
		
		System.out.println(payerName);
		trans.call(trans);*/
			
		
		System.out.println("in post persist");
    }
}
