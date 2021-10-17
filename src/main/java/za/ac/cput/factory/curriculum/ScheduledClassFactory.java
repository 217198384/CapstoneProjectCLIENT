package za.ac.cput.factory.curriculum;

import za.ac.cput.entity.curriculum.ScheduledClass;

/**
 * Dinelle Kotze
 * 219089302
 * ScheduledClassFactory.java
 * This is the factory class for the ScheduledClass entity.
 */

public class ScheduledClassFactory
{
    public static ScheduledClass build (String scheduledClassId, int subjectCode, int roomCode, String classTime)
    {
        if (scheduledClassId.isEmpty() || subjectCode <= 0 || roomCode <= 0 || classTime.isEmpty())
        {
            return null;
        }

        return new ScheduledClass.Builder()
                .setScheduledClassId(scheduledClassId)
                .setSubjectCode(subjectCode)
                .setRoomCode(roomCode)
                .setClassTime(classTime)
                .build();
    }
}
