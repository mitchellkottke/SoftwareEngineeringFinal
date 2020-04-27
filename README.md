# Final CS4531

## Structure

* **db_stuff** : contains the basic *mongod.conf* file along with a csv containing the baby names and other information associated with them
* **server_stuff** : contains a basic npm initialized folder with the **package.json** and some packages you might find useful to utilize for the project. Remember ```npm install``` to download the packages before running
* **android_stuff** : simple android project with base code for you to build off of. Contains a MainActivity and the gradle build packages for retrofit, firebase, and google services

## About

This project constitutes the final for Software Engineering. This is not meant to be a vague project with ambiguous requirements, so we will list them below. As such it is up to you to decide how you would like to implement and structure your code. **<u>Just make it work</u>**. I would suggest you think of this project as a hackathon/proof of concept project , don't sweat the small stuff and just get the grand ideas working. It will make your life much easier.

## Requirements

### Points You Need To Receive 100%: ***250***

### Minimal Features: ***150 Points***

The basic features you need to implement are some mechanism to visualize the names from the database and then like or dislike them. This can be implemented in any way you want. This is the minimal requirements for the project.

### Additional Feature List  

* Authorization : **60 Points**
  * Oauth implementation
  * Self handling of username/passwords
* Visualize names :
  * Sort mode by year, popularity, alphabetically **25 Points**
  * Swipe interactions: left swipe (add to dislikes) right swipe (add to likes) **30 Points**
    * Can also be buttons for like dislike
  * **Alternatively** Rating the names on a scale (arbitrary 1-5/1-10) **35 Points**
  * Detail mode: Long click (or whatever) to pull up an individual name and all of it's related information **40 Points**
  * Add comments to names globally for everyone to see **30 Points**
* Home/(Dis)like page :
  * View likes and dislikes per user (if single user mode just show the one users page and their likes) **35 Points**
  * Sorting of names:
    * Based off user ratings (scale mentioned above) **30 Points**
    * Normal sorting based off database information **20 Points**
  * Add comments to liked/disliked names per user **35 Points**
  * (If multiple users) make sure my likes/dislikes are separate
* Settings Page:
  * Allow user to select alternative methods for visualization/like-dislike/etc. **60 Points**

### The fancier you do something the more points we may allot. The points you see above are minimal points given for just the functionality.
