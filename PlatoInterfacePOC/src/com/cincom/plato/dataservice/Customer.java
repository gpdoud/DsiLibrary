package com.cincom.plato.dataservice;

public class Customer
{
	int nbr;
	public int getNbr() { return nbr; }
	public void setNbr(int nbr) { this.nbr = nbr; }
	
	String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	int credit;
	public int getCredit() { return credit; }
	public void setCredit(int credit) { this.credit = credit; }
	
	public Customer() {}
	public Customer(int nbr, String name, int credit)
	{
		this.setNbr(nbr);
		this.setName(name);
		this.setCredit(credit);
	}
}
