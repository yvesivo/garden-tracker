# 🌱 GartenTracker

Hochbeet- und Gemüsegarten-Tracker – Web Engineering II, DHBW Friedrichshafen, SS 2026.

Mit GartenTracker kann man **Gärten/Hochbeete** anlegen, einen **Pflanzenkatalog** pflegen und festhalten, **welche Pflanze wann in welchem Beet** gesetzt wurde. Zu jedem Garten werden über eine externe Wetter-API die **aktuellen Wetterdaten** zum Standort angezeigt.

## Architektur

```
┌──────────┐    REST/JSON   ┌──────────────┐    HTTP    ┌─────────────────┐
│ FRONTEND │ ◄────────────► │   BACKEND    │ ◄────────► │  OpenWeatherMap │
│ (React)  │                │ (Spring Boot)│            │   (externe API) │
└──────────┘                │      │       │            └─────────────────┘
                            │   ┌──▼──┐    │
                            │   │ H2  │    │
                            │   └─────┘    │
                            └──────────────┘
```

- **Frontend (React + Vite):** kommuniziert ausschließlich über die REST-API mit dem Backend.
- **Backend (Spring Boot):** REST-API + Geschäftslogik (Controller → Service → Repository), Persistenz via Spring Data JPA.
- **Datenbank:** H2 (In-Memory, mit Beispieldaten beim Start).
- **Drittanbieter:** OpenWeatherMap liefert Wetterdaten, die das Backend verarbeitet (u.a. für eine Gießempfehlung).

## Tech-Stack

| Komponente   | Technologie                                  |
|--------------|----------------------------------------------|
| Backend      | Java 21, Spring Boot 3.5, Spring Data JPA, Bean Validation |
| Datenbank    | H2 (In-Memory)                               |
| Build        | Maven (Wrapper enthalten)                    |
| Frontend     | React 19, Vite, React Router                 |
| API-Doku     | OpenAPI / Swagger UI (springdoc)             |
| Tests        | JUnit 5, Mockito, Spring MockMvc             |
| DevOps       | Docker, Docker Compose, GitHub Actions       |

## Funktionen

- CRUD für **Gärten**, **Pflanzen** und **Pflanzeinträgen** (drei verbundene Entitäten, OneToMany)
- Eingabevalidierung (Bean Validation) mit sinnvollen Fehlermeldungen und HTTP-Statuscodes
- Anbindung der **OpenWeatherMap-API** inkl. Fallback auf Demo-Daten ohne API-Key
- Aktuelles Wetter zum Garten-Standort im Frontend
- OpenAPI/Swagger-Dokumentation aller Endpunkte

## Voraussetzungen

- Java 21
- Node.js 20+ und npm
- (optional) ein kostenloser API-Key von [openweathermap.org](https://openweathermap.org/api)

## Lokal starten

### Backend
```bash
cd backend
# optional: echten Wetter-Key setzen (sonst Demo-Daten)
#   Windows (cmd):       set OPENWEATHER_API_KEY=dein_key
#   Windows (PowerShell):$env:OPENWEATHER_API_KEY="dein_key"
#   Linux/macOS:         export OPENWEATHER_API_KEY=dein_key
./mvnw spring-boot:run
```
Das Backend läuft auf **http://localhost:8080**.

- Swagger UI: http://localhost:8080/swagger-ui.html
- H2-Konsole: http://localhost:8080/h2-console (JDBC-URL `jdbc:h2:mem:gardendb`, User `sa`, kein Passwort)

> Ohne `OPENWEATHER_API_KEY` liefert der Wetterservice automatisch Demo-Daten – die App ist also auch ohne Key voll lauffähig.

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Das Frontend läuft auf **http://localhost:5173** und leitet `/api`-Anfragen per Vite-Proxy ans Backend weiter.

### Mit Docker (alles auf einmal)
```bash
# optional vorher OPENWEATHER_API_KEY als Umgebungsvariable setzen
docker compose up --build
```
Frontend dann unter **http://localhost:5173**, Backend unter **http://localhost:8080**.

## Tests

```bash
cd backend
./mvnw test
```
Enthält Unit-Tests (Services, mit Mockito) und Web-Layer-Tests (Controller, mit MockMvc).

## Drittanbieter-API

[OpenWeatherMap](https://openweathermap.org/api) – Current Weather & Forecast.
Das Backend ruft die API im `WeatherService` auf, verarbeitet die Antwort und stellt sie unter `GET /api/weather?location={ort}` bereit. Der Key wird über die Umgebungsvariable `OPENWEATHER_API_KEY` gelesen; ist keiner gesetzt, werden Demo-Daten zurückgegeben.

## Projektstruktur

```
garden-tracker/
├── backend/          # Spring Boot (com.gardentracker: model, repository, service, controller, dto, config, exception)
├── frontend/         # React + Vite (src: pages, components, api)
├── docker-compose.yml
└── README.md
```

## Ausblick / Weiterführende Ideen

Aus Zeitgründen nicht mehr umgesetzt, aber als Erweiterung angedacht:

- **Gießempfehlung im Frontend:** Das Backend liefert auf Basis der Wetterdaten eine Empfehlung (`GET /api/records/garden/{id}/watering`).
- **Kalenderansicht:** Pflanz- und Erntetermine als Kalender. Der Endpunkt `GET /api/records/calendar` existiert bereits im Backend.
- **Visueller Garten-Planer:** Pflanzen per Drag & Drop im Beet platzieren. Die dafür nötigen Grid-Koordinaten (`gridX`/`gridY`) sind im Datenmodell schon vorgesehen.
