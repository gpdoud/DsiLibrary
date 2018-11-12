/**
 * Copyright (c) 2006 by Doud Systems, Inc.
 * All Rights Reserved
 */
package com.doudsystems.dsutility;


public class Main
{

	void Run()
	{
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxya0123456789+/";
		testEncodeDecodeB64(str);
		str = "Now is the time for all good men to come to the aid of their contry..";
		testEncodeDecodeB64(str);
	}

	void testEncodeDecodeB64(String str)
	{
		System.out.format("\nTesting string [%s]\n", str);
		String baseString = Base64.encodeBytes(str.getBytes());
		String testString = DsBase64.toBase64(str);
		System.out.println("Encoded as [" + testString + "]");
		if(baseString.equals(testString))
			System.out.println("Encoded the same...");
		else
			System.out.println("Encoded different: base is [" + baseString + "], test is [" + testString + "]");
		
		String test2String = DsBase64.fromBase64(testString);
		System.out.println("Decoded as [" + test2String + "]");
		if(str.equals(test2String))
			System.out.println("Decoded the same...");
		else
			System.out.println("Decoded different: base is [" + str + "], test is [" + test2String + "]");
	}
	
	public static void main(String[] args)
	{
		Main pgm = new Main();
		pgm.Run();
	}

}
