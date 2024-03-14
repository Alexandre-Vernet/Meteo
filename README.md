# Weather

![icon](https://user-images.githubusercontent.com/72151831/104051425-63967780-51e8-11eb-9e6e-8582d3ead120.png)



## Description
Get current location to see the weather in your city. Also, you can select a city to see the weater too.

## Model

![model](https://user-images.githubusercontent.com/72151831/107120087-cf254080-688b-11eb-8c12-7600e3e063f4.png)



## Requirement
The app need internet connection and current location. If it don't have the access, a dialog would appear to allow it.

![pas_internet](https://user-images.githubusercontent.com/72151831/104051027-c3d8e980-51e7-11eb-9d8a-d34d50fc6c78.jpg)

App can't work on emulator.


## 1st launch
Upon opening, the application will ask for LOCATION permission. If the user declines, the application will automatically retrieve the weather for Paris and suggest allowing location access for proper app functionality.

![permission](https://user-images.githubusercontent.com/72151831/104051128-ecf97a00-51e7-11eb-9107-7d80dc9344a8.jpg)



## How it's work 
The application will check if the device is connected to the internet. If it is, the application will determine the user's location and display the weather for the city they are in. The application retrieves the city name, temperature, minimum and maximum temperatures of the day, humidity level, wind speed, as well as the sunrise and sunset times.

![home](https://user-images.githubusercontent.com/72151831/104054704-dd7d2f80-51ed-11eb-921f-cc358eff54c3.jpg)



## Graph
The AndroidChart library creates a forecast graph for the week, allowing users to predict temperatures for the current week.

![graph](https://user-images.githubusercontent.com/72151831/104054580-a73fb000-51ed-11eb-8f2b-60d2b9bd3130.jpg)



## Menu
A button is present at the bottom right of the screen to display a menu wich allow you to : 
- Retrieve the weather of the city where the device is located.
- Enter a city to retrieve the weather of any city in France (example: Paris).
- Settings to modify temperature units (°C or °F) and wind speed (km/h, mph, knots, etc.). These data will be stored in the phone's memory and reused every time the application is opened and until the next changes. This menu also includes the application version and the names of the developers.

![menu](https://user-images.githubusercontent.com/72151831/104052188-9e4cdf80-51e9-11eb-84ba-8f18394de4df.jpg)

![settings](https://user-images.githubusercontent.com/72151831/104051160-fbe02c80-51e7-11eb-9180-b7768efde4a8.jpg)
![settings-change-unit](https://user-images.githubusercontent.com/72151831/104051166-fd115980-51e7-11eb-946b-ce6d6ef73531.jpg)



## Shortcuts
Shortcuts on the application icon are available to directly launch the "Enter a city" or "Settings" activities.

![shortcuts](https://user-images.githubusercontent.com/72151831/104051071-d6532300-51e7-11eb-9b4c-c93d8f0c85ab.jpg)



## Widget
The app features its own widget displaying the temperature and either:
- The name of the city where the device was located
- The city entered by the user

Upon clicking the widget, the application launches.

![widget](https://user-images.githubusercontent.com/72151831/104051113-e408a880-51e7-11eb-9116-96978f6c0429.jpg)


## Languages
The application has multiple translation files:
- French
- Spanish
- Italian
- German

The application is capable of recognizing the language used by the device. If the device is configured in one of the languages mentioned above, it will translate each text into the appropriate translation; otherwise, it will choose English as the default language.

![language_deutch](https://user-images.githubusercontent.com/72151831/104055061-67c59380-51ee-11eb-9dc4-9ddb964e4e21.jpg)
![langue_spanish](https://user-images.githubusercontent.com/72151831/104055065-685e2a00-51ee-11eb-9379-f927065e2896.jpg)
![langue_italian](https://user-images.githubusercontent.com/72151831/104055066-685e2a00-51ee-11eb-930e-1af31e06d644.jpg)



## Responsive

![responsive_home_1](https://user-images.githubusercontent.com/72151831/104055339-e6223580-51ee-11eb-8516-a90bcc2d973e.jpg)
![responsive_home_2](https://user-images.githubusercontent.com/72151831/104055340-e6bacc00-51ee-11eb-99c6-c24a36dfed8e.jpg)
![responsive_settings](https://user-images.githubusercontent.com/72151831/104055341-e6bacc00-51ee-11eb-808c-7df7d6d38245.jpg)



