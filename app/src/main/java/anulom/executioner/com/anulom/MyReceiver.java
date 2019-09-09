
package anulom.executioner.com.anulom;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


public class MyReceiver extends BroadcastReceiver {
    int i = 10;
    int icon_color = 0xFF4500, not_idd;
    String rkey, id, con, datevalue, contentfromreceiver,amount, news, not_id, from, content, poststatus, type;
    Context Context;

    long when = System.currentTimeMillis();

    @Override
    public void onReceive(Context context, Intent intent) {
System.out.println("My receiver");

        type = intent.getStringExtra("type");


        if (type.equals("2")) {
            System.out.println("My receiver"+type);

            rkey = intent.getStringExtra("rkey");
            id = intent.getStringExtra("document_id");
            from = intent.getStringExtra("from");
            content = intent.getStringExtra("content");
            poststatus = intent.getStringExtra("post_status");
            news = intent.getStringExtra("news");
            not_id = intent.getStringExtra("appointment_id");
            not_idd = Integer.valueOf(not_id);
            con = intent.getStringExtra("con");
            datevalue = intent.getStringExtra("StartDate");
            amount=intent.getStringExtra("payamount");
            System.out.println("docid:"+id);

            Intent notificationIntent = new Intent(context, newstatusinfo.class);
            notificationIntent.putExtra("rkey", rkey);
            notificationIntent.putExtra("document_id", id);
            notificationIntent.putExtra("from", from);
            notificationIntent.putExtra("post_status", poststatus);
            notificationIntent.putExtra("content", con);
            notificationIntent.putExtra("StartDate", datevalue);
            notificationIntent.putExtra("amount", amount);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(newstatusinfo.class);
            stackBuilder.addNextIntent(notificationIntent);


            PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) when, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID");
            builder.setContentTitle( news + " " + rkey)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel("CHANNEL_ID", "Executioner App", importance);

                System.out.println("My receiver:"+content);


                mChannel.setDescription(content);
                mChannel.enableLights(true);
                mNotificationManager.createNotificationChannel(mChannel);
                Notification notification = builder.setAutoCancel(true)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setColor(icon_color)
                        .setPriority(2)
                        .setSound(Uri.parse("android.resource://"
                                + context.getPackageName() + "/"
                                + R.raw.linesound))
                        .setWhen(when)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pendingIntent).build();
                mNotificationManager.notify((int) when, notification);

            }else {
                System.out.println("My receiver1"+type);

                Notification notification = builder.setAutoCancel(true)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setOngoing(false)
                        .setColor(icon_color)
                        .setPriority(2)
                        .setSound(Uri.parse("android.resource://"
                                + context.getPackageName() + "/"
                                + R.raw.linesound))
                        .setWhen(when)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pendingIntent).build();
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
                notificationManager.notify((int) when, notification);
            }


        } else if (type.equals("Marriage")) {


            rkey = intent.getStringExtra("rkey");
            id = intent.getStringExtra("document_id");
            from = intent.getStringExtra("from");
            content = intent.getStringExtra("content");
            poststatus = intent.getStringExtra("post_status");
            news = intent.getStringExtra("news");
            not_id = intent.getStringExtra("appointment_id");
            not_idd = Integer.valueOf(not_id);

            Intent notificationIntent = new Intent(context, marriagestatusinfo.class);

            notificationIntent.putExtra("rkey", rkey);
            notificationIntent.putExtra("document_id", id);
            notificationIntent.putExtra("from", from);
            notificationIntent.putExtra("post_status", poststatus);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(marriagestatusinfo.class);
            stackBuilder.addNextIntent(notificationIntent);


            PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) when, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID");

            notificationBuilder.setAutoCancel(true)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setColor(icon_color)
                    .setPriority(2)
                    .setSound(Uri.parse("android.resource://"
                            + context.getPackageName() + "/"
                            + R.raw.linesound))
                    .setWhen(when)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) when, notificationBuilder.build());


        }  else if (type.equals("MyTask")) {


            rkey = intent.getStringExtra("rkey");
            from = intent.getStringExtra("from");
            content = intent.getStringExtra("content");
            news = intent.getStringExtra("news");
            not_id = intent.getStringExtra("task_id");
            not_idd = Integer.valueOf(not_id);

            Intent notificationIntent = new Intent(context, taskcomment.class);

            notificationIntent.putExtra("task_id", not_id);
            notificationIntent.putExtra("from", from);
            notificationIntent.putExtra("Value", type);


            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(taskcomment.class);
            stackBuilder.addNextIntent(notificationIntent);


            PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) when, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID");

            notificationBuilder.setAutoCancel(true)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setColor(icon_color)
                    .setPriority(2)
                    .setSound(Uri.parse("android.resource://"
                            + context.getPackageName() + "/"
                            + R.raw.linesound))
                    .setWhen(when)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) when, notificationBuilder.build());


        } else if (type.equals("Witness")) {


            rkey = intent.getStringExtra("rkey");
            from = intent.getStringExtra("from");
            content = intent.getStringExtra("content");
            news = intent.getStringExtra("news");
            not_id = intent.getStringExtra("task_id");
            not_idd = Integer.valueOf(not_id);

            Intent notificationIntent = new Intent(context, taskcomment.class);

            notificationIntent.putExtra("task_id", not_id);
            notificationIntent.putExtra("from", from);
            notificationIntent.putExtra("Value", type);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(taskcomment.class);
            stackBuilder.addNextIntent(notificationIntent);


            PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) when, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID");

            notificationBuilder.setAutoCancel(true)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setColor(icon_color)
                    .setPriority(2)
                    .setSound(Uri.parse("android.resource://"
                            + context.getPackageName() + "/"
                            + R.raw.linesound))
                    .setWhen(when)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) when, notificationBuilder.build());

        } else if (type.equals("Complaint")) {


            rkey = intent.getStringExtra("rkey");
            from = intent.getStringExtra("from");
            content = intent.getStringExtra("content");
            news = intent.getStringExtra("news");
            not_id = intent.getStringExtra("task_id");
            not_idd = Integer.valueOf(not_id);

            Intent notificationIntent = new Intent(context, taskcomment.class);

            notificationIntent.putExtra("task_id", not_id);
            notificationIntent.putExtra("from", from);
            notificationIntent.putExtra("Value", type);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(taskcomment.class);
            stackBuilder.addNextIntent(notificationIntent);


            PendingIntent pendingIntent = stackBuilder.getPendingIntent((int) when, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID");

            notificationBuilder.setAutoCancel(true)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setOngoing(false)
                    .setColor(icon_color)
                    .setPriority(2)
                    .setSound(Uri.parse("android.resource://"
                            + context.getPackageName() + "/"
                            + R.raw.linesound))
                    .setWhen(when)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(R.drawable.icon)
                    .setContentIntent(pendingIntent).build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) when, notificationBuilder.build());

        }

    }


}


