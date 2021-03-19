package com.info.tahminoyunu;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class NormalOyunActivity extends AppCompatActivity {
    // Bağlamalar
    private TextView textViewIlBilgisi, textViewIlCevap, textViewToplamPuan;
    private EditText editTextTahmin;
    private Button buttonHarfAl, buttonTahminEt;

    // Tanımlamalar
    private String[] ilListesi;
    private Random randomIl, randomHarf;
    private int randomIlNumber, randomHarfNumber, baslangicHarfSayisi;
    private String gelenIl;
    private String ilTutucu = "";
    private ArrayList<Character> ilHarfleri;
    private String tahmin;
    private float soruPuan;
    private int alinanHarfSayisi,toplamPuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_oyun);

        // idler
        textViewIlBilgisi = findViewById(R.id.textViewIlBilgisiS);
        textViewIlCevap = findViewById(R.id.textViewIlCevapS);
        editTextTahmin = findViewById(R.id.editTextTahminS);
        buttonHarfAl = findViewById(R.id.buttonHarfAlS);
        buttonTahminEt = findViewById(R.id.buttonTahminEtS);
        textViewToplamPuan = findViewById(R.id.textViewToplamPuanS);

        // il işlemleri
        ilListesi = new String[]{"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"
        };
        // random harf seçimi için kullanılan random
        randomHarf = new Random();
        // random il seçme
        randomIl = new Random();
        SoruAlanı();

    }

    public void btnHarfAl(View v) {
        if (ilHarfleri.size() > 0) {
            if (toplamPuan == 0) {
                Toast.makeText(getApplicationContext(), "Yeterli Puanınız Yok !", Toast.LENGTH_SHORT).show();
            } else if (toplamPuan > 0) {
                HarfGetirme();
                alinanHarfSayisi++;
                toplamPuan -= 10;
                textViewToplamPuan.setText("Toplam Puan : " + toplamPuan);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Alanacak Harf Kalmadı !", Toast.LENGTH_SHORT).show();
        }


    }

    public void btnTahminEt(View v) {
        tahmin = editTextTahmin.getText().toString().toLowerCase();
        if (!TextUtils.isEmpty(tahmin)) {
            if (tahmin.equals(gelenIl.toLowerCase())) {
                toplamPuan += soruPuan - (alinanHarfSayisi * 10);
                textViewToplamPuan.setText("Toplam Puan : " + Math.round(toplamPuan));
                Toast.makeText(getApplicationContext(), "Tebrikler , Doğru Bildiniz !", Toast.LENGTH_SHORT).show();
                editTextTahmin.setText("");
                SoruAlanı();
            } else {
                Toast.makeText(getApplicationContext(), "Yanlış Tahmin !", Toast.LENGTH_SHORT).show();
                editTextTahmin.setText("");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Tahmin Alanı Boş Olamaz !", Toast.LENGTH_SHORT).show();
        }
    }

    public void SoruAlanı() {
        alinanHarfSayisi = 0;
        ilTutucu = "";
        randomIlNumber = randomIl.nextInt(ilListesi.length); // 0 - 80 Arası Bir Sayı
        gelenIl = ilListesi[randomIlNumber];
        Log.e("Gelen İl : ", gelenIl);
        textViewIlBilgisi.setText(gelenIl.length() + " Harfli Bir İlimiz");

        if (gelenIl.length() >= 4 && gelenIl.length() <= 6) {
            baslangicHarfSayisi = 1;
        } else if (gelenIl.length() >= 7 && gelenIl.length() < 10) {
            baslangicHarfSayisi = 2;
        } else if (gelenIl.length() >= 10) {
            baslangicHarfSayisi = 3;
        } else {
            baslangicHarfSayisi = 0;
        }

        soruPuan = (gelenIl.length() - baslangicHarfSayisi) * 10; // ( 10 - 2 ) * 10 = 80

        // _ muhabbeti yapımı

        for (int i = 0; i < gelenIl.length(); i++) {
            if (i < gelenIl.length() - 1) {
                ilTutucu += "_ ";
            } else {
                ilTutucu += "_";
            }
        }

        textViewIlCevap.setText(ilTutucu);

        // il harflerini alma
        ilHarfleri = new ArrayList<>();

        for (char c : gelenIl.toCharArray()) {
            ilHarfleri.add(c);
        }
        // başlangıçta harf getirme
        for (int i = 0; i < baslangicHarfSayisi; i++) {
            HarfGetirme();
        }

        System.out.println("Gelen İl : " + gelenIl);

    }

    public void HarfGetirme() {
        randomHarfNumber = randomHarf.nextInt(ilHarfleri.size()); // 0 - Harf Sayısı Kadar Random Dönen Harfler => A n k a r a Gibi
        String[] textHarfler = textViewIlCevap.getText().toString().split(" "); // _ _ _ _ _ _ sadece _ leri alır boşluğa göre bölüyoruz.
        char[] gelenIlHarfler = gelenIl.toCharArray(); // Ankara -> A n k a r a   olarak char dizisine kaydediyoruz.

        for (int i = 0; i < gelenIl.length(); i++) {
            if (textHarfler[i].equals("_") && gelenIlHarfler[i] == ilHarfleri.get(randomHarfNumber)) {
                textHarfler[i] = String.valueOf(ilHarfleri.get(randomHarfNumber));
                ilTutucu = "";

                for (int j = 0; j < gelenIl.length(); j++) {
                    if (j == i) {
                        ilTutucu += textHarfler[j] + " ";
                    } else if (j < gelenIl.length() - 1) {
                        ilTutucu += textHarfler[j] + " ";
                    } else {
                        ilTutucu += textHarfler[j];
                    }
                }
                break;
            }
        }

        textViewIlCevap.setText(ilTutucu);
        ilHarfleri.remove(randomHarfNumber);
    }


}