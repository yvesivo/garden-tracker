const LABELS = { PLANNED: 'Geplant', GROWING: 'Wächst', HARVESTED: 'Geerntet' };
const COLORS = { PLANNED: '#9e9e9e', GROWING: '#4caf50', HARVESTED: '#ff9800' };

export default function StatusBadge({ status }) {
  return (
    <span
      style={{
        display: 'inline-block',
        padding: '0.15rem 0.6rem',
        borderRadius: '999px',
        backgroundColor: COLORS[status] || '#9e9e9e',
        color: '#fff',
        fontSize: '0.8rem',
      }}
    >
      {LABELS[status] || status}
    </span>
  );
}
