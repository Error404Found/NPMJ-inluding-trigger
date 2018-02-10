package com.abhinendra.domain;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.*;


import org.springframework.beans.factory.annotation.Autowired;

import com.abhinendra.services.SanctionService;
import com.abhinendra.services.Implementation.SanctionServiceImpl;

@Entity
@Data
@Table(name="Transaction")
@EntityListeners(TransactionListner.class)
public class Transaction {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated transaction ID")
    private String RefId;

    @Column(name="PayerName")
    @ApiModelProperty(notes = "The name of  transaction", required = true)
    private String payerName;
    private String payeeName;
    private String payerAccount;
    private String payeeAccount;
    private float amount;
    private Date date;
    private int status;

    public  Transaction(){
    }
    
    public Transaction(String RefId,String payerName,String payeeName,Date date,float amount,String payerAccount,String payeeAccount,int status){
        this.RefId = RefId;
        this.payerName = payerName;
        this.payerAccount = payerAccount;
        this.payeeName = payeeName;
        this.payeeAccount = payeeAccount;
        this.date = date;
        this.amount = amount;
        this.status = status;
    }
    
    public String getId() {
		return RefId;
	}
	public void setId(String RefId) {
		this.RefId = RefId;
	}
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayerAccount() {
		return payerAccount;
	}
	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}
	public String getPayeeAccount() {
		return payeeAccount;
	}
	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	/*@SuppressWarnings("unchecked")
	public void call(Transaction t)
	{
		SanctionServiceImpl sanctionObj=new SanctionServiceImpl();
		ArrayList<String> arr=new ArrayList<String>();
		arr=sanctionObj.readSanctionList("sanctionList.txt");
		
		//System.out.println(arr);
		Iterator it=arr.iterator();
		while(it.hasNext())
		{
			if(t.payeeName.equals(it.next()))
			{
				System.out.println("BLACK LISTEDDDDDDDDDDDDDDDDDD");
				break;
			}
		}
		
	}*/
}



