package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Registration;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationRepository {
    private static final String FILE_PATH = "src/main/resources/data/registrations.json";

    // Các method findAll(), findByStudentId(), save() sẽ được bổ sung ở Giai đoạn 1
}