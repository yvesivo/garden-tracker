import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { gardenApi } from '../api/api.js';

const EMPTY = { name: '', location: '', description: '', widthMeters: '', heightMeters: '' };

export default function GardenForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const editing = Boolean(id);

  const [form, setForm] = useState(EMPTY);
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (editing) {
      gardenApi.getById(id)
        .then((g) =>
          setForm({
            name: g.name ?? '',
            location: g.location ?? '',
            description: g.description ?? '',
            widthMeters: g.widthMeters ?? '',
            heightMeters: g.heightMeters ?? '',
          }),
        )
        .catch((e) => setError(e.message));
    }
  }, [id, editing]);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);
    const payload = {
      name: form.name,
      location: form.location,
      description: form.description,
      widthMeters: parseFloat(form.widthMeters) || 0,
      heightMeters: parseFloat(form.heightMeters) || 0,
    };
    try {
      const saved = editing ? await gardenApi.update(id, payload) : await gardenApi.create(payload);
      navigate(`/gardens/${saved.id}`);
    } catch (err) {
      setError(err.message);
      setSaving(false);
    }
  };

  return (
    <div className="card" style={{ maxWidth: 520 }}>
      <h1>{editing ? 'Garten bearbeiten' : 'Neuer Garten'}</h1>
      {error && <p style={{ color: '#c62828' }}>{error}</p>}
      <form onSubmit={submit}>
        <div className="form-group">
          <label>Name</label>
          <input name="name" value={form.name} onChange={change} required />
        </div>
        <div className="form-group">
          <label>Standort</label>
          <input name="location" value={form.location} onChange={change} required />
        </div>
        <div className="form-group">
          <label>Beschreibung</label>
          <textarea name="description" value={form.description} onChange={change} rows={3} />
        </div>
        <div style={{ display: 'flex', gap: '1rem' }}>
          <div className="form-group" style={{ flex: 1 }}>
            <label>Breite (m)</label>
            <input name="widthMeters" type="number" step="0.1" value={form.widthMeters} onChange={change} />
          </div>
          <div className="form-group" style={{ flex: 1 }}>
            <label>Höhe (m)</label>
            <input name="heightMeters" type="number" step="0.1" value={form.heightMeters} onChange={change} />
          </div>
        </div>
        <button type="submit" className="btn" disabled={saving}>
          {saving ? 'Speichern…' : 'Speichern'}
        </button>
      </form>
    </div>
  );
}
