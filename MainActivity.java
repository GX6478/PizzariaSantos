package br.com.projetodroidpizza.activity;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextToSpeech objTextParaVoz;

    private Button btnIniciarAtendimento;
    private Button btnSair;

    private static final int SOLICITACAO_DA_PIZZA = 1;
    private static final int SOLICITACAO_DA_BEBIDA = 2;
    private static final int CONFIRMA_PEDIDO = 3;

    private Double valorPedido = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciarAtendimento = (Button) findViewById(R.id.btnIniciar);
        btnSair = (Button) findViewById(R.id.btnSair);

        //TextToSpeech serve para capturar o comando de voz
        objTextParaVoz = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //Chamar setLanguage() para entender a voz em Portugues Brasil("pt", "BR")
                objTextParaVoz.setLanguage(new Locale("pt_BR"));
                objTextParaVoz.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    //Iniciando uma fala
                    @Override
                    public void onStart(String utteranceId) {
                    }
                    //Acabou de falar
                    @Override
                    public void onDone(String utteranceId) {
                        iniciarCapturaFala(Integer.parseInt(utteranceId));
                    }
                    @Override
                    public void onError(String utteranceId) {
                    }
                });
            }
        });

        //Evento para ouvir quando clicar no botão
        btnIniciarAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objTextParaVoz.speak("Bem Vindo a Pizzaria Santos. Fale o sabor da sua pizza.",
                        TextToSpeech.QUEUE_FLUSH, null, String.valueOf(SOLICITACAO_DA_PIZZA));
                //Terminando de falar, irá para o método onDone() dentro do método onInit
            }
        });

        btnSair.setOnClickListener(this);

    }

    public void onClick(View view){
        this.finish();
    }

    private void iniciarCapturaFala(Integer flagReconhecimento) {
        //Entender a voz
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Fale Agora");
        startActivityForResult(intent, flagReconhecimento);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        //Pegar uma lista de possíveis falas
        ArrayList<String> listaPossiveisFalas = dataIntent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

        //Verificar se a lista está preenchida
        if (listaPossiveisFalas != null && listaPossiveisFalas.size() > 0) {
            boolean achou = false;

            //Percorrer a lista
            for (int i = 0; i < listaPossiveisFalas.size(); i++) {

                String resposta = listaPossiveisFalas.get(i);

                if (requestCode == SOLICITACAO_DA_PIZZA) {

                    if (resposta.equalsIgnoreCase("Presunto") || resposta.equalsIgnoreCase("Pizza Presunto") ||
                            resposta.equalsIgnoreCase("Pizza de Presunto")) {

                        achou = true;
                        objTextParaVoz.speak("Entendi, Pizza de Presunto. Escolha sua bebida.",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(SOLICITACAO_DA_BEBIDA));
                        valorPedido += 28;
                    }

                    if (resposta.equalsIgnoreCase("Portuguesa") || resposta.equalsIgnoreCase("Pizza Portuguesa") ||
                            resposta.equalsIgnoreCase("Pizza de Portuguesa")) {

                        achou = true;
                        valorPedido += 32;
                        objTextParaVoz.speak("Entendi, Pizza de Portuguesa. Escolha sua bebida.",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(SOLICITACAO_DA_BEBIDA));
                    }

                    if (resposta.equalsIgnoreCase("Camarão") || resposta.equalsIgnoreCase("Pizza Camarão") ||
                            resposta.equalsIgnoreCase("Pizza de Camarão")) {

                        achou = true;
                        valorPedido += 40;
                        objTextParaVoz.speak("Entendi, Pídiça de Camarão. Escolha sua bebida.",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(SOLICITACAO_DA_BEBIDA));
                    }

                } else if (requestCode == SOLICITACAO_DA_BEBIDA) {

                    if (resposta.equalsIgnoreCase("Coca") || resposta.equalsIgnoreCase("Coca-Cola") ||
                            resposta.equalsIgnoreCase("Coquinha")) {

                        achou = true;
                        valorPedido += 7;
                        objTextParaVoz.speak("Entendi, Coca-Cola. O valor do seu pedido é " + String.valueOf(valorPedido) + " reais. Confirma o seu pedido?",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(CONFIRMA_PEDIDO));
                    }

                    if (resposta.equalsIgnoreCase("Guarana") || resposta.equalsIgnoreCase("Guará") ||
                            resposta.equalsIgnoreCase("Guaraná")) {

                        achou = true;
                        valorPedido += 5;
                        objTextParaVoz.speak("Entendi, Guaraná. O valor do seu pedido é " + String.valueOf(valorPedido) + " reais. Confirma o seu pedido?",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(CONFIRMA_PEDIDO));
                    }

                    if(resposta.equalsIgnoreCase("Fanta") || resposta.equalsIgnoreCase("Fanta Uva") || resposta.equalsIgnoreCase("Uva") ||
                            resposta.equalsIgnoreCase("Fanta-Uva")){

                        achou = true;
                        valorPedido += 5;
                        objTextParaVoz.speak("Entendi, Fanta Uva. O valor do seu pedido é " + String.valueOf(valorPedido) + " reais. Confirma o seu pedido?",
                                TextToSpeech.QUEUE_FLUSH, null, String.valueOf(CONFIRMA_PEDIDO));
                    }

                } else if (requestCode == CONFIRMA_PEDIDO) {
                    if (resposta.equalsIgnoreCase("Sim") || resposta.equalsIgnoreCase("OK") || resposta.equalsIgnoreCase("yes")
                            || resposta.equalsIgnoreCase("Confirmo") || resposta.equalsIgnoreCase("Confirmado")) {

                        achou = true;
                        objTextParaVoz.speak("Pedido confirmado. Em 50 minutos seu pedido será entregue em sua residência." +
                                        "Pizzaria Santos agradece sua preferência. Obrigado!",
                                TextToSpeech.QUEUE_FLUSH, null, null);

                        Intent it = new Intent(this, exibeTotal.class);
                        it.putExtra("VALORTOTAL", "R$ " + String.valueOf(valorPedido));
                        startActivity(it);

                    }

                    if (resposta.equalsIgnoreCase("Não") || resposta.equalsIgnoreCase("Not") || resposta.equalsIgnoreCase("Sair")) {

                        achou = true;
                        objTextParaVoz.speak("Ok, pedido cancelado. Faça seu pedido novamente.",
                                TextToSpeech.QUEUE_FLUSH, null, null);
                    }

                    valorPedido = 0.0;
                }

                valorPedido = 0.0;
                if (!achou) {
                    Toast.makeText(this, "Não entendi.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
