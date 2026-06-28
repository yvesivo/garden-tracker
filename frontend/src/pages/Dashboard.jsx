import { useEffect, useState } from 'react';
import { gardenApi, plantApi, recordApi } from '../api/api.js';

export default function Dashboard() {
  const [gardens, setGardens] = useState([]);
  const [plantCount, setPlantCount] = useState(0);
  const [recordCount, setRecordCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    Promise.all([gardenApi.getAll(), plantApi.getAll(), recordApi.getAll()])
      .then(([g, p, r]) => {
        setGardens(g);
        setPlantCount(p.length);
        setRecordCount(r.length);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <p className="muted">Lade Daten…</p>;
  }

  if (error) {
    return (
      <div className="card">
        <h2>Fehler beim Laden</h2>
        <p>{error}</p>
        <p className="muted">Läuft das Backend auf http://localhost:8080?</p>
      </div>
    );
  }

  return (
    <div>
      <h1>Dashboard</h1>

      <div className="stat-row">
        <div className="card stat">
          <div className="stat-value">{gardens.length}</div>
          <div className="stat-label">Gärten</div>
        </div>
        <div className="card stat">
          <div className="stat-value">{plantCount}</div>
          <div className="stat-label">Pflanzen</div>
        </div>
        <div className="card stat">
          <div className="stat-value">{recordCount}</div>
          <div className="stat-label">Einträge</div>
        </div>
      </div>

      <h2>Meine Gärten</h2>
      {gardens.length === 0 ? (
        <p className="muted">Noch keine Gärten angelegt.</p>
      ) : (
        <div className="card-grid">
          {gardens.map((g) => (
            <div className="card" key={g.id}>
              <h3 style={{ marginTop: 0 }}>{g.name}</h3>
              <p className="muted">{g.location}</p>
              <p>{g.plantRecordCount} Einträge</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
