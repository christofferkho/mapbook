# Mapbook / CS 123 Group A Project

## Members

Front End
- [Nesh Purswani](https://github.com/nesh96)
- [Migs Ramos](https://github.com/CriousCat)

Back End
- [Rico Tiongson](https://github.com/Hikari9)
- [Kitop Kho](https://github.com/christofferkho)


## Setting Up

1. Fork repository to your account. You can do so by clicking "Fork" above in this website.
2. Clone your own fork into your desktop. Download [GitHub for Desktop](https://desktop.github.com/).
3. Click the "Clone" button in GitHub app and choose *username/mapbook*. You may also clone using the Git Shell or Terminal. Type `git clone` and change `YOUR-USERNAME` with your GitHub username.
  ````sh
  $ git clone https://github.com/YOUR-USERNAME/mapbook
  
  	Cloning into 'mapbook'...
  	remote: Counting objects: 7, done.
  	remote: Compressing objects: 100% (7/7), done.
  	remove: Total 7 (delta 1), reused 0 (delta 1)
  	Unpacking objects: 100% (7/7), done.
  ````
4. Set-up the fork of your remote using Git Shell or Terminal. Type `git remote -v` to see your current configuration.
  ```sh
  $ git remote -v
  
  	origin  https://github.com/YOUR-USERNAME/mapbook.git (fetch)
  	origin  https://github.com/YOUR-USERNAME/mapbook.git (push)
  ```
5. Type `git remote add upstream` to add the *original* repository to your remotes. Note that the original is from Hikari9. It will look like this:
  ```sh
  $ git remote add upstream https://github.com/Hikari9/mapbook.git
  ```
6. Verify your remotes by typing `git remote -v` again. It should look something like this:
  ```sh
  $ git remote -v
  
  	origin    https://github.com/YOUR-USERNAME/mapbook.git (fetch)
  	origin    https://github.com/YOUR-USERNAME/mapbook.git (push)
  	upstream  https://github.com/Hikari9/mapbook.git (fetch)
  	upstream  https://github.com/Hikari9/mapbook.git (push)
  ```
  
For technicalities with regards to setting up, see https://help.github.com/articles/fork-a-repo.


## Updating your Fork

Your local fork will not be up-to-date unless you want it to. You may want to update your fork whenever other members pushed a change to the repository.

1. Check if you need to update your fork. You may do so by typing `git status`.
  ```sh
  $ git status
  
  	On branch master
  	Your branch is behind 'upstream/master' by [X] commits, and can be fast-forwarded.
  	  (use "git pull" to update your local branch)
  ```
2. Sync from the upstream remote by typing `git pull`.
  ```sh
  $ git pull upstream master
  
  	* branch          master	-> FETCH_HEAD
  	* [new branch]    master	-> upstream/master
  	Already up-to-date
  ```
3. Your changes should already reflect in your Git application.


## Pushing Changes to Repository

Changes within the group repository should be something everyone should verify. Since it is pretty risky to push changes to the original repository, it is recommended to apply your changes in your own fork, and make your fellow developer check your work.

1. Create your own branch in that fork. Type `git checkout -b <branchname>`.
  ```sh
  $ git checkout -b <branchname>
  
  	Switched to a new branch '<branchname>'.
  ```
2. Perform your changes, add, then commit them either through Git application (Commit button), or through the Terminal by typing `git commit -am`.
  ```sh
  $ git commit -am "Commit message."
  
  	[branchname 2sda3f3] Commit message.
  	create mode 100644 Commit message.
  	 1 file changed, 98 insertions(+), 13 deletions(-).
  ```
  Alternative:
  ```sh
  $ git add --all
  $ git commit -m "Commit message."
  
  	[branchname 2sda3f3] Commit message.
  	create mode 100644 Commit message.
  	 1 file changed, 98 insertions(+), 13 deletions(-).
  ```
3. Push your changes in your local fork through the Git application (Sync), or through the Terminal by typing `git push --set-upstream origin branchname`.
  ```
  $ git push --set-upstream origin branchname
  	
  	Counting objects: 7, done.
  	Delta compression using up to 2 threads.
  	Compressing objects: 100% (3/3), done.
  	Writing objects: 100%
  	Total 3 (delta 1), reused 0 (delta 0)
  	To https://github.com/YOUR-USERNAME/mapbook.git
  		??????..2sda3f3		master -> master
  ```
4. Create a pull request throught the Git application or [here](https://github.com/Hikari9/mapbook/compare) and let your front/back end partner merge your changes.

###### Avoid pushing directly to master branch. It is advisable to create a *pull request* first to be approved by partner programmer so as to avoid disruption of the system.


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
