package za.ac.cput.util;

/**
 *
 * GenericHelper.java
 * Helper class to generate unique identifier
 * @author Dylan Koevort 218088159
 * 19 July 2021
 *
 */

import java.util.UUID;

public class GenericHelper {
    public static String generateRandom() {
        return UUID.randomUUID().toString();
    }
}
