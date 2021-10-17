package za.ac.cput.entity.tertiaryInstitution;

/**
 * Course.java
 * Course entity created using builder pattern.
 * @author Jonathan Kleynhans 217268153
 * 01 June 2021
 * Updated: 28 August 2021
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Department")
public class Department {

    @Id
    private String departmentId;
    private String departmentName;
    private String departmentDesc;

    public Department(){}

    public Department(DepartmentBuilder department){
        this.departmentId = department.departmentId;
        this.departmentName = department.departmentName;
        this.departmentDesc = department.departmentDesc;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentID=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", departmentDesc='" + departmentDesc + '\'' +
                '}';
    }

    public static class DepartmentBuilder {

        private String departmentName, departmentDesc, departmentId;

        public DepartmentBuilder setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public DepartmentBuilder setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
            return this;
        }

        public DepartmentBuilder setDepartmentDesc(String departmentDesc) {
            this.departmentDesc = departmentDesc;
            return this;
        }

        public Department build(){
            return new Department(this);
        }

        public DepartmentBuilder copy(Department department){
            this.departmentId = department.departmentId;
            this.departmentName = department.departmentName;
            this.departmentDesc = department.departmentDesc;

            return this;
        }
    }
}
