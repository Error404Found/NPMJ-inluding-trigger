package com.abhinendra.services;

import com.abhinendra.domain.*;
import com.abhinendra.services.Implementation.InsufficientAmtException;

public interface PersonService{

    public Person savePerson(Person person) throws Exception;
    public Person createUser(Transaction transaction);
    public Person findOnebyId(Transaction transaction);
    public void showall();
    public void doTransaction(String fromName, String PayeeName, float amt);
    public void withdraw(String payerName, float Amt) throws InsufficientAmtException;
    public void deposit(String payeeName, float Amt);
    public void calldoTransaction();
	
}