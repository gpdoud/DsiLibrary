package com.Cincom.Guru;

public class KeyValidator
{
	public String getCompanyName()
	{
		KeyValidatorTest.msg("Enter the company name:");
		String compName = System.console().readLine();
		int compNameHash = compName.hashCode();
		java.util.UUID guid = java.util.UUID.randomUUID();
		return guid.toString() + ":" + String.valueOf(compNameHash);
	}
}
