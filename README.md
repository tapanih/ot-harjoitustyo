# Ohjelmistotekniikka, harjoitustyö

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/tuntikirjanpito.md)

## Komentorivitoiminnot

### Suorittaminen

Ohjelma vaatii toimiakseen Javan version 11. Projektin koodin pystyy suorittamaan komennoilla 

```
cd pixeleditor
mvn compile exec:java -Dexec.mainClass=pixeleditor.Main
```

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

ja raportti löytyy tämän jälkeen tiedostosta *target/site/jacoco/index.html*


