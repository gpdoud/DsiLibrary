package com.Cincom.Guru;

public class KeyValidatorTest
{

	void run()
	{
		KeyValidator kv = new KeyValidator();
		KeyValidatorTest.msg("Start of KeyValidator ...");
		KeyValidatorTest.msg("String is " + kv.getCompanyName());
		KeyValidatorTest.msg("End of KeyValidator ...");
	}
	
	public  static void  msg(Object text)
	{
		System.out.println(text.toString());
	}
	
	public static void main(String[] args)
	{
		KeyValidatorTest pgm = new KeyValidatorTest();
		pgm.run();
	}

}
