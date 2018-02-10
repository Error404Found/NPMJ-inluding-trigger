package com.abhinendra.services.Implementation;

import com.abhinendra.domain.Transaction;

import com.abhinendra.domain.Person;
import com.abhinendra.domain.QPerson;
import com.abhinendra.domain.Status;
import com.abhinendra.repositories.TransactionRepository;
import com.abhinendra.services.PersonService;
import com.abhinendra.services.TransactionService;
import com.querydsl.core.types.Predicate;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {
    Predicate predicate;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    PersonService personService;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) throws Exception {
        return transactionRepository.save(transaction);
    }
    @Override
	public void readFile(String filename)
    {
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = new String();
            Transaction transaction = new Transaction();
            boolean value=false;
            while((line=br.readLine()) != null)
            {
                System.out.println(line);
                transaction = setParameters(line);
                try {
					value = fieldValidate(transaction);
					System.out.println("hey value:************"+value);
					if(value == true)
					{
						//System.out.println("*************************HIHIHIHI");
						transaction.setStatus(1);
		            }
					else
						transaction.setStatus(0);
					
					//saveTransaction(transaction);
					transactionRepository.save(transaction);
					System.out.println("CALLLING SANCTION LIST VALIDATION");
					
					/*String Name2=transaction.getPayeeName();
	            	String Name1=transaction.getPayerName();
	            	float balan=transaction.getAmount();
	            	
	            	personService.doTransaction(Name1,Name2,balan);*/
					
				} catch (Exception e) {
					System.out.println("In catch");
					e.printStackTrace();
				}
            }
            
            System.out.println("File is read");
            File f=new File(filename);
			System.out.println("File is :***"+filename);
			if(f.exists())
			{
				System.out.println("Hey file is present re**********");
			}
         
			boolean val=false;
            val=f.renameTo(new File("/home/juiedarwade/CITI/ARCHIVE/" + f.getName()));
            if(val==true)
            {
            	System.out.println("File location has been changed succesfully!");
            }
            else
            {
            	System.out.println("**************ERRRRRRRRORRRRRRRRR**********");
            }
     	   
            
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
     }
	public boolean fieldValidate(Transaction transaction) throws ParseException
	{
        Calendar cal = Calendar.getInstance();
        Date date1;
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        date1 = (Date) formatter.parse(formatter.format(cal.getTime()));
		boolean value = true;
		
		Person person = personService.findOnebyId(transaction);
		
		personService.showall();
		//System.out.println("BALANCE:>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+person.toString());
		if(!isAlphaNumeric(transaction.getId()) || !isAlphaNumeric(transaction.getPayerName()) || !isAlphaNumeric(transaction.getPayeeName()) || !isAlphaNumeric(transaction.getPayerAccount()) || !isAlphaNumeric(transaction.getPayeeAccount()))
			value = false;
		if((transaction.getAmount() < 0 )|| transaction.getDate().compareTo(date1) != 0)
			value = false;
		return value;
	}
    public boolean isAlphaNumeric(String s)
    {
        String pattern= "^[a-zA-Z0-9]*$";
        return s.matches(pattern);
    }
    
    public Transaction setParameters(String line)
    {
        char CharArr[]=new char[127];
        CharArr=line.toCharArray();
        char TransChar[]=new char[12];
        char PayerName[]=new char[35];
        char PayeeName[]=new char[35];
        char PayerAccount[] = new char[12];
        char PayeeAccount[] = new char[12];
        String payeeName = null,payerName = null,str = "",RefId = null;
        float amount = 0;
        char Amount[] = new char[12];
        Date date = null;
        int lineIndex = 0 , size = 0;

    	for(int counter = 0 ; counter < 12 ; counter++,lineIndex++)											// Extracting the reference ID of transaction
    		TransChar[counter] = CharArr[lineIndex];
    	RefId = new String(TransChar);
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	for(int counter = 0 ; counter < 8 ; counter++,lineIndex++)
    	{
            str = str + CharArr[lineIndex];
            if( counter == 1 || counter == 3 )
            	str = str + '/';
    	}
        try {
            date = (Date) formatter.parse(str);										// Extracting the date of transaction
         }catch (ParseException e){
         }
        System.out.println("DATE IDHAR: "+date);
        for(int counter = 0 ; CharArr[lineIndex] != ' ' ; counter++,lineIndex++,size++)
            PayerName[counter]=CharArr[lineIndex];
        PayerName = Arrays.copyOfRange(PayerName, 0 , size);
        size = 0;
        lineIndex = 55;
        payerName = new String(PayerName);											// Extracting the Payer name of transaction
        
        for(int counter = 0 ; counter < 12 ; counter++,lineIndex++)
        	PayerAccount[counter] = CharArr[lineIndex];
        String payerAccount = new String(PayerAccount);								// Extracting the Payer Account of transaction
        
        for(int counter = 0 ; CharArr[lineIndex] != ' ' ; counter++,lineIndex++,size++)
            PayeeName[counter]=CharArr[lineIndex];
        PayeeName = Arrays.copyOfRange(PayeeName, 0 , size);
        lineIndex = 102;
        payeeName = new String(PayeeName);											// Extracting the Payee name of transaction
        
        for(int counter = 0 ; counter < 12 ; counter++,lineIndex++)
        	PayeeAccount[counter] = CharArr[lineIndex];
        String payeeAccount = new String(PayeeAccount);								// Extracting the Payee Account of transaction
        
        while(CharArr[lineIndex] == ' ' && lineIndex >= 114)
            lineIndex++;
        int index=lineIndex;           
        for(int counter = 0 ; counter < 127-index ; counter++,lineIndex++)
            Amount[counter] = CharArr[lineIndex];     
        String amt = new String(Amount);    
        amount = Float.parseFloat(amt);												// Extracting the Amount of transaction
        
        Transaction transaction = new Transaction(RefId,payerName,payeeName,date,amount,payerAccount,payeeAccount,1);
        personService.createUser(transaction);
        return transaction;
    }
    @Override
    public void Polling() throws IOException,InterruptedException 
    {

         String current = new File( "." ).getCanonicalPath();
         System.out.println("Current dir:"+current);
		 Path faxFolder = Paths.get(current);
		 WatchService watchService = FileSystems.getDefault().newWatchService();
		 faxFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		 String fileName=new String();
		 boolean valid = true;
		 do{
			WatchKey watchKey = watchService.take();
		
			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) 
				{
					fileName = event.context().toString();
					System.out.println("File Created: " + fileName);
					readFile(fileName);
					
				}
			}
			valid = watchKey.reset();
		} while (valid);	 
	}
    
    @Override
    public Object findAllTransaction()
    {
    	//return personRepository.findAll();
    	return transactionRepository.findAll();
    }

    	
    

}
