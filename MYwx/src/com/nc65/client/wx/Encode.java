package com.nc65.client.wx;
public class Encode
{

    public Encode()
    {
    }

    public String decode(String s)
    {
        if(s == null)
            return null;
        String res = "";
        DES des = new DES(getKey());
        byte sBytes[] = s.getBytes();
        for(int i = 0; i < sBytes.length / 16; i++)
        {
            byte theBytes[] = new byte[8];
            for(int j = 0; j <= 7; j++)
            {
                byte byte1 = (byte)(sBytes[16 * i + 2 * j] - 97);
                byte byte2 = (byte)(sBytes[16 * i + 2 * j + 1] - 97);
                theBytes[j] = (byte)(byte1 * 16 + byte2);
            }

            long x = des.bytes2long(theBytes);
            byte result[] = new byte[8];
            des.long2bytes(des.decrypt(x), result);
            res = (new StringBuilder()).append(res).append(new String(result)).toString();
        }

        return res.trim();
    }

    public String encode(String s)
    {
        if(s == null)
            return null;
        String res = "";
        DES des = new DES(getKey());
        byte space = 32;
        byte sBytes[] = s.getBytes();
        int length = sBytes.length;
        int newLength = length + (8 - length % 8) % 8;
        byte newBytes[] = new byte[newLength];
        for(int i = 0; i < newLength; i++)
            if(i <= length - 1)
                newBytes[i] = sBytes[i];
            else
                newBytes[i] = space;

        for(int i = 0; i < newLength / 8; i++)
        {
            byte theBytes[] = new byte[8];
            for(int j = 0; j <= 7; j++)
                theBytes[j] = newBytes[8 * i + j];

            long x = des.bytes2long(theBytes);
            byte result[] = new byte[8];
            des.long2bytes(des.encrypt(x), result);
            byte doubleResult[] = new byte[16];
            for(int j = 0; j < 8; j++)
            {
                doubleResult[2 * j] = (byte)((((char)result[j] & 240) >> 4) + 97);
                doubleResult[2 * j + 1] = (byte)(((char)result[j] & 15) + 97);
            }

            res = (new StringBuilder()).append(res).append(new String(doubleResult)).toString();
        }

        return res;
    }

    private static long getKey()
    {
        return 1231234234L;
    }
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\hz\hznchome\external\lib\basic.jar
	Total time: 16 ms
	Jad reported messages/errors:
The class file version is 49.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/