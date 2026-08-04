export default function Topbar({ title }) {
  const studentName = localStorage.getItem('studentName') || 'Trương Viết Thành';
  
  return (
    <div className="h-16 bg-phenikaa-navy text-white flex items-center justify-between px-6 shadow-md flex-shrink-0">
      <div className="text-sm font-medium text-gray-300">
        Đăng ký trực tuyến &gt; <span className="text-white">{title}</span>
      </div>
      
      <div className="flex items-center space-x-6">

        
        <div className="flex items-center space-x-2">
          <div className="w-8 h-8 bg-gray-400 rounded-full flex items-center justify-center overflow-hidden">
            👤
          </div>
          <span className="text-sm font-medium">{studentName}</span>
        </div>
      </div>
    </div>
  );
}
