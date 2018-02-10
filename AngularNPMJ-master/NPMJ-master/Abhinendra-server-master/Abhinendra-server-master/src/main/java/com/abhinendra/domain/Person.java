package com.abhinendra.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Data
@Table(name="Person")
public class Person {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated person ID")
    private String account;

    @Column(name="Name")
    @ApiModelProperty(notes = "The name of  person", required = true)
    private String name;
    private float balance;

    public  Person(){
    }
    public Person(String account,String name,float balance){
        this.account = account;
        this.name = name;
        this.balance = balance;
    }
    
    public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	@Override
	public String toString()
	{
		return " Account Number: "+account+" Name: "+name+" Balance: "+balance;
	}
	
	
}



