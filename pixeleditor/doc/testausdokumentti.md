# Testausdokumentti

Ohjelma testaus on toteutettu automatisoiduin yksikkö- ja integraatiotestein JUnitilla sekä manuaalisesti tapahtuneilla järjestelmätason testeillä.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Suurin osa sovelluslogiikan testeistä käyttävät *JavaFX JUnit-4 Testrunner*- kirjastoa, jonka avulla *@TestInJfxThread* annotaatiolla merkityt testit suoritetaan JavaFX-säikeessä. Tämä mahdollistaa esimerkiksi *Canvas*-olion käsittelyn testien aikana.

Luokassa [FileServiceTest](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/test/java/pixeleditor/domain/FileServiceTest.java) testataan tiedostojen käsittelyä. Luokka testaa tiedostojen avaamista käyttämällä kansiossa *src/test/resources/images* sijaitsevia kuvatiedostoja. Tiedostoon kirjoittamisen testauksessa käytettävät tilapäiset tiedostot luodaan JUnitin [TemporaryFolder](https://junit.org/junit4/javadoc/4.12/org/junit/rules/TemporaryFolder.html) -rulen avulla.

Luokissa [BucketToolTest](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/test/java/pixeleditor/domain/tools/BucketFillToolTest.java), [ColorPickerToolTest](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/test/java/pixeleditor/domain/tools/ColorPickerToolTest.java), [EraserToolTest](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/test/java/pixeleditor/domain/tools/EraserToolTest.java) ja [PenToolTest](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/test/java/pixeleditor/domain/tools/PenToolTest.java) testataan eri työkalujen toimintaa. Luokissa määritellyt testit ovat suurimmaksi osaksi integraatiotestejä, jotka hyödyntävät ja testaavat luokkia [CanvasService](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/main/java/pixeleditor/domain/CanvasService.java) ja [ColorService](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/main/java/pixeleditor/domain/ColorService.java). Näille luokille ei ole tämän takia määritelty omia testiluokkia.

Luokkaa [ToolService](https://github.com/tapanih/ot-harjoitustyo/blob/master/pixeleditor/src/main/java/pixeleditor/domain/ColorService.java) ei testata tällä hetkellä ollenkaan.

### Testikattavuus

Käyttöliittymää lukuunottamatta sovelluksen testauksen rivikattavuus on 85% ja haaraumakattavuus on 71%.

TODO: Kuva testikattavuudesta

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti käymällä läpi määrittelydokumentissa ja käyttöohjeessa listatut toiminnallisuudet.

## Sovelluksen laatuongelmat

Sovellus ei anna tällä hetkellä ollenkaan virheilmoituksia.