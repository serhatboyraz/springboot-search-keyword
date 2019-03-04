package com.alie.search;

import com.alie.search.assets.Arama;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import static java.lang.Math.pow;
import static org.junit.Assert.*;

public class AramaTest {
    @org.junit.Test
    public void aramaFunction() {
        final int tableSize = 211;                          //tablonun boyutu belirleniyor
        String kelimeler[] = new String[100];               //kelimeleri tutan dizi ve boyutu belirleniyor
        int asciKarsilik[] = new int[100];
        Hashtable<Integer, String> HashTable = new Hashtable<>(tableSize);       //tablo olusturuluyor
        try {
            FileInputStream fStream = new FileInputStream("kelimeler.txt");          //dosya olusturuluyor ve dosyanin nerede oldugu tanimlaniyor
            DataInputStream sStream = new DataInputStream(fStream);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(sStream));
            for (int i = 0; i < kelimeler.length; i++) {
                kelimeler[i] = bReader.readLine();           //kelimeler tek tek okunup kelimeler adli diziye aktarliyor
            }
            sStream.close();             //dosya kapatiliyor
        } catch (Exception e) {
            System.err.println("Hatalar : " + e.getMessage());      //eger dosyanin acilmasinda hata varsa ekrana hata mesaji veiliyor
        }
        asciKarsilikAlanFunction(asciKarsilik, kelimeler);                            //kelimelerin ascii karsiligini  bulan fonksiyon
        tabloYerlestirmeFunction(HashTable, asciKarsilik, kelimeler, tableSize);        //kelimeleri tabloya yerlestiren  fonksiyon


        HashMap<String, Integer> kediHm =  new Arama().AramaFunction(HashTable, "kedi", tableSize);
        for (Map.Entry<String,Integer> ent : kediHm.entrySet()){
            assertNotEquals(ent.getKey(),"kedi");
        }

        HashMap<String, Integer> selamHm =  new Arama().AramaFunction(HashTable, "selam", tableSize);
        assertNotNull(selamHm.get("selam"));
        assertEquals(selamHm.get("selam").longValue(), 97);

        //HashMap<String, Integer> selaHm =  new Arama().AramaFunction(HashTable, "sela", tableSize);



    }


    public void asciKarsilikAlanFunction(int ascii[], String kelimeler[]) {

        for (int j = 0; j < kelimeler.length; j++) {
            String kelime = kelimeler[j];   //gelen kelimeler kelime adli degiskene ataniyor
            char kdizi[] = new char[10];   //kelimenin karakterlere ayrirmak icin char tipinde dizi tanimlaniyor
            for (int k = 0; k < kelime.length(); k++) {
                kdizi[k] = kelime.charAt(k);  //aranan kelime karakterlere ayriliyor
            }
            int Kasci = 0; //toplam ascii degerin tutulacagi degisken tanimlaniyor
            for (int i = 0; i < kelime.length(); i++) {

                Kasci = Kasci + (i + 1) * (int) kdizi[i]; //kelimenin  ascii karsiligi alinip toplaniyor
            }
            ascii[j] = Kasci; // tum kelimelerin ascii karsiliklari  ascii[]  adli diziye ataniyor
        }

    }

    public void tabloYerlestirmeFunction(Hashtable<Integer, String> hashTable, int[] asciKarsilik, String[] kelimeler, int tableSize) {
        int key = 0;  // her kelime icin key degerinin tutuldugu  dugisken tanimlaniyor
        for (int i = 0; i < kelimeler.length; i++) { // i==0 dan kelimeler dizisinin boyutuna kadar calistiriliyor //*** 1.dongu
            for (int j = 0; j < tableSize; j++) { //j==0 dan tablonun boyutuna kadar calistiriliyor  //*** 2.dongu
                key = (int) ((asciKarsilik[i] + pow(j, 2)) % tableSize); //girilen kelimenin tabloda  yerlecsecegi yer belirleniyor
                if (!hashTable.containsKey(key)) //kelimenin yerlesmesi gereken  yer kontrol ediliyor eger  bossa  kosul saglaniyor eger kosul saglanmazsa 2. yerlesmesi gereken yer belirlenecek
                {
                    hashTable.put(key, kelimeler[i]);  //kelmeler tabloya yerlestiriliyor
                    break; // 2. donguden cikip ilk donguden devam ediliyor
                }
            }
        }
    }

}
