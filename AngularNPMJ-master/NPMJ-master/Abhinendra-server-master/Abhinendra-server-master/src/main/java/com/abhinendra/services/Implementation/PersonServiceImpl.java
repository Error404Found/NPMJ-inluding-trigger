package com.abhinendra.services.Implementation;

import com.abhinendra.domain.Person;
import com.abhinendra.domain.Transaction;
import com.abhinendra.repositories.PersonRepository;
import com.abhinendra.services.PersonService;
import com.querydsl.core.types.Predicate;

import java.math.BigDecimal;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

@Service("personService")
public class PersonServiceImpl implements PersonService {
	boolean FLAGFORCOMMIT=true;
    Predicate predicate;
    @Autowired
    PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(Person person) throws Exception {
        return personRepository.save(person);
    }

	@Override
	public Person createUser(Transaction transaction) {
        Random rand = new Random();
        int range = rand.nextInt(1000);
        float balance = rand.nextFloat()*range;
       
        Person person = null;
        try {
            person = new Person(transaction.getPayerAccount(),transaction.getPayerName(),balance);
			savePerson(person);
	        balance = rand.nextFloat()*range;
			person = new Person(transaction.getPayeeAccount(),transaction.getPayeeName(),balance);
			savePerson(person);
		} catch (Exception e) 
        {
			//e.printStackTrace();
		}
		return person;
	}

	@Override
	public Person findOnebyId(Transaction transaction) {
		Person person = personRepository.findOne(transaction.getPayerAccount());
		return person;
	}
	
	 public void showall()
	{
		for(Person p: personRepository.findAll())
		{
			System.out.println(p.getAccount()+" "+p.getName()+" "+p.getBalance());
		}
	}
	
	
	public void doTransaction(String fromName, String PayeeName, float amt) 
	{
		try 
		{
			withdraw(fromName,amt);
			if(FLAGFORCOMMIT==true)
			{
				System.out.println("**************TRANSACTION COMMITED***********");
			deposit(PayeeName,amt);
			}
		} 
		catch (InsufficientAmtException e) 
		{
			System.out.println("in catch");
			//e.printStackTrace();
		}
		
		showall();
	}
	
	public void calldoTransaction()
	{
			doTransaction("VickeyMickey","SaumeyaKatyal",50000);
		
		
	}
	
	@Transactional(rollbackOn=InsufficientAmtException.class) 
	public void withdraw(String payerName, float Amt) throws InsufficientAmtException
	{
	
		System.out.println("**************inside withdraw with name:"+payerName);
		Person per=personRepository.findByname(payerName);
		float bal=per.getBalance();
		bal=bal-Amt;
		if(bal<0) 
		{
			FLAGFORCOMMIT=false;
			throw new InsufficientAmtException("exception occured");
		}
		else
		{
			FLAGFORCOMMIT=true;
		}
		per.setBalance(bal);
		personRepository.save(per);
	}
	

	public void deposit(String payeeName, float Amt) 
	{
		System.out.println("******************inside deposit with name:"+payeeName);
		Person per=personRepository.findByname(payeeName);
		float bal=per.getBalance();
		bal=bal+Amt;
		per.setBalance(bal);
		personRepository.save(per);
	}

}
