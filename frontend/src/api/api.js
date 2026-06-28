// Einfache API-Schicht. Vite leitet /api an das Spring-Boot-Backend (Port 8080) weiter.

async function request(path, options = {}) {
  const response = await fetch(path, options);

  if (response.status === 204) {
    return null;
  }
  if (!response.ok) {
    let message = `HTTP ${response.status}`;
    try {
      const data = await response.json();
      message = data.message || message;
    } catch {
      // Standardmeldung behalten
    }
    throw new Error(message);
  }
  return response.json();
}

const jsonOptions = (method, data) => ({
  method,
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify(data),
});

export const gardenApi = {
  getAll: () => request('/api/gardens'),
  getById: (id) => request(`/api/gardens/${id}`),
  create: (data) => request('/api/gardens', jsonOptions('POST', data)),
  update: (id, data) => request(`/api/gardens/${id}`, jsonOptions('PUT', data)),
  delete: (id) => request(`/api/gardens/${id}`, { method: 'DELETE' }),
};

export const plantApi = {
  getAll: () => request('/api/plants'),
  getById: (id) => request(`/api/plants/${id}`),
  getByCategory: (cat) => request(`/api/plants/category/${encodeURIComponent(cat)}`),
  create: (data) => request('/api/plants', jsonOptions('POST', data)),
  update: (id, data) => request(`/api/plants/${id}`, jsonOptions('PUT', data)),
  delete: (id) => request(`/api/plants/${id}`, { method: 'DELETE' }),
};

export const recordApi = {
  getAll: () => request('/api/records'),
  getById: (id) => request(`/api/records/${id}`),
  getByGarden: (gardenId) => request(`/api/records/garden/${gardenId}`),
  getCalendar: () => request('/api/records/calendar'),
  getWatering: (gardenId) => request(`/api/records/garden/${gardenId}/watering`),
  create: (data) => request('/api/records', jsonOptions('POST', data)),
  update: (id, data) => request(`/api/records/${id}`, jsonOptions('PUT', data)),
  delete: (id) => request(`/api/records/${id}`, { method: 'DELETE' }),
};

export const weatherApi = {
  getWeather: (location) => request(`/api/weather?location=${encodeURIComponent(location)}`),
};
