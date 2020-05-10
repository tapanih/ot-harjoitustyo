# Ohjelmistotekniikka, harjoitustyö

## Kuvaus

Sovellus on yksinkertainen piirto-ohjelma, jolla voi muokata ja tallentaa kuvia muutamassa kuvaformaatissa.

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/arkkitehtuuri.md)

[Käyttöohje](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/kayttoohje.md)

[Testausdokumentti](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/testausdokumentti.md)

[Tuntikirjanpito](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/doc/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/tapanih/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/tapanih/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/tapanih/ot-harjoitustyo/releases/tag/loppupalautus)

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
mvn test jacoco:report
```

ja raportti löytyy tämän jälkeen tiedostosta *target/site/jacoco/index.html*

### Suoritettavan jarin generointi

Suoritettavan jar-tiedoston saa generoitua komennolla
```
mvn package
```
Jar-tiedosto löytyy tämän jälkeen hakemistosta *target* nimellä *pixeleditor-1.0-SNAPSHOT.jar*

### Javadoc ###

Javadocin saa generoitua komennolla
```
mvn javadoc:javadoc
```
Tämän jälkeen Javadocia voi tarkastella avaamalla tiedosto *target/site/apidocs/index.html*

### Checkstyle ###

Tiedostossa [checkstyle.xml](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/checkstyle.xml) määritellyt tarkistukset suoritetaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```

ja mahdolliset virheilmoitukset löytyvät tämän jälkeen tiedostosta *target/site/checkstyle.html*
