/**
 * Copyright (c) 2006 by Doud Systems, Inc.
 * All Rights Reserved
 */
package com.doudsystems.dsutility;

public class DsBase64
{
	static byte[] tb64table =
	{
		'A','B','C','D','E','F','G','H','I','J','K','L','M',	//  0-12
		'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',	// 13-25
		'a','b','c','d','e','f','g','h','i','j','k','l','m',	// 26-38
		'n','o','p','q','r','s','t','u','v','w','x','y','z',	// 39-51
		'0','1','2','3','4','5','6','7','8','9','+','/'			// 52-63
	};
	
	static int[] fb64table =
	{
		-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,		//  0-15
		-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,		// 16-31
		-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,58,-1,-1,-1,59,		// 32-47
		52,53,54,55,56,57,58,59,60,61,-1,-1,-1,32,-1,-1,		// 48-63
		-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,		// 64-79
		15,16,17,18,19,20,21,22,23,24,25,-1,-1,-1,-1,-1,		// 80-95
		-1,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,		// 96-111
		41,42,43,44,45,46,47,48,49,50,51,-1,-1,-1,-1,-1 		// 112-127
	};
	
	public static String fromBase64(String string)
	{
		return fromBase64(string.getBytes());
	}
	
	public static String fromBase64(byte[] bytes)
	{
		byte[] goodBytes = stripNonB64(bytes);
		int i = 0;
		StringBuilder sb = new StringBuilder();
		for(int idx = 0; idx < goodBytes.length; idx+=4)
		{
			i = fb64table[(char) goodBytes[idx]];
			i = (i << 6) + fb64table[(char) goodBytes[idx+1]];
			i = (i << 6) + fb64table[(char) goodBytes[idx+2]];
			i = (i << 6) + fb64table[(char) goodBytes[idx+3]];
			int c1 = (i & 0x00FF0000) >>> 16;
			int c2 = (i & 0x0000FF00) >>>  8;
			int c3 = (i & 0x000000FF);
			sb.append((char) c1);
			sb.append((char) c2);
			sb.append((char) c3);
		}
		return sb.toString().trim();
	}
	
	private static byte[] stripNonB64(byte[] bytes)
	{
		StringBuilder sb = new StringBuilder(bytes.length);
		for(byte b : bytes)
		{
			if(fb64table[(char) b] != -1)
				sb.append((char) b);
		}
		return sb.toString().getBytes();
	}
	
	public static String toBase64(String string)
	{
		return toBase64(string.getBytes());
	}
		
	public static String toBase64(byte[] bytes)
	{
		int idx = 0;
		int paddingRequired = padEncodedString(bytes.length);
		byte[] buffer = new byte[bytes.length + paddingRequired];
		for(idx = 0; idx < bytes.length; idx++)
			buffer[idx] = bytes[idx];
		
		StringBuilder sb = new StringBuilder();
		
		for(idx = 0; idx < buffer.length; idx += 3)
		{
			int i = (int) buffer[idx];
			i = (i << 8) + (int) buffer[idx+1];
			i = (i << 8) + (int) buffer[idx+2];
/*			
			int b1 = (i & 0x00FC0000) >>> 18;
			int b2 = (i & 0x0003F000) >>> 12;
			int b3 = (i & 0x00000FC0) >>> 6;
			int b4 = (i & 0x0000003F);
			char c1 = (char) b64table[b1];
			char c2 = (char) b64table[b2];
			char c3 = (char) b64table[b3];
			char c4 = (char) b64table[b4];
*/			
			sb.append((char) tb64table[(i & 0x00FC0000) >>> 18]);
			sb.append((char) tb64table[(i & 0x0003F000) >>> 12]);
			sb.append((char) tb64table[(i & 0x00000FC0) >>> 6]);
			sb.append((char) tb64table[(i & 0x0000003F)]);
		}
		
		switch(padEncodedString(bytes.length))
		{
			case 0:
				break;
			case 1:
				sb.replace(sb.length() - 1, sb.length(), "=");
				break;
			case 2:
				sb.replace(sb.length() - 2, sb.length(), "==");
				break;
		}
		return limitLinesTo76Chars(sb);
	}
	
	private static String limitLinesTo76Chars(StringBuilder sb)
	{
		StringBuilder tempsb = new StringBuilder(sb.toString());
		for(int idx = 76; idx < sb.length(); idx+= 76)
			tempsb.insert(idx, '\n');
		return tempsb.toString();
	}
	
	private static int padEncodedString(int nbrOfBytes)
	{
		switch(nbrOfBytes % 3)
		{
		case 1:
			return 2;
		case 2:
			return 1;
		}
		return 0;
	}

	private static void debug(long i)
	{ debug(i, false); }
	
	private static void debug(long i, boolean group6)
	{
/*
		System.out.print(((i & 2147483648L) > 0) ? 1 : 0);
		System.out.print(((i & 1073741824) > 0) ? 1 : 0);
		System.out.print(((i & 536870912) > 0) ? 1 : 0);
		System.out.print(((i & 268435456) > 0) ? 1 : 0);
		System.out.print(((i & 134217728) > 0) ? 1 : 0);
		System.out.print(((i & 67108864) > 0) ? 1 : 0);
		System.out.print(((i & 33554432) > 0) ? 1 : 0);
		System.out.print(((i & 16777216) > 0) ? 1 : 0);
		if(!group6) System.out.print(' ');
*/
		System.out.print(((i & 8388608) > 0) ? 1 : 0);
		System.out.print(((i & 4194304) > 0) ? 1 : 0);
		System.out.print(((i & 2097152) > 0) ? 1 : 0);
		System.out.print(((i & 1048576) > 0) ? 1 : 0);
		System.out.print(((i & 524288) > 0) ? 1 : 0);
		System.out.print(((i & 262144) > 0) ? 1 : 0);
		if(group6) System.out.print(' ');
		System.out.print(((i & 131072) > 0) ? 1 : 0);
		System.out.print(((i & 65536) > 0) ? 1 : 0);
		if(!group6) System.out.print(' ');
		System.out.print(((i & 32768) > 0) ? 1 : 0);
		System.out.print(((i & 16384) > 0) ? 1 : 0);
		System.out.print(((i & 8192) > 0) ? 1 : 0);
		System.out.print(((i & 4096) > 0) ? 1 : 0);
		if(group6) System.out.print(' ');
		System.out.print(((i & 2048) > 0) ? 1 : 0);
		System.out.print(((i & 1024) > 0) ? 1 : 0);
		System.out.print(((i & 512) > 0) ? 1 : 0);
		System.out.print(((i & 256) > 0) ? 1 : 0);
		if(!group6) System.out.print(' ');
		System.out.print(((i & 128) > 0) ? 1 : 0);
		System.out.print(((i & 64) > 0) ? 1 : 0);
		if(group6) System.out.print(' ');
		System.out.print(((i & 32) > 0) ? 1 : 0);
		System.out.print(((i & 16) > 0) ? 1 : 0);
		System.out.print(((i & 8) > 0) ? 1 : 0);
		System.out.print(((i & 4) > 0) ? 1 : 0);
		System.out.print(((i & 2) > 0) ? 1 : 0);
		System.out.print(((i & 1) > 0) ? 1 : 0);
		System.out.print(" [" + i + "] ");
		System.out.println();
	}
	
}
