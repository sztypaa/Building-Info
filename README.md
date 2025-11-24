# Building-Info

• Lokacja to budynek, poziom, lub pomieszczenie

• Budynek może składać się z poziomów a te z pomieszczeń

• Każda lokalizacja jest charakteryzowana przez:

&nbsp;   o id – unikalny identyfikator

&nbsp;  o name – opcjonalna nazwa lokalizacji

• Pomieszczenie dodatkowo jest charakteryzowane przez:

&nbsp;  o area = powierzchnia w m^2

&nbsp;  o cube = kubatura pomieszczenia w m^3

&nbsp;  o heating = poziom zużycia energii ogrzewania (float)

&nbsp;  o light – łączna moc oświetlenia

# Uwaga
Do działania wymagane jest JDK 25.

Ustawienie w IntelliJ IDEA:

File->Project Structure->Project Settings->Project->menu SDK->Add SDK

# Jak wysyłać żądania HTTP

## Windows
W terminalu:
````
(Invoke-RestMethod -Method POST -H @{"Content-Type" = "application/json"} -InFile 'tu_wstaw_ścieżkę_do_pliku_JSON' -Uri http://localhost:8080/createBuilding).Replace('\n', "`n")
````
## Linux
W terminalu:
````
curl -X POST -H "Content-Type: application/json" -d @tu_wstaw_ścieżkę_do_pliku_JSON http://localhost:8080/createBuilding
````

## Przykładowy JSON

````
{
    "name":"building",
    "children":[
        {
        "name":"floor1",
        "children": [
            {"name":"room11"},
            {"name":"room12"}
        ]
        },
        {
        "name":"floor2",
        "children": [
            {"name": "room2a"},
            {}
        ]
        }
    ]
}
````

## Odpowiedź dla przykładu
<pre>
Id: 1 Name: building
├──Id: 2 Name: floor1
│  ├──Id: 3 Name: room11 Area: 0 Volume: 0 Heating: 0.0 Lighting: 0
│  └──Id: 4 Name: room12 Area: 0 Volume: 0 Heating: 0.0 Lighting: 0
└──Id: 5 Name: floor2
   ├──Id: 6 Name: room2a Area: 0 Volume: 0 Heating: 0.0 Lighting: 0
   └──Id: 7 Name:  Area: 0 Volume: 0 Heating: 0.0 Lighting: 0
</pre>
