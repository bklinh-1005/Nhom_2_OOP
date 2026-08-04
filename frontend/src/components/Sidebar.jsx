import { Link, useLocation } from 'react-router-dom';

export default function Sidebar() {
  const location = useLocation();
  const currentPath = location.pathname;

  const menuItems = [
    { name: 'Chương trình học', path: '/curriculum', icon: '🔍' },
    { name: 'Đăng ký học', path: '/courses', icon: '📝' },
    { name: 'Theo dõi tín', path: '/my-registrations', icon: '📊' },
    { name: 'Thời khóa biểu', path: '/schedule', icon: '📅' },
    { name: 'Danh sách giảng viên', path: '/lecturers', icon: '👨‍🏫' }
  ];

  return (
    <div className="w-64 h-screen bg-phenikaa-navy text-white flex flex-col flex-shrink-0 sticky top-0">
      <div className="p-6 flex flex-col items-center border-b border-white/10">
        <div className="w-16 h-16 bg-white rounded-full flex items-center justify-center mb-4">
           <span className="text-phenikaa-navy font-bold text-2xl">🎓</span>
        </div>
        <h2 className="font-bold text-lg text-center tracking-wide uppercase">CỔNG THÔNG TIN<br/>ĐÀO TẠO</h2>
      </div>

      <nav className="flex-1 p-4 space-y-2 mt-4">
        {menuItems.map((item, idx) => (
          <Link
            key={idx}
            to={item.path}
            className={`flex items-center px-4 py-3 rounded-md transition-colors ${
              currentPath === item.path 
                ? 'bg-white/10 text-phenikaa-accent border-l-4 border-phenikaa-accent' 
                : 'text-gray-300 hover:bg-white/5 hover:text-phenikaa-accent border-l-4 border-transparent'
            }`}
          >
            <span className="mr-3">{item.icon}</span>
            <span className="text-sm font-medium">{item.name}</span>
          </Link>
        ))}
      </nav>

      <div className="p-4 border-t border-white/10">
        <button 
          onClick={() => {
            localStorage.clear();
            window.location.href = '/login';
          }}
          className="w-full text-left px-4 py-2 text-sm text-gray-300 hover:text-phenikaa-accent transition-colors"
        >
          🚪 Đăng xuất
        </button>
      </div>
    </div>
  );
}
