package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Registration;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistrationRepository {
    private static final String FILE_PATH = "src/main/resources/data/registrations.json";

    public List<Registration> findAll() {
        return FileUtils.readList(FILE_PATH, Registration.class);
    }

    public Registration findByStudentId(String studentId) {
        List<Registration> all = findAll();
        for (Registration r : all) {
            if (r.getStudent().getStudentId().equals(studentId)) {
                return r;
            }
        }
        return null;
    }

    public void save(Registration registration) {
        List<Registration> all = findAll();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getRegistrationId().equals(registration.getRegistrationId())) {
                all.set(i, registration);
                found = true;
                break;
            }
        }
        if (!found) {
            all.add(registration);
        }
        FileUtils.writeList(FILE_PATH, all);
    }
}