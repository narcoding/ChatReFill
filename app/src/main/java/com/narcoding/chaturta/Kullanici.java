package com.narcoding.chaturta;

/**
 * Created by Ali on 15.10.2016.
 */
public class Kullanici {
    public int IdKullanici     ;

    public Kullanici(int idKullanici, String email, String kullaniciAdi, String sifre, String kod, boolean aktif) {
        IdKullanici = idKullanici;
        Email = email;
        KullaniciAdi = kullaniciAdi;
        Sifre = sifre;
        Kod = kod;
        Aktif = aktif;
    }

    public String Email        ;
    public String KullaniciAdi ;
    public String Sifre        ;
    public String Kod          ;
    public boolean Aktif       ;
}
