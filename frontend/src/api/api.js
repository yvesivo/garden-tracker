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

export const gardenApi = {
  getAll: () => request('/api/gardens'),
  getById: (id) => request(`/api/gardens/${id}`),
};

export const plantApi = {
  getAll: () => request('/api/plants'),
};

export const recordApi = {
  getAll: () => request('/api/records'),
};
