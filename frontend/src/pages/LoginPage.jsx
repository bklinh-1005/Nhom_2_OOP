import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const formData = new URLSearchParams();
            formData.append('username', username);
            formData.append('password', password);

            const res = await fetch('/api/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData.toString()
            });

            if (!res.ok) {
                throw new Error('Sai tên đăng nhập hoặc mật khẩu');
            }

            let data;
            const contentType = res.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                data = await res.json();
            } else {
                const text = await res.text();
                data = JSON.parse(text);
            }

            localStorage.setItem('studentId', data.studentId || data.id || username);
            localStorage.setItem('studentName', data.fullName || data.username || 'Sinh viên');
            localStorage.setItem('className', data.className || 'Chưa cập nhật');
            localStorage.setItem('major', data.major || 'Chưa cập nhật');
            localStorage.setItem('maxCredits', data.maxCredits || 24);

            navigate('/courses');
        } catch (err) {
            setError(err.message);
        }
    };

    return (
        <div className="min-h-screen bg-phenikaa-bg flex flex-col justify-center items-center">
            <div className="w-full max-w-md bg-white rounded-xl shadow-lg p-8 border-t-4 border-phenikaa-navy">
                <div className="text-center mb-8">
                    <h1 className="text-2xl font-bold text-phenikaa-navy mb-2">
                        HỆ THỐNG ĐĂNG KÝ
                    </h1>
                    <p className="text-sm text-gray-500 uppercase tracking-widest">
                        Cổng thông tin đào tạo
                    </p>
                </div>

                {error && (
                    <div className="mb-4 bg-red-100 text-red-700 p-3 rounded text-sm text-center">
                        {error}
                    </div>
                )}

                <form onSubmit={handleLogin} className="space-y-5">
                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">
                            Mã sinh viên
                        </label>
                        <input
                            type="text"
                            required
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-phenikaa-accent focus:border-phenikaa-accent outline-none transition-colors"
                            placeholder="Nhập mã sinh viên..."
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-gray-700 mb-1">
                            Mật khẩu
                        </label>
                        <input
                            type="password"
                            required
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:ring-phenikaa-accent focus:border-phenikaa-accent outline-none transition-colors"
                            placeholder="Nhập mật khẩu..."
                        />
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-phenikaa-accent hover:bg-orange-600 text-white font-medium py-2.5 rounded-md transition-colors shadow-sm"
                    >
                        ĐĂNG NHẬP
                    </button>
                </form>
            </div>
        </div>
    );
}