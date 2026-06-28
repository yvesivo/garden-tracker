import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { plantApi } from '../api/api.js';

const CATEGORIES = ['GEMÜSE', 'OBST', 'KRÄUTER'];
const EMPTY = {
  name: '', latinName: '', category: 'GEMÜSE',
  daysTillHarvest: '', spacingCm: '', wateringIntervalDays: '', description: '',
};

const toIntOrNull = (v) => (v === '' || v == null ? null : parseInt(v, 10));

export default function PlantForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const editing = Boolean(id);

  const [form, setForm] = useState(EMPTY);
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    if (editing) {
      plantApi.getById(id)
        .then((p) =>
          setForm({
            name: p.name ?? '',
            latinName: p.latinName ?? '',
            category: p.category ?? 'GEMÜSE',
            daysTillHarvest: p.daysTillHarvest ?? '',
            spacingCm: p.spacingCm ?? '',
            wateringIntervalDays: p.wateringIntervalDays ?? '',
            description: p.description ?? '',
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
      latinName: form.latinName,
      category: form.category,
      daysTillHarvest: toIntOrNull(form.daysTillHarvest),
      spacingCm: toIntOrNull(form.spacingCm),
      wateringIntervalDays: toIntOrNull(form.wateringIntervalDays),
      description: form.description,
    };
    try {
      if (editing) {
        await plantApi.update(id, payload);
      } else {
        await plantApi.create(payload);
      }
      navigate('/plants');
    } catch (err) {
      setError(err.message);
      setSaving(false);
    }
  };

  return (
    <div className="card" style={{ maxWidth: 520 }}>
      <h1>{editing ? 'Pflanze bearbeiten' : 'Neue Pflanze'}</h1>
      {error && <p style={{ color: '#c62828' }}>{error}</p>}
      <form onSubmit={submit}>
        <div className="form-group">
          <label>Name</label>
          <input name="name" value={form.name} onChange={change} required />
        </div>
        <div className="form-group">
          <label>Lateinischer Name</label>
          <input name="latinName" value={form.latinName} onChange={change} />
        </div>
        <div className="form-group">
          <label>Kategorie</label>
          <select name="category" value={form.category} onChange={change}>
            {CATEGORIES.map((c) => <option key={c} value={c}>{c}</option>)}
          </select>
        </div>
        <div style={{ display: 'flex', gap: '1rem' }}>
          <div className="form-group" style={{ flex: 1 }}>
            <label>Tage bis Ernte</label>
            <input name="daysTillHarvest" type="number" value={form.daysTillHarvest} onChange={change} />
          </div>
          <div className="form-group" style={{ flex: 1 }}>
            <label>Abstand (cm)</label>
            <input name="spacingCm" type="number" value={form.spacingCm} onChange={change} />
          </div>
          <div className="form-group" style={{ flex: 1 }}>
            <label>Gießen (Tage)</label>
            <input name="wateringIntervalDays" type="number" value={form.wateringIntervalDays} onChange={change} />
          </div>
        </div>
        <div className="form-group">
          <label>Beschreibung</label>
          <textarea name="description" rows={3} value={form.description} onChange={change} />
        </div>
        <button type="submit" className="btn" disabled={saving}>
          {saving ? 'Speichern…' : 'Speichern'}
        </button>
      </form>
    </div>
  );
}
