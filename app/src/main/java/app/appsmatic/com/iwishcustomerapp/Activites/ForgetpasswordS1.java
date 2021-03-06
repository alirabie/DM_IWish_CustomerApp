package app.appsmatic.com.iwishcustomerapp.Activites;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import app.appsmatic.com.iwishcustomerapp.R;
import app.appsmatic.com.iwishcustomerapp.SharedPrefs.SaveSharedPreference;

public class ForgetpasswordS1 extends AppCompatActivity {

    private ImageView logo,contbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_forgetpassword_s1);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }


        logo=(ImageView)findViewById(R.id.forgetpassword_logo);
        contbtn=(ImageView)findViewById(R.id.forgetpassword_cont_1);

        //Set image language for logo and login button
        if(SaveSharedPreference.getLangId(this).equals("ar")){
            logo.setImageResource(R.drawable.logoarabic);
            contbtn.setImageResource(R.drawable.continuebtnarabic);
        }else{
            logo.setImageResource(R.drawable.logo);
            contbtn.setImageResource(R.drawable.continuebtnenglish);
        }

        //Continue Button
        //Check Os Ver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           contbtn.setBackgroundResource(R.drawable.ripple);
        }
        contbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(),SecurityCode.class));
            }
        });
    }
}
