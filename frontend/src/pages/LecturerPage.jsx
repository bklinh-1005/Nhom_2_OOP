import { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';

export default function LecturerPage() {
  const [lecturersData, setLecturersData] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCoursesAndGroup();
  }, []);

  const fetchCoursesAndGroup = async () => {
    try {
      const res = await fetch('/api/courses');
      if (res.ok) {
        const courses = await res.json();
        
        // Gom nhóm môn học theo giảng viên
        const groups = {};
        courses.forEach(course => {
          if (course.lecturer) {
            const lid = course.lecturer.lecturerId;
            if (!groups[lid]) {
              groups[lid] = {
                lecturer: course.lecturer,
                courses: []
              };
            }
            groups[lid].courses.push(course);
          }
        });
        
        // Chuyển object thành mảng và sắp xếp theo mã GV
        const lecturerList = Object.values(groups).sort((a, b) => {
           // Sắp xếp GV01, GV02...
           return a.lecturer.lecturerId.localeCompare(b.lecturer.lecturerId);
        });
        
        setLecturersData(lecturerList);
      }
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex h-screen overflow-hidden bg-phenikaa-bg">
      <Sidebar />
      <div className="flex-1 flex flex-col overflow-hidden">
        <Topbar title="Danh sách giảng viên toàn trường" />
        
        <div className="flex-1 overflow-y-auto p-6">
          <div className="max-w-7xl mx-auto">
            <div className="flex justify-between items-end mb-6">
               <h3 className="text-xl font-bold text-phenikaa-navy">Thông tin Giảng viên và Lớp học phần đảm nhiệm</h3>
               <span className="bg-white px-3 py-1 rounded shadow-sm text-sm font-medium text-gray-600 border">
                 Tổng số giảng viên: {lecturersData.length}
               </span>
            </div>
            
            {loading ? (
              <div className="text-center text-gray-500 my-12 flex flex-col items-center">
                <span className="text-3xl mb-3 animate-spin">⏳</span>
                <p>Đang tải dữ liệu giảng viên...</p>
              </div>
            ) : lecturersData.length === 0 ? (
              <div className="text-center text-gray-500 my-12">Chưa có dữ liệu giảng viên nào.</div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                {lecturersData.map((data, idx) => (
                  <div key={idx} className="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1 flex flex-col">
                    <div className="bg-phenikaa-navy text-white p-4 flex items-center gap-4">
                       <div className="w-12 h-12 bg-white/20 rounded-full flex items-center justify-center text-xl font-bold">
                         {data.lecturer.fullName ? data.lecturer.fullName.split(' ').pop().charAt(0) : 'G'}
                       </div>
                       <div>
                         <h4 className="font-bold text-sm">{data.lecturer.fullName}</h4>
                         <p className="text-xs opacity-80 mt-1">Mã GV: {data.lecturer.lecturerId}</p>
                       </div>
                    </div>
                    <div className="p-4 flex-1 bg-gray-50/50">
                      <h5 className="text-xs font-bold text-gray-500 uppercase tracking-wider mb-3 flex items-center">
                        <span className="bg-blue-100 text-blue-700 py-0.5 px-2 rounded mr-2">
                          {data.courses.length}
                        </span> 
                        Lớp học phần
                      </h5>
                      <ul className="space-y-3">
                        {data.courses.map(c => (
                          <li key={c.courseId} className="text-sm bg-white p-2 rounded border border-gray-100 shadow-sm flex flex-col">
                            <span className="font-bold text-phenikaa-accent text-xs mb-1">{c.courseId}</span>
                            <span className="text-gray-700 leading-tight">{c.courseName}</span>
                          </li>
                        ))}
                      </ul>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
