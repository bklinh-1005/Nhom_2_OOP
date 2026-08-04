import React from 'react';

export default function ClassDetailModal({ isOpen, onClose, courseClass }) {
  if (!isOpen || !courseClass) return null;

  // Derive Subject ID (Mã học phần) from Course Class ID (Mã lớp học phần)
  // Assuming format like "INT105-01"
  const subjectId = courseClass.courseId ? courseClass.courseId.split('-')[0] : '';
  const lecturerName = courseClass.lecturer ? courseClass.lecturer.fullName : 'Chưa xếp';
  const scheduleText = courseClass.schedule 
    ? `${courseClass.schedule.dayOfWeek}, tiết ${courseClass.schedule.period}, phòng ${courseClass.schedule.room}`
    : 'Chưa xếp';

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
      <div className="bg-white rounded-xl shadow-xl max-w-md w-full relative p-6">
        <button 
          onClick={onClose}
          className="absolute right-4 top-4 text-gray-500 hover:text-gray-800 font-bold text-xl"
        >
          ×
        </button>

        <h2 className="text-xl font-semibold mb-6 text-gray-800 pr-8">Chi tiết lớp học phần</h2>

        <div className="space-y-4">
          <div>
            <p className="text-sm text-gray-500">Mã lớp học phần</p>
            <p className="font-semibold text-gray-800">{courseClass.courseId}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Mã học phần</p>
            <p className="font-semibold text-gray-800">{subjectId}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Tên học phần</p>
            <p className="font-semibold text-gray-800">{courseClass.courseName}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Số tín chỉ</p>
            <p className="font-semibold text-gray-800">{courseClass.credits}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Giảng viên</p>
            <p className="font-semibold text-gray-800">{lecturerName}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Sĩ số</p>
            <p className="font-semibold text-gray-800">{courseClass.registeredSlots}/{courseClass.maxSlots}</p>
          </div>

          <div>
            <p className="text-sm text-gray-500">Lịch học</p>
            <p className="font-semibold text-gray-800">{scheduleText}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
