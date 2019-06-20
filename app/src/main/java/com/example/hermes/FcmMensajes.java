package com.example.hermes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hermes.MainActivity;
import com.example.hermes.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

public class FcmMensajes extends FirebaseMessagingService {

    String tipo = "";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Intent intent = new Intent(this, MainActivity.class);
        if (remoteMessage.getData().size() > 0){
            tipo = "json";
            sendNotification(remoteMessage.getData().toString());
        }
        if (remoteMessage.getNotification() != null){
            tipo = "mensaje";
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String cuerpoDelMensaje){
        String id = "", mensaje = "", titulo = "";

        if (tipo.equals("json")) {
            try{
                JSONObject jsonObject = new JSONObject(cuerpoDelMensaje);
                id = jsonObject.getString("id");
                mensaje = jsonObject.getString("mensaje");
                titulo = jsonObject.getString("titulo");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (tipo.equals("mensaje")){

            mensaje = cuerpoDelMensaje;
        }

        int NOTIFICATION_ID = 234;

        NotificationManager notificationManagerA = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManagerA.createNotificationChannel(mChannel);

            Uri sonidoUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Hermes!")
                    .setContentText("Tú colectivo está llegando!!")
                    .setSmallIcon(R.drawable.ic_stat_name_xxxhdpi)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),R.drawable.logo_notificacion))
                    .setSound(sonidoUri)
                    .setAutoCancel(true);

            Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(1000);

            Intent resultIntent = new Intent(this, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentIntent(resultPendingIntent);

            notificationManagerA.notify(NOTIFICATION_ID, builder.build());

        }

//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//                intent, PendingIntent.FLAG_ONE_SHOT);
//        NotificationCompat.Builder constructorDeNotificion =
//                new NotificationCompat.Builder(this);
//        constructorDeNotificion.setContentTitle("");
//        constructorDeNotificion.setContentText("");
//        Uri sonidoUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        constructorDeNotificion.setSound(sonidoUri);
//        constructorDeNotificion.setSmallIcon(R.mipmap.ic_launcher);
//        constructorDeNotificion.setLargeIcon(
//                BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_launcher));
//        constructorDeNotificion.setAutoCancel(true);
//        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
//        v.vibrate(1000);
//        constructorDeNotificion.setContentIntent(pendingIntent);
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0,constructorDeNotificion.build());

    }
}
