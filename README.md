# Mapbook / CS 123 Group A Project

Front End: [Nesh](github.com/nesh96), [Migs](github.com/CriousCat) <br>
Back End: [Rico](github.com/Hikari9), [Kitop](github.com/christofferkho)

## Pushing Conventions

1. Fork repository to your account. You can do so by clicking "Fork" in the web interface.
2. Clone your own fork into your desktop. Download [GitHub for Desktop](https://desktop.github.com/) first, then click "Clone".
3. Create your own branch in that fork. Open Git Shell and type `git checkout -b <taskname>`
4. Perform your changes, push, then commit to your local fork.
5. Create a pull request [here](https://github.com/Hikari9/mapbook/compare) and let your front/back end partner merge your changes. 

Avoid pushing directly to master branch. It is advisable to create a *pull request* first to be approved by partner programmer so as to avoid disruption of the system.

## Steps to Push

```
git add -all
git commit -m "My commit message."
git push
```

## Sprint Backlog

Database for location
- [x] Create ERD
- [x] Create table(s) for SQLite
- [ ] Create table(s) for GIS
- [x] Create database adapter class for Android
- [x] Add permissions to manifest xml

Database for OSM user
- [ ] Create ERD
- [ ] Create table(s) for SQLite
- [ ] Create table(s) for GIS
- [ ] Create database adapter class
- [ ] Link to Open Street Maps
- [ ] Add permissions to manifest xml

User Interface
- [ ] Design a layout for menu, buttons, and content
- [x] Create UI for activities
- [ ] Create events in UI widgets to go from one activity to another
- [ ] Create method stub for widgets

Image saving
- [ ] Design a layout for positioning map/images
- [ ] Implement method to capture and save pictures in a device's storage
- [ ] Allow images to appear in the information creator screen

Location Content
- [ ] Automate GPS / Geolocation saving
- [ ] Implement methods to save input information to database
- [ ] Implement networking to save SQLite information to GIS online database

Location Logbook
- [ ] Design List view for each log, layout for picture, location name, contact information
- [ ] Implement a search function for logs by online users

Map
- [ ] Design markers for location coordinates
- [ ] Add markers to map according to location coordinates (online/offline?)
- [ ] Add tooltip snippets for selected markers and integrate info from database
- [ ] Provide settings on map topology (powered by Google Maps or OSM)



