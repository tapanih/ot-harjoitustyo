import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaksukorttiTest {
    Maksukortti maksukortti;

    @Before
    public void setUp() {
        maksukortti = new Maksukortti(10);
    }
     
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("Kortilla on rahaa 10.0 euroa", maksukortti.toString());
    }

    @Test
    public void syoEdullisestiVahentaaSaldoaOikein() {
        maksukortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 7.5 euroa", maksukortti.toString());
    }

    @Test
    public void syoMaukkaastiVahentaaSaldoaOikein() {
        maksukortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 6.0 euroa", maksukortti.toString());
    }

    @Test
    public void syoEdullisestiEiVieSaldoaNegatiiviseksi() {
        maksukortti.syoMaukkaasti();
        maksukortti.syoMaukkaasti();
        // nyt kortin saldo on 2
        maksukortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 2.0 euroa", maksukortti.toString());
    }
    
    @Test
    public void syoMaukkaastiEiVieSaldoaNegatiiviseksi() {
        maksukortti.syoEdullisesti();
        maksukortti.syoEdullisesti();
        maksukortti.syoEdullisesti();
        //nyt kortin saldo on 2.5
        maksukortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 2.5 euroa", maksukortti.toString());
    }
    
    @Test
    public void edullisenLounaanVoiOstaaKunRahaaOnJuuriLounaanVerran() {
        maksukortti = new Maksukortti(2.5);
        maksukortti.syoEdullisesti();
        assertEquals("Kortilla on rahaa 0.0 euroa", maksukortti.toString());
    }
    
    @Test
    public void maukkaanLounaanVoiOstaaKunRahaaOnJuuriLounaanVerran() {
        maksukortti = new Maksukortti(4);
        maksukortti.syoMaukkaasti();
        assertEquals("Kortilla on rahaa 0.0 euroa", maksukortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        maksukortti.lataaRahaa(25);
        assertEquals("Kortilla on rahaa 35.0 euroa", maksukortti.toString());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiMuutaSaldoa() {
        maksukortti.lataaRahaa(-5);
        assertEquals("Kortilla on rahaa 10.0 euroa", maksukortti.toString());
    }
    
    @Test
    public void kortinSaldoEiYlitaMaksimiarvoa() {
        maksukortti.lataaRahaa(200);
        assertEquals("Kortilla on rahaa 150.0 euroa", maksukortti.toString());
    }
}
