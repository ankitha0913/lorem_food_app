package com.robosoftin.lorem_food_app.utility;

import java.math.BigInteger;
import java.util.UUID;

public class UniqueIdGenerator {

    public static long generateUniqueId()
    {
        // The UUID.randomUUID().toString() of length
        // consist of digits ,alphabets which will be handled
        // to get digits using BigInteger and "-" which needs
        // to be replaced with "". Inside  new
        // BigInteger("%010d", new
        // BigInteger(UUID.randomUUID().toString().replace("-",
        // ""), 16)) 16 represent radix .
        String generateUUIDNo = String.format("%010d",new BigInteger(UUID.randomUUID().toString().replace("-",""),16));

        // To decide length of unique positive long number
        // generateUUIDNo.length() - uniqueNoSize is being
        // used
        String unique_no = generateUUIDNo.substring( generateUUIDNo.length() - 10);
        return Long.parseLong(unique_no);
    }
}
