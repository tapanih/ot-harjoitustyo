package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(420);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoOnAlussaOikein() {
        assertEquals("saldo: 4.20", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(115);
        assertEquals("saldo: 5.35", kortti.toString());
    }
    
    @Test
    public void kortiltaVoiOttaaRahaa() {
        kortti.otaRahaa(350);
        assertEquals(70, kortti.saldo());
    }
    
    @Test
    public void saldoEiMuutuJosRahaaEiOleTarpeeksi() {
        kortti.otaRahaa(500);
        assertEquals(420, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahaaOnTarpeeksi() {
        assertTrue(kortti.otaRahaa(420));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahaaEiOleTarpeeksi() {
        assertFalse(kortti.otaRahaa(421));
    }
}
