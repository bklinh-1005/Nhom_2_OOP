package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RegistrationRepository {

    private static final String FILE_PATH = "src/main/resources/data/registrations.json";

    public List<Registration> findAll() {
        return FileUtils.readList(FILE_PATH, Registration.class);
    }

    public List<Registration> findByStudentId(String studentId) {

        List<Registration> result = new ArrayList<>();

        for (Registration registration : findAll()) {

            if (registration.getStudent() != null
                    && registration.getStudent().getId().equals(studentId)) {

                result.add(registration);
            }
        }

        return result;
    }

    public void save(List<Registration> registrations) {
        FileUtils.writeList(FILE_PATH, registrations);
    }
}