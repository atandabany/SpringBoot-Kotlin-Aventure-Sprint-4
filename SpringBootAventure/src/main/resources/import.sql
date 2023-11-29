insert into qualite (bonus_qualite, couleur, nom) values (0,'grey','Commun'),(1,'#00FFFFFF','Rare'),(2,'#FF69B4FF','Epique'),(3,'#FFA500FF','Légendaire');
insert into type_arme (activation_critique, multiplicateur_critique, valeur_de_max, nombre_des, nom) values (18,3,4,1,'Dague'),(20,2,6,1,'Bâton'),(20,3,8,1,'Lance'),(19,2,8,1,'Arbalète Légère'),(19,2,6,1,'Epée Courte'),(20,3,8,1,'Hache'),(19,2,8,1,'Epée Longue'),(20,3,8,1,'Marteau de Guerre'),(20,3,8,1,'Arc');
insert into type_armure (bonus_type,nom) values (0,'Rembourré'),(1,'Cuir'),(2,'Cuir Clouté'),(3,'Chemise Chaîne'),(4,'Pectoral'),(5,'Cotte de Mailles');
insert into type_accessoire (nom, bonus_type) values ('Bague','Attaque'),('Anneau','Défense'),('Collier','Vitesse'),('Pendentif','Endurance');
insert into item(qualite_id,type_arme_id,discriminateur,nom,description,chemin_image) values (2,7,'Arme','Epée Longue','Une épée en fer froid',''),(1,3,'Arme','Le Manteau de la nuit','Une armure en cuir obscure comme la nuit',''),(4,8,'Arme','Marteau Tonnerre','Un marteau légendaire qui frappe comme la foudre',''),(2,9,'Arme','Arc Long','Un arc crée pour tuer les humains','');
insert into item(qualite_id,type_armure_id,discriminateur,nom,description,chemin_image) values (2,6,'Armure','Cotte de Mailles en Adamantine','Cotte de mailles plus lourde mais aussi plus solide',''),(3,2,'Armure','Le Manteau de la Nuit','Une armure en cuir obscure comme la nuit.',''),(1,2,'Armure','Armure du Gobelin','Armure en cuir rudimentaire','');
insert into item(qualite_id,type_accessoire_id,discriminateur,nom,description,chemin_image) values (1,2,'Accessoire','Anneau du Héros','Anneau qui coute cher',''),(2,1,'Accessoire','Bague du Roi Dragon','Héritage du Dieu Dragon',''),(3,3,'Accessoire','Anneau du Gobelin',' Anneau du Garde Forestier',''),(4,4,'Accessoire','Collier de la Reine des Elfes','Apparu dans une nuit de pleine lune après la découverte de la Reine des Elfes',''),(3,3,'Accessoire','Anneau Simple ',' Anneau du Garde Forestier','');
insert into role (nom) values ('admin');
insert into role (nom) values ('joueur');
insert into utilisateur ( email, mdp) values ('admin@email.com','$2a$10$MJn5SDUCgsm3XEyvELGQI.lcCzCXtxhA8hunr9jX9yvDd6/FkjxYO');
insert into personne_role (role_id, personne_id) VALUES (1,1), (2,1);
-- Monstre 1
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Goblin', 10, 5, 20, 15, 70,1);
-- Monstre 2
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Dragon Rouge', 50, 30, 100, 40, 150,1);
-- Monstre 3
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Sorcier des Ombres', 25, 15, 60, 25, 100,1);
-- Monstre 4
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie, utilisateur_id) VALUES ('Orc Berserker', 35, 20, 80, 30, 120,1);
-- Monstre 5
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie, utilisateur_id) VALUES ('Liche Maléfique', 40, 25, 90, 35, 110,1);
-- Monstre 6
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Chimère Élémentaire', 55, 35, 120, 45, 180,1);
-- Hero 1
INSERT INTO personnage (nom, attaque, defense, endurance, vitesse, point_de_vie,utilisateur_id) VALUES ('Paladin', 37, 45, 75, 30, 110,1);


INSERT INTO campagne (nom,  dernier_score, meilleur_score, statut, utilisateur_id,hero_id) VALUES ('Campagne1', 0, 0, 'En cours', 1,7);
INSERT INTO campagne (nom,  dernier_score, meilleur_score, statut, utilisateur_id,hero_id) VALUES ('Campagne2',  0, 0, 'En cours', 1,7);


insert into combat (nombre_tours ,est_termine, campagne_id, monstre_id) VALUES (0,false,1,1),(0,false,1,2),(0,false,1,3);
insert into combat (nombre_tours ,est_termine, campagne_id, monstre_id) VALUES (0,false,2,1),(0,false,2,2),(0,false,2,5),(0,false,2,6);

