package br.com.pizzariasantos.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.content.*;
import android.widget.Button;
import android.widget.TextView;

public class exibeTotal extends AppCompatActivity implements View.OnClickListener{

    private TextView txtValorTotal;
    private Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_total);

        txtValorTotal = (TextView)findViewById(R.id.txtValorTotal);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);

        btnVoltar.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle.containsKey("VALORTOTAL")){
            String valorTotal = bundle.getString("VALORTOTAL");
            txtValorTotal.setText(valorTotal);
        }
    }

    public void onClick(View view){
        finish();
    }

}
