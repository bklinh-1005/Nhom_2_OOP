import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import CoursePage from './pages/CoursePage'
import CurriculumPage from './pages/CurriculumPage'
import MyRegistrations from './pages/MyRegistrations'
import SchedulePage from './pages/SchedulePage'
import LecturerPage from './pages/LecturerPage'
import './index.css'

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/curriculum" element={<CurriculumPage />} />
        <Route path="/courses" element={<CoursePage />} />
        <Route path="/my-registrations" element={<MyRegistrations />} />
        <Route path="/schedule" element={<SchedulePage />} />
        <Route path="/lecturers" element={<LecturerPage />} />
      </Routes>
    </Router>
  )
}

export default App
