package com.alie.search.assets;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static java.lang.Math.pow;

public class Arama {

    public HashMap<String, Integer> AramaFunction(Hashtable<Integer, String> HashTable, String girilenKelime, int tableSize) {
        HashMap<String, Integer> hm = new HashMap<>();

        int Arananascii = 0; //aranan kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < girilenKelime.length(); i++) {  //dongu girilen kelimenin boyutu kadar calistiriliyor
            char Kkarakter = girilenKelime.charAt(i);                  //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter; //karakterler ascii ye cevriliyor
            Arananascii = Arananascii + (i + 1) * Kasci;                //girilen kelimenin ascii karsiligi bulunuyor
        }
        int key; //aranan kelimenin yerlesecegi yeritutan degisken
        for (int j = 0; j < tableSize; j++) {  //dongu 0'dan tablonun boyutuna kadar calistiriliyor
            key = (int) ((Arananascii + pow(j, 2)) % tableSize); //aranan kelimenin tabloda olasi yerlesecegi  yer belirleniyor
            if (!HashTable.containsKey(key)) { //eger kelimenin yerlesmesi gereken yer bossa  kosul saglaniyor ve kelime bulunamiyor
                System.out.println("Aradiginiz kelime bulunamadi");
                HashMap<String, Integer> hmLocal = harfYerdegistir(HashTable, girilenKelime, tableSize); //kelime bulunmadigi icin kelimenin harflerinin yerleri degistirerek aramaya gonderiliyor
                for (Map.Entry<String, Integer> entry : hmLocal.entrySet()) {
                    hm.put(entry.getKey(),entry.getValue());
                }
                HarfCikar(HashTable, girilenKelime, tableSize);  //kelime bulunmadigi icin harf cikarma fonksiyonuna gonderiliyor
                break; //dongu kiriliyor
            }
            if (girilenKelime.equals(HashTable.get(key))) { //kelimenin olasi yerlesecegi yer doluysa aranan  kelime ile yerlesen kelime esit olup olmadigi kontrol ediliyor
                System.out.println("Aradiginiz kelime    " + key + ".inci hucrede  mevcuttur "); // kosul saglanirsa kelime bulunuyor
                hm.put(girilenKelime,key);
                break; //dongu kiriliyor
            }
        }
        return hm;
    }


    private void HarfCikar(Hashtable<Integer, String> HashTable, String girilenKelime, int tableSize) {
        char kdizi[] = new char[girilenKelime.length()]; //aranan kelimeyi karakterlere cevirmek icin char tipinde dizi tanimlaniyor
        char k[] = new char[girilenKelime.length() - 1];  //aranan  kelimenin bir harf eksiltilmis hali icin char tipinde kelimenin bir harf eksigi boyutunda  dizi tanimlaniyor
        int j = 0; // hangi harfin silinecegini tutan index
        char temp;  //gecici degisken
        try {

            for (int i = 1; i < girilenKelime.length() + 1; i++) {  //dongu aranan kelimenin uzunlugu kadar calistiriliyor
                String eskiHal = girilenKelime; //girilen kelimenin eski halini korumasi icin eskihal degiskenine ataniyor
                for (int l = 0; l < eskiHal.length(); l++) { //dongu girilen kelimenin uzunlugu kadar calisiyor
                    kdizi[l] = eskiHal.charAt(l); //girilen kelime karakterlere donusturuluyor
                }
                while (j < girilenKelime.length() - 1) { //dongu girilen kelimenin 1 eksigi kadar calisiyor
                    temp = kdizi[j]; // karakter gecici degiskene ataniyor
                    kdizi[j] = kdizi[j + 1]; // bir sonkari  karakter bir oncekinine ataniyor
                    j++; ///sonraki harf icin
                }
                j = i;  // j  dongudeki i degerine bagli degisiyor

                for (int a = 0; a < girilenKelime.length() - 1; a++) {//dongu girilen kelimenin 1 eksigi kadar calisiyor. Bunun amaci en son karakteri ortadan kaldirmak
                    k[a] = kdizi[a];
                }
                String yeni = new String(k); //harf eksiltilmis dizi birlestiriliyor
                harfCikararakArama(HashTable, yeni, tableSize); //harf cikarilmis haliyle arama fonksiyonuna gonderiliyor
            }
        } catch (Exception e) {
            System.out.println("hata: " + e);
        }
    }


    private void harfCikararakArama(Hashtable<Integer, String> hashTable, String arananKelime, int tableSize) {

        int Arananascii = 0; //harf cikartilmis kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < arananKelime.length(); i++) {
            char Kkarakter = arananKelime.charAt(i);                   //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter; //harfler ascii ye donusturuluyor
            Arananascii = Arananascii + (i + 1) * Kasci; //kelime askiye donusturuluyor
        }
        int key; //aranan kelimenin yerlesecegi yeri belirleyen degisken
        for (int j = 0; j < tableSize; j++) {
            key = (int) ((Arananascii + pow(j, 2)) % tableSize); //kelimenin yerlesmesi geeken yer belirleniyor
            if (!hashTable.containsKey(key)) { //eger kelimenin yerlesmesi gereken yer bossa bu kelime  yoktur
                break; //dongu kiriliyor
            }
            if (arananKelime.equals(hashTable.get(key))) { //eger kelimenin yerlesecegi yer doluysa arana kelime ile tablodaki kelime karsilastiriliyor ayni ise kelime bulunuyor
                System.out.println("fakat " + arananKelime + "  kelimesi " + key + ".inci hucrede mevcuttur");
                break; //dongu kiriliyor
            }

        }

    }


    private HashMap<String, Integer> harfYerdegistir(Hashtable<Integer, String> HashTable, String girilenKelime, int tableSize) {
        char[] kdizi = new char[girilenKelime.length()];
        char temp;
        String yeni = " ";// harf degistirilmis kelimeyi tutan degisken
        for (int i = 0; i < girilenKelime.length(); i++) {
            kdizi[i] = girilenKelime.charAt(i); //girilen kelime karakterlere cevriliyor
        }
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int j = 0; j < girilenKelime.length(); j++) {
            try {
                temp = kdizi[j];          // kelimenin
                kdizi[j] = kdizi[j + 1];  ///      harfleri
                kdizi[j + 1] = temp;        ///    yerdegistiriyor

                yeni = new String(kdizi); //yeni kelime yeni adinda degiskene ataniyor

                Pair<String, Integer> pair = harfDegistirArama(HashTable, yeni, tableSize); //aramaya gonderiliyor
                if(pair != null)
                    hashMap.put(pair.getKey(), pair.getValue());
                temp = kdizi[j];      //kelime
                kdizi[j] = kdizi[j + 1];//eskihaline
                kdizi[j + 1] = temp;    //donusturuluyor
            } catch (Exception e) {
                System.err.println();
            }
        }
        return hashMap;
    }


    private Pair<String, Integer> harfDegistirArama(Hashtable<Integer, String> hashTable, String arananKelime, int tableSize) {

        int Arananascii = 0; //aranan kelimenin ascii karsiligini tutan degisken

        for (int i = 0; i < arananKelime.length(); i++) {
            char Kkarakter = arananKelime.charAt(i);                  //kelime karakterlere cevriiyor
            int Kasci = (int) Kkarakter;  //harfler ascii karsliklarina donusturluor
            Arananascii = Arananascii + (i + 1) * Kasci;//kelimenin ascii karsiligi bulunuyor
        }

        int key; //tabloda yerlesegegi yeri tutan degisken


        for (int j = 0; j < tableSize; j++) {
            key = (int) ((Arananascii + pow(j, 2)) % tableSize); //yerleseceggi yer belrleniyor
            if (!hashTable.containsKey(key)) {//eger yerlesmesi gereken yer bossa kosul saglaniyor ve kelime bulunamiyor
                break;
            }
            if (arananKelime.equals(hashTable.get(key))) {//eger yerlesecegi yer doluysa yerleseccegi yerdeki kelime ile ayni olup olmadigi kontrol ediliyor kosul saglaniyorsa kelime bulunuyor
                System.out.println("fakat " + arananKelime + "  kelimesi " + key + ".inci hucrede mevcuttur");
                return new Pair<String, Integer>(arananKelime, key);
            }
        }
        return null;
    }
}
