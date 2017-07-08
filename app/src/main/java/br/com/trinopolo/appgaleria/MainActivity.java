package br.com.trinopolo.appgaleria;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SharedPreferences config;

    ImageView imageView;

    Button btnPrevious;
    Button btnNext;

    TextView tvImagePosition;
    TextView tvImageTotal;

    ArrayList<String> listaImagens;
    int posicaoAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("AppGaleria");
        this.config = getSharedPreferences("config", MODE_PRIVATE);

        this.imageView = (ImageView) findViewById(R.id.imageView);

        this.btnPrevious = (Button) findViewById(R.id.btnPrevious);
        this.btnNext = (Button) findViewById(R.id.btnNext);

        this.tvImagePosition = (TextView) findViewById(R.id.tvImagePosition);
        this.tvImageTotal = (TextView) findViewById(R.id.tvImageTotal);

        this.posicaoAtual = this.config.getInt("posicao_atual", 0);
        this.criarListaImagens();
        this.transicaoImagens();

        this.carregarImagem();

    }

    private void criarListaImagens() {
        this.listaImagens = new ArrayList<String>();
        this.listaImagens.add("image_01.png");
        this.listaImagens.add("image_02.png");
        this.listaImagens.add("image_03.png");

        this.tvImageTotal.setText(String.valueOf(this.listaImagens.size()));
    }

    private void carregarImagem() {
        try {
            // Seta a ordem da imagem
            this.tvImagePosition.setText(String.valueOf(this.posicaoAtual + 1));

            // Salva a posicao da imagem no Shared Preferences
            this.config.edit().putInt("posicao_atual", this.posicaoAtual).commit();

            InputStream image = getAssets().open(this.listaImagens.get(this.posicaoAtual));
            Bitmap b = BitmapFactory.decodeStream(image);
            imageView.setImageBitmap(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transicaoImagens() {

        // Botao Next
        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicaoAtual++;
                if (posicaoAtual >= (listaImagens.size())) {
                    posicaoAtual = 0;
                }
                carregarImagem();
            }
        });

        this.btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicaoAtual--;
                if (posicaoAtual <= -1) {
                    posicaoAtual = listaImagens.size() - 1;
                }
                carregarImagem();
            }
        });


    }

}
