import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { plantApi } from '../api/api.js';

export default function PlantCatalog() {
  const [plants, setPlants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    plantApi.getAll()
      .then(setPlants)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="muted">Lade Pflanzen…</p>;
  if (error) return <div className="card"><p>Fehler: {error}</p></div>;

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1 style={{ margin: 0 }}>Pflanzenkatalog</h1>
        <Link to="/plants/new" className="btn">+ Neue Pflanze</Link>
      </div>

      <div className="card-grid">
        {plants.map((p) => (
          <div className="card" key={p.id}>
            <h3 style={{ marginTop: 0 }}>{p.name}</h3>
            <p className="muted" style={{ fontStyle: 'italic' }}>{p.latinName}</p>
            <p>{p.category}{p.daysTillHarvest ? ` · ${p.daysTillHarvest} Tage bis Ernte` : ''}</p>
            <Link to={`/plants/${p.id}/edit`} className="muted">Bearbeiten</Link>
          </div>
        ))}
      </div>
    </div>
  );
}
