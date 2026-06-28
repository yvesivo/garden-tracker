import { useEffect, useState } from 'react';
import { weatherApi } from '../api/api.js';

export default function WeatherWidget({ location }) {
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!location) return;
    weatherApi.getWeather(location)
      .then(setWeather)
      .catch((e) => setError(e.message));
  }, [location]);

  // Widget ist optional – bei Fehler oder ohne Daten einfach nichts anzeigen
  if (error || !weather) return null;

  return (
    <div className="card" style={{ display: 'flex', alignItems: 'center', gap: '1rem', marginBottom: '1.5rem' }}>
      {weather.icon && (
        <img
          src={`https://openweathermap.org/img/wn/${weather.icon}@2x.png`}
          alt={weather.description}
          width="60"
          height="60"
        />
      )}
      <div>
        <div style={{ fontSize: '1.5rem', fontWeight: 700 }}>{Math.round(weather.temperature)}°C</div>
        <div className="muted">{weather.description} · {weather.location}</div>
        <div className="muted" style={{ fontSize: '0.85rem' }}>
          Luftfeuchte {weather.humidity}% · Wind {weather.windSpeed} m/s
        </div>
      </div>
    </div>
  );
}
