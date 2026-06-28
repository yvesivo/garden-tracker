import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';
import Dashboard from './pages/Dashboard.jsx';
import GardenList from './pages/GardenList.jsx';
import GardenForm from './pages/GardenForm.jsx';
import GardenDetail from './pages/GardenDetail.jsx';
import PlantCatalog from './pages/PlantCatalog.jsx';
import PlantForm from './pages/PlantForm.jsx';
import PlantRecordForm from './pages/PlantRecordForm.jsx';

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
          <Route path="/gardens/:id" element={<GardenDetail />} />
          <Route path="/plants" element={<PlantCatalog />} />
          <Route path="/plants/new" element={<PlantForm />} />
          <Route path="/plants/:id/edit" element={<PlantForm />} />
          <Route path="/records/new" element={<PlantRecordForm />} />
          <Route path="/records/:id/edit" element={<PlantRecordForm />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </div>
  );
}
