# 🧪 Analyse Médicale - JEE

**Analyse Médicale** est une application web complète permettant d’analyser les symptômes et les marqueurs biologiques d’un patient pour proposer une orientation diagnostique initiale.

> 🎓 Projet réalisé dans le cadre du module JEE à l'**EMSI** par **Yassine Bouchida**.

---

## 📁 Structure du projet

```
Analyse-Medicale-JEE/
├── frontend/            # Application Angular (interface utilisateur)
└── medicalanalysis/     # Application Spring Boot (API REST et logique métier)
```

---

## ⚙️ Technologies utilisées

- **Frontend**
  - Angular 16+
  - TypeScript
  - HTML/CSS
  - Angular Material (si utilisé)
  - jsPDF (export PDF)
  - i18n (internationalisation : 🇫🇷 / 🇬🇧)

- **Backend**
  - Spring Boot 3.x
  - Spring Data JPA
  - RESTful API
  - Maven
  - H2 ou MySQL

---

## 🚀 Lancer le projet

### 🖥️ Backend (Spring Boot)

1. Ouvrir le dossier `medicalanalysis` dans IntelliJ ou VS Code.
2. Lancer `MedicalanalysisApplication.java`.
3. Accéder à l’API sur : `http://localhost:8080`

#### Exemple de configuration (H2) :
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
```

---

### 🌐 Frontend (Angular)

1. Aller dans le dossier :
   ```bash
   cd frontend
   ```

2. Installer les dépendances :
   ```bash
   npm install
   ```

3. Lancer l’application :
   ```bash
   ng serve
   ```

4. Ouvrir [http://localhost:4200](http://localhost:4200) dans le navigateur.

---

## ✨ Fonctionnalités principales

- 📋 Formulaire complet patient (taille, poids, sexe…)
- 🧾 Ajout dynamique des symptômes
- 🧬 Marqueurs biologiques personnalisés
- 🔍 Analyse automatisée + suggestion de diagnostic
- 🖨️ Génération PDF de l’analyse (via jsPDF)
- 🌙 Mode sombre / clair
- 🌍 Interface multilingue (Français / Anglais)
- 🧑‍⚕️ Signature médecin dans le PDF

---

## 🔒 Améliorations prévues

- Authentification utilisateur (admin / médecin)
- Historique des analyses
- Déploiement en ligne (via Firebase / Vercel / Render)
- Tests unitaires (Jest / JUnit)

---

## 📸 Aperçu (bientôt disponible)

> Ajoutez ici des captures d’écran ou GIFs du formulaire, du résultat et du PDF.

---

## 👨‍💻 Auteur

- **Yassine Bouchida**  
  Étudiant en 4eme annee- G8– EMSI  
  [github.com/Yassinebouchida](https://github.com/Yassinebouchida)

---

## 📚 Licence

Projet académique réalisé à des fins pédagogiques. Reproduction partielle autorisée avec mention.
