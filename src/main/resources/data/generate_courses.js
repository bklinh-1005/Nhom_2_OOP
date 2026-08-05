const fs = require('fs');

const coursesData = [
  { courseId: 'FFS703007', courseName: 'Đại số tuyến tính', credits: 3 },
  { courseId: 'FFS703008', courseName: 'Giải tích', credits: 3 },
  { courseId: 'FEL703001', courseName: 'Tiếng Anh 1', credits: 3 },
  { courseId: 'CSE703010', courseName: 'Đánh giá và kiểm định chất lượng', credits: 3 },
  { courseId: 'CSE702027', courseName: 'Lập trình cho thiết bị di động', credits: 2 },
  { courseId: 'CSE703048', courseName: 'Phân tích và thiết kế phần mềm', credits: 3 },
  { courseId: 'CSE703038', courseName: 'Ngôn ngữ lập trình C', credits: 3 },
  { courseId: 'CSE703024', courseName: 'Toán rời rạc', credits: 3 },
  { courseId: 'CSE703006', courseName: 'Cấu trúc dữ liệu và thuật toán', credits: 3 },
  { courseId: 'CSE703029', courseName: 'Lập trình hướng đối tượng', credits: 3 }
];

const lecturers = [
  { lecturerId: "GV01", id: "L1", username: "lec1", passwordHash: "hash", fullName: "PGS.TS. Trần Kim Anh" },
  { lecturerId: "GV02", id: "L2", username: "lec2", passwordHash: "hash", fullName: "TS. Nguyễn Công Hoan" },
  { lecturerId: "GV03", id: "L3", username: "lec3", passwordHash: "hash", fullName: "ThS. Đinh Xuân Tùng" },
  { lecturerId: "GV04", id: "L4", username: "lec4", passwordHash: "hash", fullName: "GS.TS. Lê Tuấn Anh" },
  { lecturerId: "GV05", id: "L5", username: "lec5", passwordHash: "hash", fullName: "ThS. Đỗ Gia Khánh" },
  { lecturerId: "GV06", id: "L6", username: "lec6", passwordHash: "hash", fullName: "ThS. Trương Hải Đăng" },
  { lecturerId: "GV07", id: "L7", username: "lec7", passwordHash: "hash", fullName: "TS. Phạm Bảo Châu" },
  { lecturerId: "GV08", id: "L8", username: "lec8", passwordHash: "hash", fullName: "ThS. Lê Minh Quân" }
];

const days = ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7'];
const periods = ['1-3', '4-6', '7-9', '10-12'];

function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
}

const generatedClasses = [];

coursesData.forEach(subject => {
  // 2 to 3 classes per subject
  const numClasses = Math.floor(Math.random() * 2) + 2; 
  
  // Pick unique lecturers for this subject's classes
  const shuffledLecturers = shuffleArray([...lecturers]);
  
  for (let i = 1; i <= numClasses; i++) {
    const classSuffix = i.toString().padStart(2, '0'); // -01, -02, -03
    
    const lec = shuffledLecturers[i - 1];
    const maxSlots = 40 + Math.floor(Math.random() * 40);
    const regSlots = Math.floor(Math.random() * (maxSlots - 5));
    const day = days[Math.floor(Math.random() * days.length)];
    const period = periods[Math.floor(Math.random() * periods.length)];
    
    generatedClasses.push({
      courseId: `${subject.courseId}-${classSuffix}`,
      courseName: subject.courseName,
      credits: subject.credits,
      lecturer: lec,
      maxSlots,
      registeredSlots: regSlots,
      schedule: {
        dayOfWeek: day,
        period: period,
        semester: "20241",
        room: `A${Math.floor(Math.random() * 8) + 1}-${Math.floor(Math.random() * 4) + 1}${String(Math.floor(Math.random() * 10) + 1).padStart(2, '0')}`
      }
    });
  }
});

fs.writeFileSync('src/main/resources/data/courses.json', JSON.stringify(generatedClasses, null, 2));
console.log('Generated ' + generatedClasses.length + ' course classes');
