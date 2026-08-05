import { useState, useEffect } from 'react';
import Sidebar from '../components/Sidebar';
import Topbar from '../components/Topbar';
import curriculumData from '../data/curriculum.json';

export default function CurriculumPage() {
  const [courses, setCourses] = useState([]);
  const [keyword, setKeyword] = useState('');
  const [selectedMajor, setSelectedMajor] = useState('KHMT');

  const majors = [
    { id: 'KHMT', name: 'Đại học Chính quy Khóa 18_ 4 năm - Khoa học máy tính' }
  ];

  useEffect(() => {
    fetchCourses();
  }, [selectedMajor]);

  const fetchCourses = (search = '') => {
    let filtered = curriculumData;
    if (search) {
      filtered = curriculumData.filter(c => 
        c.courseName.toLowerCase().includes(search.toLowerCase()) ||
        c.courseId.toLowerCase().includes(search.toLowerCase())
      );
    }
    setCourses(filtered);
  };

  const subjectsList = courses.map(c => ({
    subjectId: c.courseId,
    subjectName: c.courseName,
    credits: c.credits
  }));

  return (
    <div className="flex h-screen overflow-hidden bg-phenikaa-bg">
      <Sidebar />
      <div className="flex-1 flex flex-col overflow-hidden">
        <Topbar title="Chương trình học" />
        
        <div className="flex-1 overflow-y-auto p-6">
          <div className="flex flex-col gap-6 max-w-6xl mx-auto">
            
            {/* Top Section: Selection */}
            <div className="bg-white rounded-lg shadow-sm p-6">
              <h3 className="font-bold text-gray-800 mb-4 text-lg border-b pb-2">Tra cứu chương trình đào tạo</h3>
              
              <div className="flex flex-col md:flex-row gap-4">
                <div className="flex-1">
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Chọn ngành đào tạo:
                  </label>
                  <select 
                    value={selectedMajor}
                    onChange={(e) => setSelectedMajor(e.target.value)}
                    className="w-full border border-gray-300 rounded-md p-2 text-sm focus:outline-none focus:border-phenikaa-accent focus:ring-1 focus:ring-phenikaa-accent"
                  >
                    {majors.map(m => (
                      <option key={m.id} value={m.id}>{m.name}</option>
                    ))}
                  </select>
                </div>

                <div className="flex-1">
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    Tìm kiếm học phần:
                  </label>
                  <div className="relative">
                    <input 
                      type="text" 
                      value={keyword}
                      onChange={(e) => setKeyword(e.target.value)}
                      onKeyDown={(e) => e.key === 'Enter' && fetchCourses(keyword)}
                      placeholder="Nhập mã hoặc tên học phần..." 
                      className="w-full border border-gray-300 rounded-md pl-3 pr-10 py-2 text-sm focus:outline-none focus:border-phenikaa-accent focus:ring-1 focus:ring-phenikaa-accent"
                    />
                    <button 
                      onClick={() => fetchCourses(keyword)}
                      className="absolute right-2 top-2 text-gray-400 hover:text-phenikaa-accent"
                    >
                      🔍
                    </button>
                  </div>
                </div>
              </div>
            </div>

            {/* Bottom Section: Course List */}
            <div className="bg-white rounded-lg shadow-sm p-6 min-h-[400px]">
              <div className="flex justify-between items-center mb-4">
                <h4 className="font-semibold text-gray-800 text-lg">
                  Danh sách môn học ({majors.find(m => m.id === selectedMajor)?.name})
                </h4>
                <span className="text-sm text-gray-500 bg-gray-100 px-3 py-1 rounded-full">
                  Tổng số: {subjectsList.length} học phần
                </span>
              </div>
              
              <div className="overflow-x-auto rounded-lg border border-gray-200">
                <table className="w-full text-sm text-left">
                  <thead className="bg-phenikaa-navy text-white font-medium">
                    <tr>
                      <th className="px-6 py-4">Mã HP</th>
                      <th className="px-6 py-4">Tên học phần</th>
                      <th className="px-6 py-4 text-center">Số tín chỉ</th>
                    </tr>
                  </thead>
                  <tbody>
                    {subjectsList.length === 0 ? (
                      <tr>
                        <td colSpan="3" className="text-center py-8 text-gray-500">Không tìm thấy học phần nào trong chương trình này.</td>
                      </tr>
                    ) : (
                      subjectsList.map((subject, index) => {
                        return (
                          <tr key={subject.subjectId} className={`border-b hover:bg-gray-50 ${index % 2 === 0 ? 'bg-white' : 'bg-gray-50/50'}`}>
                            <td className="px-6 py-4 font-semibold text-phenikaa-navy">{subject.subjectId}</td>
                            <td className="px-6 py-4 text-gray-800">{subject.subjectName}</td>
                            <td className="px-6 py-4 text-center font-medium">{subject.credits}</td>
                          </tr>
                        );
                      })
                    )}
                  </tbody>
                </table>
              </div>
            </div>

          </div>
        </div>
      </div>
    </div>
  );
}
