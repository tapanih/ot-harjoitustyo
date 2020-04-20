# Arkkitehtuurikuvaus

## Sovelluslogiikka

Luokka/pakkauskaavio:

![Luokkakaavio](images/luokkakaavio.png)

## Päätoiminnallisuudet

### Kynätyökalu

Kun työkalupalkista on valittuna kynätyökalu, niin piirtoalueelle klikkaamisen jälkeen sovelluksen kontrolli etenee seuraavasti:

![Kynätyökalun sekvenssi](images/sekvenssi1.png)

Piirtoalueen tapahtumankäsittelijä kutsuu työkalupalvelun *ToolService* metodia *mousePressed*, joka ohjaa hiiritapahtuman valittuna olevalle työkalulle, joka on kuvatussa tilanteessa kynätyökalu. Kynätyökalu tarvitsee PixelWriter-olion, jolla piirtoaluetta voi muokata. Tämän se hakee piirtoalueelta *CanvasService*-luokan kautta. Kynätyökalu tarvisee myös tiedon valittuna olevasta väristä, jonka se kysyy väripalvelulta *ColorService*. Muokattavan pikselin koordinaatit kynätyökalu saa selville parametrina saamastaan hiiritapahtumasta. Sitten kynätyökalu kutsuu PixelWriter-olion *setColor*-metodia, jolloin piirtoalue päivittyy ja muutos näkyy käyttäjälle.