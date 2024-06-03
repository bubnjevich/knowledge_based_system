import requests
from datetime import datetime, timedelta

url = 'http://localhost:8080/api/weather-conditions'


temperatures = [33.0, 32.0, 28.9, 14.0, 12.0]
precipitations = [2.0, 4.0, 5.0, 1.0, 2.0]
date_list = [datetime.now() - timedelta(days=i) for i in range(5)]


for date, temperature, precipitation in zip(date_list, temperatures, precipitations):
    data = {
        'measurementDate': date.isoformat(),
        'temperature': temperature,
        'precipitation': precipitation
    }
    
    response = requests.post(url, json=data)
    
    if response.status_code == 200:
        print(f"Zahtev za datum {date.date()} uspešno poslat!")
    else:
        print(f"Došlo je do greške prilikom slanja zahteva za datum {date.date()}: {response.status_code}")


if __name__ == "__main__":
    pass