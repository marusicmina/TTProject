# Sistem za obradu naloga na berzi

Ovaj projekat predstavlja backend servis za upravljanje berzanskim Order-ima. Korisnicima omogućava sledeće funkcionalnosti:

Kreiranje novih Order-a za kupovinu i prodaju.
Pregled postojećih Order-a.
Prikaz 10 najboljih Order-a za kupovinu i prodaju.
Za kreiranje Order-a, korisnik mora biti prijavljen. Ako korisnik još uvek nema nalog, može se registrovati putem sistema.

Projekat je razvijen koristeći Java Spring Boot i Maven. Kao razvojno okruženje korišćen je Spring Tool Suite 4. Baza podataka je PostgreSQL, a za testiranje je korišćen Postman.

# Pokretanje aplikacije
Da biste pokrenuli aplikaciju na svom računaru, potrebno je da pratite sledeće korake:

1. Instalacija potrebnih alata
   
JDK 17+ (proverite instalaciju komandom: java -version)

Maven (proverite instalaciju komandom: mvn -version)

PostgreSQL baza podataka

3. Konfiguracija baze podataka
   
Instalirajte i pokrenite PostgreSQL.


Kreirajte bazu podataka sa nazivom trader.

Podrazumevani pristupni podaci (mogu se promeniti u application.properties):
Korisničko ime: postgres
Lozinka: postgres

Možete koristiti pgAdmin ili neki drugi alat za upravljanje bazom.


4. Pokretanje aplikacije

U terminalu, pozicionirajte se u folder projekta i pokrenite komandu:

mvn clean install


Nakon toga, pokrenite aplikaciju komandom:

mvn spring-boot:run


# Testiranje aplikacije
Za testiranje je korišćen Postman.

Korisnike predstavlja klasa Trader, a naloge klasa TradeOrder.

Registracija korisnika
POST: http://localhost:8080/traders/register

Body (JSON):{
  "firstName": "Mina",
  "lastName": "Marusic",
  "username": "minamar",
  "password": "minamar123"
}

Login korisnika
POST: http://localhost:8080/traders/login

Body (JSON):
{
  "username": "minamar",
  "password": "minamar123"
}

Nakon uspešnog logovanja, biće generisan token koji će biti korišćen za dalju autentifikaciju korisnika.

Dodavanje ordersa
POST: http://localhost:8080/trade-orders

Za Authorization izaberite Bearer Token i unesite prethodno dobijeni token. U Body izaberite JSON i unesite podatke:

{
  "orderType": "BUY",
  "price": 100,
  "amount": 13,
  "traderUsername": "minamar"
}


{
  "orderType": "BUY",
  "price": 100,
  "amount": 1,
  "traderUsername": "minamar"
}


{
  "orderType": "SELL",
  "price": 50,
  "amount": 10,
  "traderUsername": "minamar"
}


{
  "orderType": "SELL",
  "price": 100,
  "amount": 1,
  "traderUsername": "minamar"
}

Prikaz 10 najboljih naloga
GET: http://localhost:8080/trade-orders/top10-buy
GET: http://localhost:8080/trade-orders/top10-sell

Korisnici mogu pratiti 10 najboljih ordera za kupovinu ili prodaju, zavisno od zahteva.

Pregled svih naloga
GET: http://localhost:8080/trade-orders
GET: http://localhost:8080/trades

Ove metode omogućavaju korisnicima da pregledaju sve kreirane naloge i sve korisnike.

# WebSocket

Dodat je WebSocket za praćenje promena u 10 najboljih naloga za kupovinu i prodaju u realnom vremenu. Kada neko doda novi TradeOrder, WebSocket će prikazati promene u 10 najboljih naloga.

Za korišćenje WebSocket-a u Postman-u:

Izaberite opciju WebSocket Request.

Unesite URL: 

ws://localhost:8080/ws/orders.

Kliknite Connect i zatim Send.

Kada se novi TradeOrder doda, biće vidljive promene u 10 najboljih naloga za kupovinu i prodaju.

Ukoliko ima problema oko pokretanja i testiranja projekta pišite na mejl: minamarusic@gmail.com
