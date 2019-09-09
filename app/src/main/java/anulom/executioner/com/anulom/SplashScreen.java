package anulom.executioner.com.anulom;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        final AlertDialog alertDialog = new AlertDialog.Builder(SplashScreen.this).create();
        alertDialog.setMessage("Executioner App is not Compatible with your device ");
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                    }
                });

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    if(alertDialog.isShowing() && alertDialog!=null) {

                        alertDialog.dismiss();

                    }

                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    finish();
                }
                if(android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){


                    alertDialog.show();


                }



                // close this activity

            }
        }, SPLASH_TIME_OUT);
    }

}
