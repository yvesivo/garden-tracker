import { Link, useLocation } from 'react-router-dom';

export default function Navbar() {
  const { pathname } = useLocation();
  const linkClass = (path) =>
    'nav-link' + (pathname === path ? ' active' : '');

  return (
    <nav className="navbar">
      <Link to="/" className="navbar-brand">🌱 GartenTracker</Link>
      <Link to="/" className={linkClass('/')}>Dashboard</Link>
      <Link to="/gardens" className={linkClass('/gardens')}>Gärten</Link>
      <Link to="/plants" className={linkClass('/plants')}>Pflanzen</Link>
    </nav>
  );
}
