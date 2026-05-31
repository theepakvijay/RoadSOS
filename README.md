🚨 RoadSoS — Emergency Response System

Every Second Saves Lives
A location-based emergency response web application built for the IIT Madras Road Safety Hackathon 2026 organized by CoERS & RBG Labs.

🚦 Problem Statement
During a road accident, victims and bystanders struggle to quickly find nearby emergency services. Critical information about trauma centres, ambulances, police stations, and vehicle rescue services is scattered and hard to access in the moment it is needed most.
Delays in locating these services can cost lives — especially during the Golden Hour, the critical 60-minute window after an accident when timely medical response can save lives.

💡 Solution
RoadSoS provides one-tap access to all emergency services based on the user's live GPS location:

🏥 Hospitals & Trauma Centres
🚑 Ambulance Services
👮 Police Stations
🚗 Towing Services
🔧 Puncture Shops
🏭 Vehicle Service Centres


✨ Features
FeatureDescription📍 Live GPSAuto-captures location from device🚨 One-Tap SOSTriggers full emergency response by severity⏱ ETA EstimationCalculates arrival time based on traffic speeds🗺️ Google MapsTurn-by-turn directions to nearest resource📡 Offline ModelocalStorage cache with 24hr TTL🌍 Multi-CityChennai, Delhi, Mumbai, Bangalore🎯 Priority EngineRanks resources based on accident severity

🏗️ Architecture
Frontend (HTML5 / CSS3 / Vanilla JS)
              ↕ REST API
Spring Boot Backend (Java 17)
    ├── LocationService   ← Haversine distance algorithm
    ├── PriorityService   ← Severity-based ranking
    └── SosService        ← Orchestrates emergency response
              ↕ JPA
       H2 In-Memory DB
       (42 real resources)

🛠️ Tech Stack
Backend:

Java 17
Spring Boot 3.5.14
Spring Data JPA + Hibernate
Spring Security + CORS
H2 In-Memory Database
Apache Maven

Frontend:

HTML5 / CSS3 / Vanilla JavaScript
Browser Geolocation API
localStorage (offline cache)
Google Maps Directions URL


📡 API Endpoints
GET  /api/nearby?lat=13.08&lng=80.27&type=HOSPITAL   → Nearest hospitals
GET  /api/nearby?lat=13.08&lng=80.27                 → All nearby resources
POST /api/sos?lat=13.08&lng=80.27&severity=HIGH      → Trigger SOS
GET  /api/sos/{id}                                   → SOS status
GET  /api/sos/pending                                → All pending SOS
Resource Types:
HOSPITAL AMBULANCE POLICE TRAUMA_CENTRE TOWING_SERVICE PUNCTURE_SHOP SERVICE_CENTRE
Severity Levels:
LOW → Police first | MEDIUM → Ambulance + Hospital | HIGH → Ambulance + Trauma Centre + Police simultaneously

🗄️ Data Coverage
CityResourcesChennai28 resourcesDelhi5 resourcesMumbai5 resourcesBangalore4 resourcesTotal42 resources

🚀 How to Run
Prerequisites

Java 17+
Apache Maven 3.x
Chrome browser (recommended)

Steps
bash# Clone the repository
git clone https://github.com/yourusername/roadsos.git

# Navigate to project
cd roadsos

# Run the application
mvn spring-boot:run

# Open browser
http://localhost:8080
H2 Database Console
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:roadsos
Username: sa
Password: (leave empty)

📁 Project Structure
src/main/java/com/roadsos/
├── RoadsosApplication.java
├── config/
│   └── SecurityConfig.java
├── controller/
│   ├── NearbyResourceController.java
│   └── SosController.java
├── model/
│   ├── EmergencyResource.java
│   ├── ResourceType.java
│   ├── SosRequest.java
│   ├── SeverityLevel.java
│   └── SosStatus.java
├── repository/
│   ├── EmergencyResourceRepository.java
│   └── SosRequestRepository.java
└── service/
    ├── LocationService.java    ← Haversine algorithm
    ├── PriorityService.java    ← Severity ranking
    └── SosService.java         ← Orchestrator

src/main/resources/
├── static/
│   └── index.html              ← Frontend
├── application.properties
└── data.sql                    ← 42 seeded resources

🔮 Future Scope

Accelerometer crash detection — auto-trigger SOS when sudden deceleration detected
Golden Hour countdown — visible 60-min timer from SOS trigger
PostgreSQL + PostGIS — native GPS radius queries
Redis cache — replace localStorage with server-side caching
Kafka — parallel dispatch to ambulance + police + hospital simultaneously
Real-time hospital availability — skip hospitals at capacity


⚠️ Known Limitations

Distance uses Haversine (straight-line) — actual road distance ~20-40% longer
ETA based on average speeds, not real-time traffic
H2 in-memory DB resets on restart
Offline cache limited to 5MB localStorage


🏆 Hackathon
Built for IIT Madras Road Safety Hackathon 2026
Organized by: CoERS (Centre of Excellence for Road Safety) & RBG Labs
Track: RoadSoS — Emergency Response System

👤 Author
Theepak Vijay
Java Backend Developer | TCS
