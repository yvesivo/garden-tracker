-- Beispielpflanzen: Gemüse
INSERT INTO plants (name, latin_name, category, description) VALUES ('Tomate', 'Solanum lycopersicum', 'GEMÜSE', 'Klassische Gartenpflanze, viele Sorten verfügbar');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Gurke', 'Cucumis sativus', 'GEMÜSE', 'Liebt Wärme und gleichmäßige Feuchtigkeit');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Paprika', 'Capsicum annuum', 'GEMÜSE', 'Braucht viel Sonne und Wärme');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Zucchini', 'Cucurbita pepo', 'GEMÜSE', 'Sehr ertragreich, platzbedürftig');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Karotte', 'Daucus carota', 'GEMÜSE', 'Lockerer Boden für gerades Wachstum nötig');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Salat', 'Lactuca sativa', 'GEMÜSE', 'Schnellwachsend, ideal für Hochbeete');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Radieschen', 'Raphanus sativus', 'GEMÜSE', 'Eine der schnellsten Gemüsepflanzen');

-- Beispielpflanzen: Kräuter
INSERT INTO plants (name, latin_name, category, description) VALUES ('Basilikum', 'Ocimum basilicum', 'KRÄUTER', 'Wärmeliebendes Kraut, ideal für Tomaten');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Petersilie', 'Petroselinum crispum', 'KRÄUTER', 'Zweijährig, reich an Vitaminen');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Schnittlauch', 'Allium schoenoprasum', 'KRÄUTER', 'Mehrjährig, einfach zu pflanzen');

-- Beispielpflanzen: Obst
INSERT INTO plants (name, latin_name, category, description) VALUES ('Erdbeere', 'Fragaria x ananassa', 'OBST', 'Beliebt im Hochbeet, süße Früchte');
INSERT INTO plants (name, latin_name, category, description) VALUES ('Himbeere', 'Rubus idaeus', 'OBST', 'Mehrjährig, braucht Stütze für Ruten');

-- Beispielgärten
INSERT INTO gardens (name, description, location, created_at) VALUES ('Hauptbeet', 'Mein erstes Hochbeet im Garten', 'Berlin', CURRENT_TIMESTAMP);
INSERT INTO gardens (name, description, location, created_at) VALUES ('Terrassenbeet', 'Kräuter und Salat für die Terrasse', 'Berlin', CURRENT_TIMESTAMP);

-- Beispieleinträge
INSERT INTO plant_records (garden_id, plant_id, planting_date, status, notes) VALUES (1, 1, '2026-04-15', 'GROWING', 'Sorte: Cherrytomaten');
INSERT INTO plant_records (garden_id, plant_id, planting_date, status, notes) VALUES (1, 5, '2026-04-10', 'GROWING', 'Tiefes Beet ideal für Karotten');
INSERT INTO plant_records (garden_id, plant_id, planting_date, status, notes) VALUES (2, 8, '2026-03-01', 'PLANNED', 'Basilikum zu den Tomaten setzen');
