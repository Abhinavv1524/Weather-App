# Weather Application ☁️

This Android app fetches and displays real-time weather data for a specified city using the OpenWeatherMap API. The user can input a city name, and the app will retrieve the current temperature and weather description.

## Features

- Enter a city name to get real-time weather information.
- Displays temperature (in Celsius) and a brief weather description.
- User-friendly interface for fetching weather data.

## 🚀 About Me

🔭 I’m currently working on My Data Science Skills.
🌱 I’m currently learning Data Analytics, Machine Learning.
💬 Ask me about Front-end Development, React, Python, Databases.

## Run Locally

Clone the project

```bash
  git clone https://github.com/Abhinavv1524/Weather-App.git
```

Go to the project directory

```bash
  cd Weather-App
```

Go to the Java main file

```bash
  cd app/src/main/java/com/example/weather/MainActivity.java
```

Create a gradle.properties file (or modify the existing one) in the android directory and add your API key:

```bash
  WEATHER_API_KEY="your_api_key_here"

```

## API Reference

#### Get current weather for a city

```http
  GET /data/2.5/weather?q={city}&appid={api_key}
```

| Parameter | Type     | Description                               |
| :-------- | :------- | :---------------------------------------- |
| `city`    | `string` | **Required**. Name of the city            |
| `api_key` | `string` | **Required**. Your OpenWeatherMap API key |

#### Get item

```http
  GET /data/2.5/weather?q=London&appid=your_api_key

```
