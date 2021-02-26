package com.skyimager.ifpe.removedis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private ImageView imagemTela;
    static   private Bitmap imagemT;

    static public    int [] mapL= new int[548*548];
    static public int [] mapC= new int[548*548];
    int[][] mapCx= new int[548][548];
    int[][] mapLx= new int[548][548];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imagemTela= (ImageView) findViewById(R.id.imagemCap);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        //File f1 = new File("/storage/emulated/0/tst7.jpg");
        File f1 = new File("/storage/emulated/0/tst7c.jpg");
        imagemT=(BitmapFactory.decodeFile(f1.getAbsolutePath(),(options)));

        mapeamento();
        cortarImagem(imagemT);
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*==*==*=**/
    private void cortarImagem(Bitmap bitmap) {
        Bitmap bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        int wq= Math.abs(200-imagemT.getWidth()/2);
        int hq= Math.abs(200-imagemT.getHeight()/2);
        for(int x=0;x<bmp.getWidth();x++){
            for(int y=0;y<bmp.getHeight();y++){
                bmp.setPixel(x, y, bitmap.getPixel(x+wq, y+hq));
            }
        }

        removeDis(bmp);

    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*==*==**/
public void removeDis(Bitmap bmp){

    Log.i("Bruna", "TESTE;"+  mapCx[17][535]+ "  "+ mapLx[547][547]);

 //   Bitmap bmp2 = Bitmap.createBitmap(548, 548, Bitmap.Config.ARGB_8888);

    Bitmap bmp2 = Bitmap.createBitmap(bmp, 0, 0, 400, 400);
   for(int x=0;x<bmp.getWidth();x++){
        for(int y=0;y<bmp.getHeight();y++){
            bmp2.setPixel(x, y, bmp.getPixel(mapCx[x][y],mapLx[x][y]));
            // bmp2.setPixel(x+t,y, bmp.getPixel(mapCx[x+t][y],mapLx[x+t][y]));
        }
    }
  /*  for(int x=0;x<bmp2.getWidth();x++){
        for(int y=0;y<bmp2.getHeight();y++){

         //   bmp2.setPixel(x, y, bmp.getPixel(mapCx[x][y],mapLx[x][y]));
         int s= bmp.getPixel(mapCx[x][y],mapLx[x][y]);
          //  bmp2.setPixel(x, y, bmp.getPixel(x,y));


            // bmp2.setPixel(x+t,y, bmp.getPixel(mapCx[x+t][y],mapLx[x+t][y]));
        }
    }*/
    imagemTela.setImageBitmap(bmp2);

}
    
        /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*==*==*=**/
    private void mapeamento(){
        Log.i("Bruna", "ENTROU REMOVE DIS;");
        try {

            AssetManager assetManager = getResources().getAssets();
            InputStream inputStream = assetManager.open("map.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String  linha;
            LinkedList<String> linhas = new LinkedList<String>();

            while((linha = bufferedReader.readLine())!=null){
                String[] lin= linha.split("[,]");
                int x = Integer.parseInt(lin[0]);
                int x2 = Integer.parseInt(lin[1]);
                mapC[linhas.size()] = x;//ler a columa MapC
                mapL[linhas.size()] = x2;//ler a columa MapL
                linhas.add(linha);
            }
            inputStream.close();
            int c=0;
            for (int coluna=0; coluna<548; coluna++ ){
                for (int l=0; l<548; l++ ){
                    mapCx[coluna][l]=mapC[coluna+c];
                    mapLx[coluna][l]=mapL[coluna+c];
                    c++;
                }
                c--;
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        Log.i("Bruna", "SAIU REMOVE DIS;");
    }
    /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*==*==*=**/
}