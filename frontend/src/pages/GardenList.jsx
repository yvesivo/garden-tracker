import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { gardenApi } from '../api/api.js';

export default function GardenList() {
  const [gardens, setGardens] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    gardenApi.getAll()
      .then(setGardens)
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="muted">Lade Gärten…</p>;
  if (error) return <div className="card"><p>Fehler: {error}</p></div>;

  return (
    <div>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h1 style={{ margin: 0 }}>Gärten</h1>
        <Link to="/gardens/new" className="btn">+ Neuer Garten</Link>
      </div>

      {gardens.length === 0 ? (
        <p className="muted">Noch keine Gärten – lege deinen ersten an!</p>
      ) : (
        <div className="card-grid">
          {gardens.map((g) => (
            <Link to={`/gardens/${g.id}`} className="card" key={g.id} style={{ color: 'inherit' }}>
              <h3 style={{ marginTop: 0 }}>{g.name}</h3>
              <p className="muted">{g.location}</p>
              <p>{g.widthMeters} × {g.heightMeters} m · {g.plantRecordCount} Einträge</p>
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
