# ClearSkiesWeather

Weather forecast + weather station.
Android App written in Kotlin with MVVM architeture.

[![Contributors](https://img.shields.io/github/contributors/mjcarterdev/ClearSkiesWeather?color=blue)](https://github.com/mjcarterdev/ClearSkiesWeather)
[![GitHub last commit (branch)](https://img.shields.io/github/last-commit/mjcarterdev/ClearSkiesWeather/master.svg)](https://github.com/mjcarterdev/ClearSkiesWeather)
[![GitHub license](https://img.shields.io/github/license/mjcarterdev/ClearSkiesWeather)](https://github.com/mjcarterdev/ClearSkiesWeather/blob/master/LICENSE)
[![GitHub issues](https://img.shields.io/github/issues/mjcarterdev/ClearSkiesWeather?color=green)](https://github.com/mjcarterdev/ClearSkiesWeather)
[![GitHub closed issues](https://img.shields.io/github/issues-closed/mjcarterdev/ClearSkiesWeather?color=green&label=issues%20closed)](https://github.com//mjcarterdev/ClearSkiesWeather)
![GitHub commit activity](https://img.shields.io/github/commit-activity/m/mjcarterdev/ClearSkiesWeather)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/mjcarterdev/ClearSkiesWeather?color=green)](https://github.com//mjcarterdev/ClearSkiesWeather)
[![GitHub closed pull requests](https://img.shields.io/github/issues-pr-closed/mjcarterdev/ClearSkiesWeather?color=green&label=closed%20pull%20requests)](https://github.com//mjcarterdev/ClearSkiesWeather)

## Features

- Display the weather based on values collected from sensors
- Display the weather forecast based on user location
- Show 8 days forecast
- Get the current location
- Search weather by city name
- Show sensors statistics

## Tech Stack

- minSdk 26
- targetSdk 30
- Kotlin
- MVVM architecture
- Retrofit
- Material Design
- Room database
- DataBinding
- LiveData
- Singleton Pattern
- Coroutines
- Sensors: temperature, light, humidity, pressure
- Location + geocoding
- Broadcast receiver
- Accessibility checked
- Fragments
- Service
- AnyGraph

## API

- OpenWeather API for weather forecast
- https://openweathermap.org/api

## Run the app

1. Clone the repo https://github.com/mjcarterdev/ClearSkiesWeather.git
2. Create an an account on OpenWeatherMap and generate One Call API key
3. Open project with Android Studio
4. Under App, create folder constants and in it an object file called Constants, Add your API key:

```
// setup your own API KEY
object Constants {
    const val API_KEY = "YOUR API KEY"
}
```

5. Run project with Your mobile or Android Emulator SDK 26 or higher
6. On phone, functionality of weather station is limited to the sensor that the phone has.
   In emulator, sensor activity can be simulated. Graph data will appear after the phone registers a sensor change.

## Demo video
[Demo Video](https://drive.google.com/file/d/1YNMiYIk2Pgb6W_mRfZZZYHrba0iplk-Y/view?usp=sharing)
## Screenshots

<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/splash.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/home_forecast_1.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/home_forecast_2.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/home_sensors_1.png" width="420" />

<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/set_region.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/forecast.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/chart.png" width="420" />
<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/chart2.png" width="420" />

## Presentation

[Presentation](https://docs.google.com/presentation/d/1y17MEiOWuCGaACqPx-doMhmjc9K6md9K/edit?usp=sharing&ouid=100419313399244545656&rtpof=true&sd=true) 

## Project Stucture

<img src="https://github.com/mjcarterdev/ClearSkiesWeather/blob/docs/screenshots/structure.png" width="300" />
