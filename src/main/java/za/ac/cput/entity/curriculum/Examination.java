/*
Dinelle Kotze
219089302
Examination.java
This is the class for the Examination entity.
 */

package za.ac.cput.entity.curriculum;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Examination
{
    @Id
    private int subjectCode;
    private String examDesc, examId;
    private LocalDate examDate;

    protected Examination() {}

    private Examination(Builder builder)
    {
        this.examId = builder.examId;
        this.subjectCode = builder.subjectCode;
        this.examDesc = builder.examDesc;
        this.examDate = builder.examDate;
    }

    public String getExamId() {
        return examId;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public String getExamDesc() {
        return examDesc;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    @Override
    public String toString() {
        return "Examination{" +
                "examID=" + examId + '\'' +
                ", subjectCode=" + subjectCode + '\'' +
                ", examDesc='" + examDesc + '\'' +
                ", examDate=" + examDate + '\'' +
                '}';
    }

    public static class Builder
    {
        private int subjectCode;
        private String examDesc, examId;
        private LocalDate examDate;

        public Builder setExamId(String examId)
        {
            this.examId = examId;
            return this;
        }

        public Builder setSubjectCode(int subjectCode)
        {
            this.subjectCode = subjectCode;
            return this;
        }


        public Builder setExamDesc(String examDesc)
        {
            this.examDesc = examDesc;
            return this;
        }

        public Builder setExamDate(LocalDate examDate)
        {
            this.examDate = examDate;
            return this;
        }

        public Examination build()
        {
            return new Examination(this);
        }

        public Builder copy(Examination examination)
        {
            this.examId = examination.examId;
            this.subjectCode = examination.subjectCode;
            this.examDesc = examination.examDesc;
            this.examDate = examination.examDate;
            return this;
        }

    }
}

