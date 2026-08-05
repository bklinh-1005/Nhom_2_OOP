import { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';

export default function SchedulePage() {
  const [courses, setCourses] = useState([]);
  const studentId = localStorage.getItem('studentId');

  useEffect(() => {
    fetchSummary();
  }, []);

  const fetchSummary = async () => {
    try {
      const res = await fetch(`/api/registrations/${studentId}/summary`);
      if (res.ok) {
        const data = await res.json();
        setCourses(data.courses || []);
      }
    } catch (err) {
      console.error(err);
    }
  };

  const days = ['Thứ 2', 'Thứ 3', 'Thứ 4', 'Thứ 5', 'Thứ 6', 'Thứ 7', 'Chủ nhật'];
  
  const periods = [
    { id: '1-3', time: '07:00 - 09:30', name: 'Tiết 1-3' },
    { id: '4-6', time: '09:30 - 12:00', name: 'Tiết 4-6' },
    { id: '7-9', time: '13:00 - 15:30', name: 'Tiết 7-9' },
    { id: '10-12', time: '15:30 - 18:00', name: 'Tiết 10-12' },
  ];

  const getCourseForSlot = (day, periodId) => {
    return courses.find(c => {
      const schedule = c.course.schedule;
      if (!schedule) return false;
      return schedule.dayOfWeek === day && schedule.period === periodId;
    });
  };

  const colors = [
    'bg-blue-100 border-blue-500 text-blue-800',
    'bg-green-100 border-green-500 text-green-800',
    'bg-purple-100 border-purple-500 text-purple-800',
    'bg-yellow-100 border-yellow-500 text-yellow-800',
    'bg-pink-100 border-pink-500 text-pink-800',
    'bg-indigo-100 border-indigo-500 text-indigo-800',
    'bg-red-100 border-red-500 text-red-800',
  ];

  return (
    <div className="flex h-screen overflow-hidden bg-phenikaa-bg">
      <Sidebar />
      <div className="flex-1 flex flex-col overflow-hidden">
        <Topbar title="Thời khóa biểu" />
        
        <div className="flex-1 overflow-y-auto p-6">
          <div className="bg-white rounded-lg shadow-sm p-6 mb-6">
            <div className="flex justify-between items-center mb-6">
              <h3 className="text-xl font-bold text-gray-800 flex items-center">
                <span className="mr-2">📅</span> Lịch cá nhân
              </h3>
              <div className="text-sm text-gray-500">
                Tuần hiện tại
              </div>
            </div>

            {/* Grid Thời khóa biểu */}
            <div className="overflow-x-auto">
              <div className="min-w-[800px]">
                {/* Header Row (Days) */}
                <div className="grid grid-cols-8 gap-1 mb-2">
                  <div className="text-center font-semibold text-gray-500 py-2 border-b">Giờ VN</div>
                  {days.map(day => (
                    <div key={day} className="text-center font-semibold text-gray-700 py-2 border-b">
                      {day}
                    </div>
                  ))}
                </div>

                {/* Body Rows (Periods) */}
                <div className="flex flex-col gap-2">
                  {periods.map(period => (
                    <div key={period.id} className="grid grid-cols-8 gap-2 min-h-[120px]">
                      {/* Time Column */}
                      <div className="text-center text-sm font-medium text-gray-500 py-2 border-r pr-2 flex flex-col justify-center">
                        <span className="block text-gray-800">{period.time}</span>
                        <span className="block text-xs">{period.name}</span>
                      </div>
                      
                      {/* Days Columns */}
                      {days.map((day, idx) => {
                        const courseData = getCourseForSlot(day, period.id);
                        return (
                          <div key={day} className="border border-gray-100 rounded bg-gray-50/30 p-1 relative">
                            {courseData && (
                              <div className={`absolute inset-1 p-2 rounded border-l-4 shadow-sm flex flex-col justify-between ${colors[idx % colors.length]}`}>
                                <div>
                                  <div className="font-bold text-sm leading-tight mb-1">{courseData.course.courseName}</div>
                                  <div className="text-xs opacity-90">{courseData.course.courseId}</div>
                                </div>
                                <div className="text-xs font-medium mt-2 bg-white/50 px-1 py-0.5 rounded inline-block">
                                  {period.time}
                                </div>
                              </div>
                            )}
                          </div>
                        );
                      })}
                    </div>
                  ))}
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  );
}
