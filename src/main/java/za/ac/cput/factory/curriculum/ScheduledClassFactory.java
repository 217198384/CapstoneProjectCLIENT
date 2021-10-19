package za.ac.cput.factory.curriculum;

import za.ac.cput.entity.curriculum.ScheduledClass;
import za.ac.cput.util.GenericHelper;

/**
 * Dinelle Kotze
 * 219089302
 * ScheduledClassFactory.java
 * This is the factory class for the ScheduledClass entity.
 */

public class ScheduledClassFactory
{
    public static ScheduledClass build (int subjectCode, int roomCode, String classTime)
    {
        if (subjectCode <= 0 || roomCode <= 0 || classTime.isEmpty())
        {
            return null;
        }

        String id = GenericHelper.generateRandom();

        return new ScheduledClass.Builder()
                .setScheduledClassId(id)
                .setSubjectCode(subjectCode)
                .setRoomCode(roomCode)
                .setClassTime(classTime)
                .build();
    }
}
