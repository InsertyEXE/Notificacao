package com.insertu.app_notificacoes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button btnProgramar, btnEnviar;
    EditText edtIntervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnEnviar = findViewById(R.id.btnEnviar);
        btnProgramar = findViewById(R.id.btnProgramar);
        edtIntervalo = findViewById(R.id.edtIntervalo);


        btnProgramar.setOnClickListener( view1 ->{

            String sIntervalo = edtIntervalo.getText().toString();

            //Filtrando o campo de intervalo
            if (sIntervalo.isEmpty()){
                edtIntervalo.setError("Preencha o campo");
                return;
            }

            int intervalo = Integer.parseInt(sIntervalo);


            //Confirgurando a notificação

            //Configurando o conteudo da notificação
            Intent notificacaoIntent = new Intent(MainActivity.this, PublicadorNotificacao.class);
            notificacaoIntent.putExtra(PublicadorNotificacao.NOTIFICACAO_ID, 1);
            notificacaoIntent.putExtra(PublicadorNotificacao.NOTIFICACAO,
                    "Aqui esta uma notificação programada com intervalo de " + intervalo * 1000);


           //Configurando qual notificação será enviada
            PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 0,
                    notificacaoIntent, PendingIntent.FLAG_ONE_SHOT);


            //Configurando para quando será enviada
            long tempoMilissegundos = SystemClock.elapsedRealtime() + (intervalo * 1000);
            AlarmManager gerenciadorAlarme = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            gerenciadorAlarme.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tempoMilissegundos, broadcast);


        });



        btnEnviar.setOnClickListener(view -> {

            //Confirgurando a notificação

            //Configurando o conteudo da mensagem
            Intent notificacaoIntent = new Intent(MainActivity.this, PublicadorNotificacao.class);
            notificacaoIntent.putExtra(PublicadorNotificacao.NOTIFICACAO_ID, 1);
            notificacaoIntent.putExtra(PublicadorNotificacao.NOTIFICACAO,
                    "Aqui esta uma notificação por clicar no botão");


            //Configurando qual notificação ira mostrar
            PendingIntent broadcast = PendingIntent.getBroadcast(MainActivity.this, 0,
                    notificacaoIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            //Configurando para quando a mensagem será enviada
            AlarmManager gerenciadorAlarme = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            gerenciadorAlarme.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, broadcast);

        });
    }
}