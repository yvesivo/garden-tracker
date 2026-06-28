import { useEffect, useState } from 'react';
import { useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { gardenApi, plantApi, recordApi } from '../api/api.js';

const STATUSES = ['PLANNED', 'GROWING', 'HARVESTED'];
const EMPTY = { gardenId: '', plantId: '', plantingDate: '', status: 'PLANNED', notes: '' };

export default function PlantRecordForm() {
  const { id } = useParams();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const editing = Boolean(id);

  const [gardens, setGardens] = useState([]);
  const [plants, setPlants] = useState([]);
  const [form, setForm] = useState(EMPTY);
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    Promise.all([gardenApi.getAll(), plantApi.getAll()])
      .then(([g, p]) => {
        setGardens(g);
        setPlants(p);
      })
      .catch((e) => setError(e.message));
  }, []);

  useEffect(() => {
    if (editing) {
      recordApi.getById(id)
        .then((r) =>
          setForm({
            gardenId: r.gardenId ?? '',
            plantId: r.plantId ?? '',
            plantingDate: r.plantingDate ?? '',
            status: r.status ?? 'PLANNED',
            notes: r.notes ?? '',
          }),
        )
        .catch((e) => setError(e.message));
    } else {
      const g = searchParams.get('garden');
      if (g) setForm((f) => ({ ...f, gardenId: g }));
    }
  }, [id, editing, searchParams]);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const submit = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError(null);
    const payload = {
      gardenId: parseInt(form.gardenId, 10),
      plantId: parseInt(form.plantId, 10),
      plantingDate: form.plantingDate || null,
      status: form.status,
      notes: form.notes,
    };
    try {
      const saved = editing ? await recordApi.update(id, payload) : await recordApi.create(payload);
      navigate(`/gardens/${saved.gardenId}`);
    } catch (err) {
      setError(err.message);
      setSaving(false);
    }
  };

  return (
    <div className="card" style={{ maxWidth: 520 }}>
      <h1>{editing ? 'Eintrag bearbeiten' : 'Neuer Pflanzeintrag'}</h1>
      {error && <p style={{ color: '#c62828' }}>{error}</p>}
      <form onSubmit={submit}>
        <div className="form-group">
          <label>Garten</label>
          <select name="gardenId" value={form.gardenId} onChange={change} required>
            <option value="">– wählen –</option>
            {gardens.map((g) => <option key={g.id} value={g.id}>{g.name}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label>Pflanze</label>
          <select name="plantId" value={form.plantId} onChange={change} required>
            <option value="">– wählen –</option>
            {plants.map((p) => <option key={p.id} value={p.id}>{p.name}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label>Pflanzdatum</label>
          <input name="plantingDate" type="date" value={form.plantingDate} onChange={change} />
        </div>
        <div className="form-group">
          <label>Status</label>
          <select name="status" value={form.status} onChange={change}>
            {STATUSES.map((s) => <option key={s} value={s}>{s}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label>Notizen</label>
          <textarea name="notes" rows={3} value={form.notes} onChange={change} />
        </div>
        <button type="submit" className="btn" disabled={saving}>
          {saving ? 'Speichern…' : 'Speichern'}
        </button>
      </form>
    </div>
  );
}
