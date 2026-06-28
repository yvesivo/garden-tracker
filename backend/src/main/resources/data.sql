-- Beispielpflanzen: Gemüse
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Tomate', 'Solanum lycopersicum', 'GEMÜSE', 70, 60, 2, 'Klassische Gartenpflanze, viele Sorten verfügbar');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Gurke', 'Cucumis sativus', 'GEMÜSE', 60, 50, 1, 'Liebt Wärme und gleichmäßige Feuchtigkeit');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Paprika', 'Capsicum annuum', 'GEMÜSE', 80, 45, 2, 'Braucht viel Sonne und Wärme');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Zucchini', 'Cucurbita pepo', 'GEMÜSE', 55, 80, 2, 'Sehr ertragreich, platzbedürftig');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Karotte', 'Daucus carota', 'GEMÜSE', 90, 10, 3, 'Lockerer Boden für gerades Wachstum nötig');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Salat', 'Lactuca sativa', 'GEMÜSE', 45, 25, 1, 'Schnellwachsend, ideal für Hochbeete');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Radieschen', 'Raphanus sativus', 'GEMÜSE', 25, 8, 2, 'Eine der schnellsten Gemüsepflanzen');

-- Beispielpflanzen: Kräuter
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Basilikum', 'Ocimum basilicum', 'KRÄUTER', 30, 25, 1, 'Wärmeliebendes Kraut, ideal für Tomaten');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Petersilie', 'Petroselinum crispum', 'KRÄUTER', 60, 20, 2, 'Zweijährig, reich an Vitaminen');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Schnittlauch', 'Allium schoenoprasum', 'KRÄUTER', 60, 15, 3, 'Mehrjährig, einfach zu pflanzen');

-- Beispielpflanzen: Obst
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Erdbeere', 'Fragaria x ananassa', 'OBST', 90, 30, 2, 'Beliebt im Hochbeet, süße Früchte');
INSERT INTO plants (name, latin_name, category, days_till_harvest, spacing_cm, watering_interval_days, description) VALUES ('Himbeere', 'Rubus idaeus', 'OBST', 120, 100, 3, 'Mehrjährig, braucht Stütze für Ruten');

-- Beispielgärten
INSERT INTO gardens (name, description, width_meters, height_meters, location, created_at) VALUES ('Hauptbeet', 'Mein erstes Hochbeet im Garten', 3.0, 1.5, 'Berlin', CURRENT_TIMESTAMP);
INSERT INTO gardens (name, description, width_meters, height_meters, location, created_at) VALUES ('Terrassenbeet', 'Kräuter und Salat für die Terrasse', 1.5, 0.8, 'Berlin', CURRENT_TIMESTAMP);

-- Beispieleinträge
INSERT INTO plant_records (garden_id, plant_id, planting_date, expected_harvest_date, status, grid_x, grid_y, notes) VALUES (1, 1, '2026-04-15', '2026-06-24', 'GROWING', 0, 0, 'Sorte: Cherrytomaten');
INSERT INTO plant_records (garden_id, plant_id, planting_date, expected_harvest_date, status, grid_x, grid_y, notes) VALUES (1, 5, '2026-04-10', '2026-07-09', 'GROWING', 2, 0, 'Tiefes Beet ideal für Karotten');
INSERT INTO plant_records (garden_id, plant_id, planting_date, expected_harvest_date, actual_harvest_date, yield_kg, status, grid_x, grid_y, notes) VALUES (2, 8, '2026-03-01', '2026-03-31', '2026-04-05', 0.3, 'HARVESTED', 0, 0, 'Erste Ernte der Saison');
