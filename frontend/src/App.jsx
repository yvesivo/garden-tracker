import { Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar.jsx';

function Home() {
  return (
    <div className="card">
      <h1>🌱 GartenTracker</h1>
      <p className="muted">
        Verwalte deine Hochbeete, Pflanzen und Pflanzeinträge.
        Das Frontend wird gerade aufgebaut – weitere Seiten folgen.
      </p>
    </div>
  );
}

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
          <Route path="/" element={<Home />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </div>
  );
}
