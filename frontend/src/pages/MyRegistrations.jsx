import { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import ClassDetailModal from '../components/ClassDetailModal';

export default function MyRegistrations() {
  const [courses, setCourses] = useState([]);
  const [totalCredits, setTotalCredits] = useState(0);
  const [selectedClassDetail, setSelectedClassDetail] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const studentId = localStorage.getItem('studentId');

  const handleOpenDetailModal = (courseClass) => {
    setSelectedClassDetail(courseClass);
    setIsModalOpen(true);
  };

  const handleCloseDetailModal = () => {
    setIsModalOpen(false);
    setSelectedClassDetail(null);
  };

  useEffect(() => {
    fetchSummary();
  }, []);

  const fetchSummary = async () => {
    try {
      const res = await fetch(`/api/registrations/${studentId}/summary`);
      if (res.ok) {
        const data = await res.json();
        setCourses(data.courses || []);
        setTotalCredits(data.totalCredits || 0);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const handleCancel = async (courseId) => {
    if (!window.confirm('Bạn có chắc chắn muốn hủy đăng ký môn này?')) return;

    try {
      const res = await fetch(`/api/registrations/${studentId}?courseId=${courseId}`, {
        method: 'DELETE'
      });
      if (res.ok) {
        alert('Hủy học phần thành công!');
        fetchSummary();
      } else {
        alert('Có lỗi xảy ra khi hủy môn.');
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="flex h-screen overflow-hidden bg-phenikaa-bg">
      <Sidebar />
      <div className="flex-1 flex flex-col overflow-hidden">
        <Topbar title="Theo dõi tiến độ học tập" />

        <div className="flex-1 overflow-y-auto p-6">
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6 border-l-4 border-phenikaa-accent">
            <h3 className="text-lg font-bold text-phenikaa-navy mb-2">TỔNG KẾT TÍN CHỈ</h3>
            <p className="text-gray-600">Bạn đã đăng ký thành công: <span className="text-2xl font-bold text-phenikaa-accent mx-2">{totalCredits}</span> tín chỉ.</p>
          </div>

          <div className="bg-white rounded-lg shadow-sm p-4">
            <h4 className="font-semibold text-gray-800 mb-4">Lớp học phần đã đăng ký</h4>
            <div className="overflow-x-auto">
              <table className="w-full text-sm text-left">
                <thead className="bg-gray-50 text-gray-600 font-medium border-b">
                  <tr>
                    <th className="px-4 py-3">Mã HP</th>
                    <th className="px-4 py-3">Tên học phần</th>
                    <th className="px-4 py-3">Số TC</th>
                    <th className="px-4 py-3">Ngày đăng ký</th>
                    <th className="px-4 py-3 text-center">Thao tác</th>
                  </tr>
                </thead>
                <tbody>
                  {courses.length === 0 ? (
                    <tr>
                      <td colSpan="5" className="text-center py-6 text-gray-500">Chưa có môn học nào được đăng ký.</td>
                    </tr>
                  ) : (
                    courses.map((detail, idx) => (
                      <tr key={idx} className="border-b hover:bg-gray-50">
                        <td className="px-4 py-3 font-medium text-phenikaa-navy">{detail.course.courseId}</td>
                        <td className="px-4 py-3">{detail.course.courseName}</td>
                        <td className="px-4 py-3">{detail.course.credits}</td>
                        <td className="px-4 py-3 text-gray-500">{detail.registeredDate}</td>
                        <td className="px-4 py-3 text-center">
                          <button
                            onClick={() => handleOpenDetailModal(detail.course)}
                            className="text-[#3b82f6] hover:text-blue-700 font-medium underline text-xs mr-4"
                          >
                            Xem chi tiết
                          </button>
                          <button
                            onClick={() => handleCancel(detail.course.courseId)}
                            className="text-red-500 hover:text-red-700 font-medium underline text-xs"
                          >
                            Hủy đăng ký
                          </button>
                        </td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
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
