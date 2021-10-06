package com.insertu.app_notificacoes;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class PublicadorNotificacao extends BroadcastReceiver {

    public static final String NOTIFICACAO = "notificacao";
    public static final String NOTIFICACAO_ID = "notificacao_id";


    @Override
    public void onReceive(Context context, Intent intent) {

        //Settando o serviço do android de notificações para administrar os erros de canais de versões superiores

        NotificationManager gerenciadorNotificacoes = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        //Configurando a notificação

        //Recebendo o conteudo da mensagem da activity
        String mensagem = intent.getStringExtra(NOTIFICACAO);
        int id = intent.getIntExtra(NOTIFICACAO_ID, 0);


        //Configurando para qual tela ele ira abrir ao clickar na notificação
        final Intent intencao = new Intent(context.getApplicationContext(), MainActivity.class);

        //Fornecendo permissão para clickar na notificação e abrir o app
        PendingIntent preIntencao = PendingIntent.getActivity(context, 0, intencao, 0);


        //Configurando para qual notificação ele mostrará
        Notification notificacao = getNotificacao(context, mensagem, gerenciadorNotificacoes, preIntencao);


        //enviando a notificação
        gerenciadorNotificacoes.notify(id, notificacao);

    }


    //Função para criar a notificação (a mensagem de alerta)
    private Notification getNotificacao(Context context, String mensagem,
                                        NotificationManager gerenciadorNotificacoes, PendingIntent preIntencao) {

        //Construindo a notificação
        Notification.Builder builder =
                new Notification.Builder(context.getApplicationContext())
                        .setContentText(mensagem)
                        .setTicker("Titulo")
                        .setAutoCancel(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(preIntencao);


        /*
         * Em versões (a partir da Oreo) as notificações PRECISAM ser
         * filtradas criando um canal de notificação com o comando a baixo
         *
         */

        //Criando canal de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String canalId = "YOUR_CHANNEL_ID";
            NotificationChannel canal =
                    new NotificationChannel(canalId, "Canal", NotificationManager.IMPORTANCE_DEFAULT);
            gerenciadorNotificacoes.createNotificationChannel(canal);
            builder.setChannelId(canalId);

        }


        return builder.build();
    }


}
