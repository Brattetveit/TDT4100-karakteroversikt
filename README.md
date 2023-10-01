# TDT4100-prosjekt
## Oversikt

Dette prosjektet er en karakteroversikt som holder styr på et institutt sine emner, studenter og hver enkelt student sine karakterer. Appen gir brukeren muligheten til å legge til studenter ved å skrive inn navn og studentID, og legge til emner ved å skrive inn emnenavn og emnekode. Når emner legges til, oppdateres oversikten over emnene til instituttet. Appen lar også brukeren legge til karakterer i et emne ved å skrive inn studentID og karakter. Når en ny karakter legges til, vises en oversikt over antall studenter og karakterfordelingen i det aktuelle emnet. Feltene for emnets snittkarakter, mediankarakter, høyeste karakter og laveste karakter oppdateres også. Appen gir også en oversikt over karakterene til den aktuelle studenten, og feltene for studentens snittkarakter, mediankarakter, høyeste karakter og laveste karakter oppdateres. Brukeren kan velge hvilken student det vises oversikt over, og kan klage på karakterer i et emne. Ved klage kommer det opp en «alert» med informasjon om resultatet av klagen. Endringer kan lagres, og tidligere lagret informasjon kan hentes ved hjelp av knappene «lagre» og «hent». Hvis det oppstår feil, får brukeren en «alert» med beskrivelse av hva som har gått galt.

## Modell og arkitektur

### Modell (Model):
Inneholder all data og logikk for appen. Den er den eneste som kan endre tilstanden og inkluderer oversikt over studenter, emner og karakterer. Metoder for å legge til og fjerne studenter/emner og legge til karakterer finnes i denne klassen.

### Brukergrensesnitt (View): 
Ansvarlig for det visuelle brukergrensesnittet som brukeren ser og interagerer med.

### Kontroller (Controller): 
Håndterer brukerinput, kaller på modellens metoder for å oppdatere data og videreformidler data til brukergrensesnittet. Håndterer også feil i modellen og viser feilmeldinger til brukeren.

## Testing

Det er laget testklasser for hver av klassene i modellen.
Testene inkluderer handlinger som å legge til og fjerne studenter/emner og legge til karakterer.
Det er testet at objektene oppfører seg som forventet i ulike scenarioer.
Testmetoder sjekker også at riktige unntak kastes ved feil.
