# Health_over_wealth
A team-of-two Android Kotlin app used to track user's physical activity as well as some of the biometrics. It utilises Kotlin, Firebase, Android biometric sensors and MPAndroid Charts.

### Login Screen
This activity features a login form connected to the Firebase user DB. After "Log in" is clicked, the app connects with Firebase in order to verify the data inputted by the user.
If everything is correct, the user is being redirected to the app's main screen. If not, the user can set up a new account using the Registration form. During the registration proccess, user credentials get verified:
- the app checks if there is no prevoius database occurence of the email address inputted by the user
- the password's strength is being estimated and presented to the user via the "strength bar"


![image](https://user-images.githubusercontent.com/61741336/124314556-dcacd100-db72-11eb-991e-21a0908fe07c.png)
