package com.lanrelay;
import android.app.*;import android.content.*;import android.os.*;import java.io.*;
public final class RelayService extends Service{
 static RelayServer server; static final String CH="relay";
 public void onCreate(){super.onCreate();NotificationManager n=getSystemService(NotificationManager.class);if(n!=null)n.createNotificationChannel(new NotificationChannel(CH,"LAN Relay",NotificationManager.IMPORTANCE_LOW));}
 public int onStartCommand(Intent i,int f,int id){startForeground(1,new Notification.Builder(this,CH).setSmallIcon(android.R.drawable.stat_sys_upload).setContentTitle("LAN Relay running").setContentText("Local HTTP server on port 8080").setOngoing(true).build());int p=i==null?8080:i.getIntExtra("port",8080);try{if(server==null)server=new RelayServer(new File(getFilesDir(),"www"),p,e->{});if(!server.isRunning())server.start();}catch(Exception e){stopSelf();}return START_STICKY;}
 public void onDestroy(){if(server!=null)server.stop();server=null;super.onDestroy();} public IBinder onBind(Intent i){return null;}
}
