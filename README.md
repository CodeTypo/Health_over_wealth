# Health_over_wealth
A team-of-two Android Kotlin app used to track user's physical activity as well as some of the biometrics. It utilises Kotlin, Firebase, Android biometric sensors and MPAndroid Charts.

### Login Screen
This activity features a login form connected to the Firebase user DB. After "Log in" is clicked, the app connects with Firebase in order to verify the data inputted by the user.
If everything is correct, the user is being redirected to the app's main screen. If not, the user can set up a new account using the Registration form. During the registration proccess, user credentials get verified:
- the app checks if there is no prevoius database occurence of the email address inputted by the user
- the password's strength is being estimated and presented to the user via the "strength bar"

![image](https://user-images.githubusercontent.com/61741336/124314556-dcacd100-db72-11eb-991e-21a0908fe07c.png)

_The app's login / register screens_

### Main screen 

Main screen consists of a few tiles, each one reffering to a different activity. Each tile is a separate Fragment with an individual layout. Tiles are managed by the Fragment manager. Each tile has custom onClickListener interface implemented. The tiles refer to:
 - step tracker
 - bmi calculator
 - drinked water cups tracker
 - user weight history
 - heart rate measurements
   
 ### Step tracker
 
 After clicking on the steps tile, user gets redirected to the separate acitivity consisting of a Chart displaying the weekly steps-made history, and a steps made today counter. If the dayily step goal is made, the commentary below the counter is changed.


![image](https://user-images.githubusercontent.com/61741336/124355696-dec66c80-dc12-11eb-8ce3-1599fb012ae6.png)

_A screenshot presenting the app's main activity and a stepCounter activity which appears after the user clicks on the steps tile_

### BMI checker

This tile is rather simple - it just downloads the user height and mass from the Firebase and calculates the BMI based on the regular formula available online, after clicking on the tile, it displays a short infographic regarding the body mass index information.

### Drank water cups tracker

A tile dedicated to tracking the amount of water cups drank by the user. The amount of water cups drank today can be changed using the "+" and "-" buttons on the water cups tile. A weekly history can be seen using the navbar localised on the bottom. After redirecting to the water cups history, a history in the form of RecyclerView is being displayed. If the user didn't manage to meet the goal, a dessert image is being shown next to the associated day of the week. If the user accomplished the goad, a water bottle is being shown.

![image](https://user-images.githubusercontent.com/61741336/124356375-079c3100-dc16-11eb-8bcd-ffa64eba7824.png)

_A screenshot depicting the water cups history before and after setting the amount of water cups drank on wednesday_

### Heart rate tile 

The heart rate tile contains a chart used for live - plotting the currently measured heart rate and a TextView displaying instructions to the user. After a successful measurement, the latest measured heart rate value is being saved to the Firebase.

![image](https://user-images.githubusercontent.com/61741336/124357531-4af99e00-dc1c-11eb-8b14-12ca957d8bed.png)

_A picture showung the heart rate tile before and after the user placed his/her finger on the heart rate sensor._

### Settings 

The app provides the user with posiibility to change his/her password, height and steps/drank water cups accessible via the "Settings" tab on the bottom navbar as well. 

All the input fields are povided with validating mechanism that chceks if the value inputted meets the requirements (for example, the user is not allowed to set his/her water cups target above 20 because its rather unrealistic)

![image](https://user-images.githubusercontent.com/61741336/124357753-729d3600-dc1d-11eb-8891-d5ff5a8f8122.png)

_Changing height via the settings_


![image](https://user-images.githubusercontent.com/61741336/124357791-9fe9e400-dc1d-11eb-9f38-73d4a25c60da.png)

_Password changing attempt_

![image](https://user-images.githubusercontent.com/61741336/124357801-ac6e3c80-dc1d-11eb-9e85-2c19fc11959b.png)

_Changing the water cups goal_

![image](https://user-images.githubusercontent.com/61741336/124357946-4f26bb00-dc1e-11eb-8c6b-97afa55eaeee.png)

_Changing the steps amount target_
