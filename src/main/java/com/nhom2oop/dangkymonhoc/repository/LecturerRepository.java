package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Lecturer;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LecturerRepository {

    private static final String FILE_PATH = "src/main/resources/data/lecturers.json";

    public List<Lecturer> findAll() {
        return FileUtils.readList(FILE_PATH, Lecturer.class);
    }


    public Lecturer findById(String id) {
        List<Lecturer> lecturers = findAll();

        for (Lecturer lecturer : lecturers) {
            if (lecturer.getId() != null && lecturer.getId().equals(id)) {
                return lecturer;
            }
        }

        return null;
    }


    public Lecturer findByLecturerId(String lecturerId) {
        List<Lecturer> lecturers = findAll();

        for (Lecturer lecturer : lecturers) {
            if (lecturer.getLecturerId() != null
                    && lecturer.getLecturerId().equals(lecturerId)) {
                return lecturer;
            }
        }

        return null;
    }


    public void save(Lecturer lecturer) {
        List<Lecturer> lecturers = findAll();

        boolean found = false;

        for (int i = 0; i < lecturers.size(); i++) {
            if (lecturers.get(i).getId().equals(lecturer.getId())) {
                lecturers.set(i, lecturer);
                found = true;
                break;
            }
        }

        if (!found) {
            lecturers.add(lecturer);
        }

        FileUtils.writeList(FILE_PATH, lecturers);
    }
}
