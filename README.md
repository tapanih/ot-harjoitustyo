# Ohjelmistotekniikka, harjoitustyö

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/arkkitehtuuri.md)

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

### Checkstyle ###

Tiedostossa [checkstyle.xml](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

ja mahdolliset virheilmoitukset löytyvät tämän jälkeen tiedostosta *target/site/checkstyle.html*
