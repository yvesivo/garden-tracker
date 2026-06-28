import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { gardenApi, recordApi } from '../api/api.js';
import StatusBadge from '../components/StatusBadge.jsx';
import WeatherWidget from '../components/WeatherWidget.jsx';

export default function GardenDetail() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [garden, setGarden] = useState(null);
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    Promise.all([gardenApi.getById(id), recordApi.getByGarden(id)])
      .then(([g, r]) => {
        setGarden(g);
        setRecords(r);
      })
      .catch((e) => setError(e.message))
      .finally(() => setLoading(false));
  }, [id]);

  const remove = async () => {
    if (!window.confirm('Diesen Garten wirklich löschen?')) return;
    try {
      await gardenApi.delete(id);
      navigate('/gardens');
    } catch (e) {
      setError(e.message);
    }
  };

  if (loading) return <p className="muted">Lade…</p>;
  if (error) return <div className="card"><p>Fehler: {error}</p></div>;
  if (!garden) return null;

  return (
    <div>
      <Link to="/gardens" className="muted">← Zurück zur Übersicht</Link>

      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', margin: '0.5rem 0 1rem' }}>
        <h1 style={{ margin: 0 }}>{garden.name}</h1>
        <div style={{ display: 'flex', gap: '0.5rem' }}>
          <Link to={`/gardens/${id}/edit`} className="btn">Bearbeiten</Link>
          <button className="btn" style={{ backgroundColor: '#c62828' }} onClick={remove}>Löschen</button>
        </div>
      </div>

      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <p className="muted">{garden.location} · {garden.widthMeters} × {garden.heightMeters} m</p>
        {garden.description && <p>{garden.description}</p>}
      </div>

      <WeatherWidget location={garden.location} />

      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h2 style={{ margin: 0 }}>Pflanzeinträge</h2>
        <Link to={`/records/new?garden=${id}`} className="btn">+ Eintrag</Link>
      </div>

      {records.length === 0 ? (
        <p className="muted">Noch keine Einträge in diesem Beet.</p>
      ) : (
        <div className="card-grid">
          {records.map((r) => (
            <div className="card" key={r.id}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h3 style={{ margin: 0 }}>{r.plantName}</h3>
                <StatusBadge status={r.status} />
              </div>
              <p className="muted">Gepflanzt: {r.plantingDate || '—'}</p>
              {r.notes && <p>{r.notes}</p>}
              <Link to={`/records/${r.id}/edit`} className="muted">Bearbeiten</Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
