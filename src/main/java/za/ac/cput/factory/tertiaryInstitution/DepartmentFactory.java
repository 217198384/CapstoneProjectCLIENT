package za.ac.cput.factory.tertiaryInstitution;

import za.ac.cput.entity.tertiaryInstitution.Department;

/**
 * DepartmentFactory.java
 * Factory class for Department.java
 * @author Jonathan Kleynhans 217268153
 * 09 June 2021
 */

public class DepartmentFactory {

        public static Department build (String departmentId, String departmentName, String departmentDesc){

            if( departmentId.isEmpty() || departmentName.isEmpty() || departmentDesc.isEmpty() ){
                return null;
            }

            return new Department.DepartmentBuilder()
                    .setDepartmentId(departmentId)
                    .setDepartmentName(departmentName)
                    .setDepartmentDesc(departmentDesc)
                    .build();
        }
    }
