package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    Kassapaate kassa;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void uudenKassanRahamaaraOnOikea() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void uudenKassanMyytyjenLounaidenMaaraOnNolla() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty() + kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void rahamaaraKasvaaLounaanHinnallaJosMaksuOnRiittava() {
       kassa.syoMaukkaasti(400);
       kassa.syoEdullisesti(240);
       assertEquals(100640, kassa.kassassaRahaa());
    }
    
    @Test
    public void vaihtorahanSuuruusOnOikeaJosMaksuOnRiittava() {
        int vaihtoraha = kassa.syoEdullisesti(1000) + kassa.syoMaukkaasti(500);
        assertEquals(860, vaihtoraha);
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaJosMaksuOnRiittava() {
        kassa.syoMaukkaasti(600);
        kassa.syoEdullisesti(300);
        kassa.syoEdullisesti(400);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaidenLounaidenMaaraKasvaaJosMaksuOnRiittava() {
        kassa.syoMaukkaasti(600);
        kassa.syoEdullisesti(300);
        kassa.syoEdullisesti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahamaaraEiKasvaJosMaksuEiOleRiittava() {
        kassa.syoEdullisesti(239);
        kassa.syoMaukkaasti(399);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kaikkiRahatPalautetaanVaihtorahanaJosMaksuEiOleRiittava() {
        int vaihtoraha = kassa.syoEdullisesti(200) + kassa.syoMaukkaasti(300);
        assertEquals(500, vaihtoraha);
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiKasvaJosMaksuEiOleRiittava() {
        kassa.syoEdullisesti(0);
        kassa.syoEdullisesti(150);
        kassa.syoEdullisesti(239);
        kassa.syoEdullisesti(-1234);
        
        kassa.syoMaukkaasti(0);
        kassa.syoMaukkaasti(100);
        kassa.syoMaukkaasti(399);
        kassa.syoMaukkaasti(-3100);
        
        assertEquals(0, kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoOnnistuuJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(240);
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void edullisenLounaanOstoVahentaaSaldoaJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(400);
        kassa.syoEdullisesti(kortti);
        assertEquals(160, kortti.saldo());
    }
    
    @Test
    public void maukkaanLounaanOstoOnnistuuJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(400);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void maukkaanLounaanOstoVahentaaSaldoaJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(500);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void edullistenLounaidenMaaraKasvaaJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(10000);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaidenLounaidenMaaraKasvaaJosKortillaOnTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(10000);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullisenLounaanOstoEiOnnistuJosKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(200);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void maukkaanLounaanOstoEiOnnistuJosKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(300);
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kortinSaldoEiMuutuJosKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(239);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(239, kortti.saldo());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiMuutuJosKortillaEiOleTarpeeksiRahaa() {
        Maksukortti kortti = new Maksukortti(200);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty() + kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortillaMaksettaessa() {
        Maksukortti kortti = new Maksukortti(20000);
        kassa.syoEdullisesti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortilleRahaaLadattaessaKortinSaldoMuuttuu() {
        Maksukortti kortti = new Maksukortti(200);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(1200, kortti.saldo());
    }
    
    @Test
    public void kortilleRahaaLadattaessaKassanRahamaaraMuuttuu() {
        Maksukortti kortti = new Maksukortti(200);
        kassa.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, kassa.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiVaikutaKassanRahamaaraan() {
        Maksukortti kortti = new Maksukortti(2000);
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void negatiivisenSummanLataaminenEiVaikutaKortinSaldoon() {
        Maksukortti kortti = new Maksukortti(2000);
        kassa.lataaRahaaKortille(kortti, -1000);
        assertEquals(2000, kortti.saldo());
    }
}
