import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import Dashboard from './pages/Dashboard.jsx';
import GardenList from './pages/GardenList.jsx';
import GardenForm from './pages/GardenForm.jsx';

function NotFound() {
  return (
    <div className="card">
      <h2>Seite nicht gefunden</h2>
      <p className="muted">Diese Seite gibt es (noch) nicht.</p>
    </div>
  );
}

export default function App() {
  return (
    <div className="app-layout">
      <Navbar />
      <main className="main-content">
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/gardens" element={<GardenList />} />
          <Route path="/gardens/new" element={<GardenForm />} />
          <Route path="/gardens/:id/edit" element={<GardenForm />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </div>
  );
}
