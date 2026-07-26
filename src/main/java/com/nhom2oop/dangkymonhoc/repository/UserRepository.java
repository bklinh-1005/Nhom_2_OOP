package com.nhom2oop.dangkymonhoc.repository;

import com.nhom2oop.dangkymonhoc.model.Student;
import com.nhom2oop.dangkymonhoc.model.User;
import com.nhom2oop.dangkymonhoc.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private static final String FILE_PATH = "src/main/resources/data/students.json";

    /**
     * Tìm người dùng theo username.
     */
    public User findByUsername(String username) {
        List<Student> students = FileUtils.readList(FILE_PATH, Student.class);

        for (Student student : students) {
            if (student.getUsername() != null
                    && student.getUsername().equalsIgnoreCase(username)) {
                return student;
            }
        }

        return null;
    }

    /**
     * Tìm người dùng theo id.
     */
    public User findById(String id) {
        List<Student> students = FileUtils.readList(FILE_PATH, Student.class);

        for (Student student : students) {
            if (student.getId() != null
                    && student.getId().equals(id)) {
                return student;
            }
        }

        return null;
    }

    /**
     * Thêm mới hoặc cập nhật sinh viên.
     */
    public void save(User user) {

        if (!(user instanceof Student student)) {
            throw new IllegalArgumentException("User phải là Student.");
        }

        List<Student> students = FileUtils.readList(FILE_PATH, Student.class);

        boolean found = false;

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                found = true;
                break;
            }
        }

        if (!found) {
            students.add(student);
        }

        FileUtils.writeList(FILE_PATH, students);
    }

    /**
     * Lấy toàn bộ sinh viên.
     */
    public List<Student> findAllStudents() {
        return FileUtils.readList(FILE_PATH, Student.class);
    }
}