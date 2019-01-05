package com.yd.javaWeb.IDGenerator;

/**
 * @author Yd on  2018-06-26
 * @description
 **/
public class UUID {
    public static java.util.UUID uuid = java.util.UUID.randomUUID();
    protected static String id = uuid.toString().replaceAll("-","");

    public static void main(String[] args) {
        System.out.println(id);
        System.out.println(uuid.toString());
    }

    public static long GuidToInt64()
    {
//        byte[] bytes = Guid.NewGuid().ToByteArray();
//        return BitConverter.ToInt64(bytes, 0);
        return 0;
    }

//    private Guid GenerateComb()
//    {
//        byte[] guidArray = Guid.NewGuid().ToByteArray();
//
//        DateTime baseDate = new DateTime(1900, 1, 1);
//        DateTime now = DateTime.Now;
//
//        // Get the days and milliseconds which will be used to build
//        //the byte string
//        TimeSpan days = new TimeSpan(now.Ticks - baseDate.Ticks);
//        TimeSpan msecs = now.TimeOfDay;
//
//        // Convert to a byte array
//        // Note that SQL Server is accurate to 1/300th of a
//        // millisecond so we divide by 3.333333
//        byte[] daysArray = BitConverter.GetBytes(days.Days);
//        byte[] msecsArray = BitConverter.GetBytes((long)
//                (msecs.TotalMilliseconds / 3.333333));
//
//        // Reverse the bytes to match SQL Servers ordering
//        Array.Reverse(daysArray);
//        Array.Reverse(msecsArray);
//
//        // Copy the bytes into the guid
//        Array.Copy(daysArray, daysArray.Length - 2, guidArray,
//                guidArray.Length - 6, 2);
//        Array.Copy(msecsArray, msecsArray.Length - 4, guidArray,
//                guidArray.Length - 4, 4);
//
//        return new Guid(guidArray);
//    }
}
