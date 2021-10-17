package za.ac.cput.factory.curriculum;

/**
 *
 * ExaminationFactory.java
 * Factory class for Examination entity
 * @author Dylan Koevort 218088159
 * 9 June 2021
 *
 */

import za.ac.cput.entity.curriculum.Examination;
import za.ac.cput.util.GenericHelper;

import java.time.LocalDate;

public class ExaminationFactory {

    public static Examination build(int subjectCode, String examDesc, LocalDate examDate) {

        if(subjectCode <= 0 || examDesc.isEmpty() || examDate == null) {
            return null;
        }

        String id = GenericHelper.generateRandom();

        return new Examination.Builder()
                .setExamId(id)
                .setSubjectCode(subjectCode)
                .setExamDesc(examDesc)
                .setExamDate(examDate)
                .build();
    }
}
