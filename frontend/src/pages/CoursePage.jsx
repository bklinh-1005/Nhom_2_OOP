import { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import ClassDetailModal from '../components/ClassDetailModal';

export default function CoursePage() {
    const [courses, setCourses] = useState([]);
    const [keyword, setKeyword] = useState('');
    const [registeredCredits, setRegisteredCredits] = useState(0);
    const [registeredCourseIds, setRegisteredCourseIds] = useState([]);

    // States for the new UI
    const [selectedSubjectId, setSelectedSubjectId] = useState(null);
    const [selectedClassDetail, setSelectedClassDetail] = useState(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    // States for dynamic filtering
    const [selectedLecturers, setSelectedLecturers] = useState([]);
    const [selectedDays, setSelectedDays] = useState([]);

    const studentName = localStorage.getItem('studentName') || 'Trương Viết Thành';
    const studentId = localStorage.getItem('studentId') || '24106898';
    const className = localStorage.getItem('className') || 'SEK68';
    const major = localStorage.getItem('major') || 'Công nghệ phần mềm';
    const maxCredits = localStorage.getItem('maxCredits') || '24';

    useEffect(() => {
        fetchCourses();
        fetchSummary();
    }, []);

    const fetchSummary = async () => {
        try {
            const res = await fetch(`/api/registrations/${studentId}/summary`);
            if (res.ok) {
                const data = await res.json();
                setRegisteredCredits(data.totalCredits || 0);
                if (data.courses) {
                    setRegisteredCourseIds(data.courses.map(item => item.course.courseId));
                }
            }
        } catch (err) {
            console.error(err);
        }
    };

    const fetchCourses = async (search = '') => {
        try {
            let url = '/api/courses';
            if (search) url = `/api/courses/search?keyword=${encodeURIComponent(search)}`;

            const res = await fetch(url);
            if (res.ok) {
                const data = await res.json();
                setCourses(data);
            }
        } catch (err) {
            console.error(err);
        }
    };

    const handleRegister = async (courseId) => {
        try {
            const formData = new URLSearchParams();
            formData.append('studentId', studentId);
            formData.append('courseId', courseId);

            const res = await fetch('/api/registrations', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            });

            if (res.ok) {
                alert('Đăng ký môn học thành công!');
                fetchCourses(keyword);
                fetchSummary();
            } else {
                try {
                    const errData = await res.json();
                    alert('Lỗi: ' + (errData.message || errData.error || 'Hệ thống bận'));
                } catch {
                    alert('Lỗi kết nối đến Backend (Máy chủ chưa bật).');
                }
            }
        } catch (err) {
            alert('Đã xảy ra lỗi khi đăng ký!');
        }
    };

    const handleChangeClass = async (oldCourseId, newCourseId) => {
        const confirmMsg = "Bạn có chắc chắn muốn đổi sang lớp này không? Lớp cũ sẽ bị hủy.";
        if (!window.confirm(confirmMsg)) return;

        try {
            // 1. Delete old class
            const delRes = await fetch(`/api/registrations/${studentId}?courseId=${oldCourseId}`, {
                method: 'DELETE'
            });

            if (!delRes.ok) {
                alert("Lỗi khi hủy lớp cũ. Hủy thao tác đổi lớp.");
                return;
            }

            // 2. Register new class
            const formData = new URLSearchParams();
            formData.append('studentId', studentId);
            formData.append('courseId', newCourseId);

            const postRes = await fetch('/api/registrations', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            });

            if (postRes.ok) {
                alert('Đổi lớp thành công!');
                fetchCourses(keyword);
                fetchSummary();
            } else {
                const errData = await postRes.json();
                alert('Lỗi đăng ký lớp mới: ' + (errData.message || errData.error || 'Hệ thống bận'));
                fetchCourses(keyword);
                fetchSummary();
            }
        } catch (err) {
            alert('Đã xảy ra lỗi khi đổi lớp!');
        }
    };

    // Group courses by Subject
    const subjectsMap = new Map();
    courses.forEach(c => {
        const subjectId = c.courseId.split('-')[0];
        if (!subjectsMap.has(subjectId)) {
            subjectsMap.set(subjectId, {
                subjectId: subjectId,
                subjectName: c.courseName,
                credits: c.credits,
                classes: []
            });
        }
        subjectsMap.get(subjectId).classes.push(c);
    });

    const subjectsList = Array.from(subjectsMap.values());

    // Automatically select the first subject if none is selected
    useEffect(() => {
        if (subjectsList.length > 0 && !subjectsList.find(s => s.subjectId === selectedSubjectId)) {
            setSelectedSubjectId(subjectsList[0].subjectId);
        }
    }, [subjectsList, selectedSubjectId]);

    // Reset filters when the selected subject changes
    useEffect(() => {
        setSelectedLecturers([]);
        setSelectedDays([]);
    }, [selectedSubjectId]);

    const selectedSubject = subjectsList.find(s => s.subjectId === selectedSubjectId);
    const classesOfSubject = selectedSubject ? selectedSubject.classes : [];

    // Extract unique filter options for the current subject
    const availableLecturers = Array.from(new Set(classesOfSubject.map(c => c.lecturer?.fullName).filter(Boolean)));
    const availableDays = Array.from(new Set(classesOfSubject.map(c => c.schedule?.dayOfWeek).filter(Boolean)));

    // Apply filters to get the classes to display
    const classesToDisplay = classesOfSubject.filter(course => {
        const matchLecturer = selectedLecturers.length === 0 || (course.lecturer && selectedLecturers.includes(course.lecturer.fullName));
        const matchDay = selectedDays.length === 0 || (course.schedule && selectedDays.includes(course.schedule.dayOfWeek));
        return matchLecturer && matchDay;
    });

    const handleLecturerChange = (lecturerName) => {
        setSelectedLecturers(prev =>
            prev.includes(lecturerName) ? prev.filter(l => l !== lecturerName) : [...prev, lecturerName]
        );
    };

    const handleDayChange = (day) => {
        setSelectedDays(prev =>
            prev.includes(day) ? prev.filter(d => d !== day) : [...prev, day]
        );
    };

    const handleOpenDetailModal = (courseClass) => {
        setSelectedClassDetail(courseClass);
        setIsModalOpen(true);
    };

    const handleCloseDetailModal = () => {
        setIsModalOpen(false);
        setSelectedClassDetail(null);
    };

    return (
        <div className="flex h-screen overflow-hidden bg-phenikaa-bg">
            <Sidebar />
            <div className="flex-1 flex flex-col overflow-hidden">
                <Topbar title="Đăng ký học" />

                <div className="flex-1 overflow-y-auto p-6 bg-[#eaeff8]">
                    <div className="flex flex-col lg:flex-row gap-6">

                        {/* Left Column */}
                        <div className="w-full lg:w-1/4 space-y-6">
                            {/* Profile Box */}
                            <div className="bg-white rounded-lg shadow-sm p-6 text-center h-auto">
                                <div className="w-24 h-24 bg-gray-200 rounded-lg mx-auto mb-4 flex items-center justify-center overflow-hidden shadow-inner">
                                    <span className="text-4xl font-bold text-gray-500">{studentName.charAt(0)}</span>
                                </div>
                                <h3 className="font-bold text-gray-800 text-lg">{studentName}</h3>
                                <p className="text-sm text-gray-500 mb-6">Mã số: {studentId}</p>

                                <div className="text-left text-sm text-gray-700 space-y-3">
                                    <div className="flex justify-between border-b pb-2">
                                        <span className="font-medium">Họ tên:</span>
                                        <span className="font-bold text-gray-800">{studentName}</span>
                                    </div>
                                    <div className="flex justify-between border-b pb-2">
                                        <span className="font-medium">Lớp:</span>
                                        <span className="font-bold text-gray-800 text-right">{className}</span>
                                    </div>
                                    <div className="flex justify-between border-b pb-2">
                                        <span className="font-medium">Ngành học:</span>
                                        <span className="font-bold text-gray-800 text-right w-1/2">{major}</span>
                                    </div>
                                    <div className="flex justify-between border-b pb-2">
                                        <span className="font-medium">Số tín chỉ tối đa:</span>
                                        <span className="font-bold text-gray-800">{maxCredits} TC</span>
                                    </div>
                                    <div className="flex justify-between pb-2">
                                        <span className="font-medium">Đã đăng ký:</span>
                                        <span className="font-bold text-[#3b82f6]">{registeredCredits} TC</span>
                                    </div>
                                </div>
                            </div>

                            {/* Filter Box */}
                            <div className="bg-white rounded-lg shadow-sm p-5">
                                <h4 className="font-bold text-gray-800 mb-2">
                                    Bộ lọc tìm kiếm
                                </h4>
                                <p className="text-sm text-gray-500 mb-6">
                                    Chọn giảng viên và thứ học để lọc các lớp của học phần đang chọn.
                                </p>
                                <div className="space-y-4">
                                    <div>
                                        <label className="block text-sm font-bold text-gray-700 mb-2">Giảng viên</label>
                                        {availableLecturers.length === 0 ? (
                                            <p className="text-xs text-gray-400">Không có dữ liệu</p>
                                        ) : (
                                            <div className="space-y-2">
                                                {availableLecturers.map((lecturer) => (
                                                    <label key={lecturer} className="flex items-center space-x-2">
                                                        <input
                                                            type="checkbox"
                                                            checked={selectedLecturers.includes(lecturer)}
                                                            onChange={() => handleLecturerChange(lecturer)}
                                                            className="rounded border-gray-300"
                                                        />
                                                        <span className="text-sm text-gray-700 font-medium">{lecturer}</span>
                                                    </label>
                                                ))}
                                            </div>
                                        )}
                                    </div>

                                    <div className="pt-2">
                                        <label className="block text-sm font-bold text-gray-700 mb-2">Thứ học</label>
                                        {availableDays.length === 0 ? (
                                            <p className="text-xs text-gray-400">Không có dữ liệu</p>
                                        ) : (
                                            <div className="space-y-2">
                                                {availableDays.map((day) => (
                                                    <label key={day} className="flex items-center space-x-2">
                                                        <input
                                                            type="checkbox"
                                                            checked={selectedDays.includes(day)}
                                                            onChange={() => handleDayChange(day)}
                                                            className="rounded border-gray-300"
                                                        />
                                                        <span className="text-sm text-gray-700 font-medium">{day}</span>
                                                    </label>
                                                ))}
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div>
                        </div>

                        {/* Right Column */}
                        <div className="w-full lg:w-3/4 space-y-6">

                            {/* Search Bar */}
                            <div className="bg-white rounded-lg shadow-sm p-4 flex items-center gap-4">
                                <span className="text-base font-bold text-gray-800 w-24">Học phần</span>
                                <div className="flex-1">
                                    <input
                                        type="text"
                                        value={keyword}
                                        onChange={(e) => setKeyword(e.target.value)}
                                        onKeyDown={(e) => e.key === 'Enter' && fetchCourses(keyword)}
                                        placeholder="Tìm kiếm học phần"
                                        className="w-full border rounded-lg px-4 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-[#3b82f6] focus:border-transparent bg-gray-50"
                                    />
                                </div>
                            </div>

                            {/* Subjects List */}
                            <div className="bg-white rounded-lg shadow-sm p-6">
                                <h4 className="font-bold text-gray-800 mb-6">Học phần</h4>

                                {subjectsList.length === 0 ? (
                                    <p className="text-gray-500 text-center py-4">Không tìm thấy học phần nào.</p>
                                ) : (
                                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
                                        {subjectsList.map((subject) => {
                                            const isSelected = selectedSubjectId === subject.subjectId;
                                            return (
                                                <div
                                                    key={subject.subjectId}
                                                    onClick={() => setSelectedSubjectId(subject.subjectId)}
                                                    className={`p-4 rounded-lg cursor-pointer transition-all duration-200 min-h-[100px] flex flex-col justify-center ${
                                                        isSelected
                                                            ? 'bg-[#3b82f6] text-white shadow-md transform scale-[1.02]'
                                                            : 'bg-white hover:bg-gray-50 text-gray-800 hover:shadow-sm border border-gray-100'
                                                    }`}
                                                >
                                                    <div className="font-bold text-base mb-2">
                                                        {subject.subjectId} - {subject.subjectName}
                                                    </div>
                                                    <div className={`text-sm ${isSelected ? 'text-blue-100' : 'text-gray-600'}`}>
                                                        ({subject.credits} Tín chỉ)
                                                    </div>
                                                </div>
                                            );
                                        })}
                                    </div>
                                )}
                            </div>

                            {/* Classes List */}
                            {selectedSubject && (
                                <div className="bg-white rounded-lg shadow-sm p-6 min-h-[400px]">
                                    <h4 className="font-bold text-gray-800 mb-6">Lớp học phần</h4>

                                    {classesToDisplay.length === 0 ? (
                                        <p className="text-gray-500 text-center py-4">Không có lớp nào phù hợp với bộ lọc.</p>
                                    ) : (
                                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                            {classesToDisplay.map((course) => {
                                                const isFull = course.registeredSlots >= course.maxSlots;
                                                const isRegistered = registeredCourseIds.includes(course.courseId);

                                                // Check if any class in the same subject is registered
                                                const subjectId = course.courseId.split('-')[0];
                                                const registeredCourseInSubject = registeredCourseIds.find(id => id.split('-')[0] === subjectId);

                                                let btnClass = 'bg-[#22c55e] text-white hover:bg-green-600';
                                                let btnText = 'Đăng ký';
                                                let isDisabled = false;
                                                let action = () => handleRegister(course.courseId);

                                                if (isRegistered) {
                                                    btnClass = 'bg-gray-400 text-white cursor-not-allowed';
                                                    btnText = 'Đã đăng ký';
                                                    isDisabled = true;
                                                } else if (registeredCourseInSubject) {
                                                    btnClass = 'bg-yellow-500 text-white hover:bg-yellow-600';
                                                    btnText = 'Đổi lớp';
                                                    action = () => handleChangeClass(registeredCourseInSubject, course.courseId);
                                                } else if (isFull) {
                                                    btnClass = 'bg-gray-200 text-gray-500 cursor-not-allowed';
                                                    btnText = 'Đã đầy';
                                                    isDisabled = true;
                                                }

                                                return (
                                                    <div key={course.courseId} className="border border-gray-200 rounded-lg p-4 flex flex-col bg-white hover:shadow-md transition-shadow">
                                                        <div className="flex justify-between items-start mb-4 border-b pb-2">
                                                            <h5 className="font-bold text-gray-800 text-sm w-4/5 leading-snug">{course.courseName}</h5>
                                                            <button className="text-gray-400 hover:text-gray-600 font-bold">×</button>
                                                        </div>

                                                        <div className="flex justify-between text-sm text-gray-600 mb-2">
                                                            <span>Lý thuyết</span>
                                                            <span className="font-medium text-gray-800">
                                {course.schedule ? course.schedule.dayOfWeek : 'Chưa xếp'}
                              </span>
                                                        </div>

                                                        <div className="flex justify-between text-sm text-gray-600 mb-6">
                                                            <span>Tổng số: {course.maxSlots}</span>
                                                            <span>Đã đăng ký: {course.registeredSlots}</span>
                                                        </div>

                                                        <div className="mt-auto flex gap-2">
                                                            <button
                                                                onClick={() => handleOpenDetailModal(course)}
                                                                className="flex-1 bg-white border border-gray-300 text-gray-700 font-bold py-2 px-2 rounded hover:bg-gray-50 transition-colors text-sm"
                                                            >
                                                                Xem chi tiết
                                                            </button>
                                                            <button
                                                                onClick={action}
                                                                disabled={isDisabled}
                                                                className={`flex-1 font-bold py-2 px-2 rounded transition-colors text-sm ${btnClass}`}
                                                            >
                                                                {btnText}
                                                            </button>
                                                        </div>
                                                    </div>
                                                );
                                            })}
                                        </div>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>

            <ClassDetailModal
                isOpen={isModalOpen}
                onClose={handleCloseDetailModal}
                courseClass={selectedClassDetail}
            />
        </div>
    );
}
