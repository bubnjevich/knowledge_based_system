import requests
from datetime import datetime, timedelta

url = 'http://localhost:8080/api/weather-conditions'


temperatures = [38.0, 34.0, 29.9, 26.0, 34.0, 27.5]
precipitations = [2.0, 1.0, 2.0, 1.0, 6.0, 2.2]

temperaturesSowing = [21.0, 22.0, 19.9, 26.0, 14.0, 26.0]
precipitationsSowing = [20.0, 10.0, 70.0, 50.0, 40.0, 30.0]



def send_request(T, P, month, day):
    date_list = [datetime(2024, month, day) + timedelta(days=i) for i in range(6)]

    for date, temperature, precipitation in zip(date_list, T, P):
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
    while True:
        month = int(input("Month: "))
        day = int(input("Day: "))
        e = input("s for optimal conditions >> ")
        if e == "s":
            send_request(temperaturesSowing, precipitationsSowing, month, day)
        else:
            send_request(temperatures, precipitations, month, day)
