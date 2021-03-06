package app.appsmatic.com.iwishcustomerapp.Activites;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Locale;

import app.appsmatic.com.iwishcustomerapp.API.Models.LoginData;
import app.appsmatic.com.iwishcustomerapp.API.Models.Msg;
import app.appsmatic.com.iwishcustomerapp.API.RetrofitUtilities.ClintAppApi;
import app.appsmatic.com.iwishcustomerapp.API.RetrofitUtilities.Genrator;
import app.appsmatic.com.iwishcustomerapp.R;
import app.appsmatic.com.iwishcustomerapp.SharedPrefs.SaveSharedPreference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private ImageView logo,signinbtn;
    private EditText phonenum,password;
    private TextView forgetpass,signup;
    private LoginData loginData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setLang(R.layout.activity_sign_in);

        loginData=new LoginData();
        phonenum=(EditText)findViewById(R.id.phonenum_login);
        password=(EditText)findViewById(R.id.password_login);
        signinbtn=(ImageView)findViewById(R.id.loginbtn);




        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Check Os Ver For Set Status Bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }


        forgetpass=(TextView)findViewById(R.id.tv_forgetpass);
        signup=(TextView)findViewById(R.id.tv_newaccount);
        logo=(ImageView)findViewById(R.id.logo);
        signinbtn=(ImageView)findViewById(R.id.loginbtn);



        //Set image language for logo and login button
        if(SaveSharedPreference.getLangId(this).equals("ar")){
            logo.setImageResource(R.drawable.logoarabic);
            signinbtn.setImageResource(R.drawable.signinbuttonnar);
        }else{
            logo.setImageResource(R.drawable.logo);
            signinbtn.setImageResource(R.drawable.signinbtn);
        }

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.toptodown);
        final LinearLayout loginpanel=(LinearLayout)findViewById(R.id.loginlayout);
        loginpanel.clearAnimation();
        loginpanel.setAnimation(anim);



        //Sign in button Action
        //Check Os Ver
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            signinbtn.setBackgroundResource(R.drawable.ripple);
            forgetpass.setBackgroundResource(R.drawable.ripple);
            signup.setBackgroundResource(R.drawable.ripple);
        }
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if there is Empty Filed
                if(phonenum.getText().toString().equals("")||password.getText().toString().equals("")){


                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(SignIn.this);
                    dialogBuilder
                            .withTitle(getResources().getString(R.string.app_name))
                            .withDialogColor(R.color.colorPrimary)
                            .withTitleColor("#FFFFFF")
                            .withDuration(700)                                          //def
                            .withEffect(Effectstype.RotateBottom)
                            .withMessage(getResources().getString(R.string.dontleavefildes)+"")
                            .show();
                }else{



                    //Loading Dialog
                    final ProgressDialog mProgressDialog = new ProgressDialog(SignIn.this,R.style.AppCompatAlertDialogStyle);
                    mProgressDialog.setIndeterminate(true);
                    mProgressDialog.setMessage(Html.fromHtml("<font color=#FFFFFF><big>"+getApplicationContext().getResources().getString(R.string.login)+"</big></font>"));
                    mProgressDialog.show();

                    //Post Data Object Json to server
                    loginData.setUserName(phonenum.getText().toString() + "");
                    loginData.setPassword(password.getText().toString() + "");

                    Genrator.createService(ClintAppApi.class).Login(loginData).enqueue(new Callback<Msg>() {
                        @Override
                        public void onResponse(Call<Msg> call, Response<Msg> response) {


                            //If Server response successfully move to home Activity
                            if(response.isSuccessful()){

                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();
                                //Check if Message response have String Success move to Home Activity and send user ID
                                String code=response.body().getCode()+"";
                                if(!code.equals("0")){

                                    String fulltext=response.body().getUserid()+"";
                                    SaveSharedPreference.setOwnerId(SignIn.this,fulltext.substring(fulltext.indexOf(",")+1, fulltext.length()));
                                    Toast.makeText(SignIn.this,getResources().getString(R.string.loginsucess),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignIn.this, Home.class));
                                    Home.setUserProfileInfo(SignIn.this);
                                    SignIn.this.finish();

                                }else{


                                    if (mProgressDialog.isShowing())
                                        mProgressDialog.dismiss();

                                    NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(SignIn.this);
                                    dialogBuilder
                                            .withTitle(getResources().getString(R.string.app_name))
                                            .withDialogColor(R.color.colorPrimary)
                                            .withTitleColor("#FFFFFF")
                                            .withDuration(700)                                          //def
                                            .withEffect(Effectstype.RotateBottom)
                                            .withMessage(response.body().getMessage()+"")
                                            .show();

                                }


                            }else{

                                if (mProgressDialog.isShowing())
                                    mProgressDialog.dismiss();


                                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(SignIn.this);
                                dialogBuilder
                                        .withTitle(getResources().getString(R.string.app_name))
                                        .withDialogColor(R.color.colorPrimary)
                                        .withTitleColor("#FFFFFF")
                                        .withDuration(700)                                          //def
                                        .withEffect(Effectstype.RotateBottom)
                                        .withMessage(getResources().getString(R.string.Responsenotsucusess)+"")
                                        .show();


                            }




                        }

                        @Override
                        public void onFailure(Call<Msg> call, Throwable t) {
                            if (mProgressDialog.isShowing())
                                mProgressDialog.dismiss();



                            NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(SignIn.this);
                            dialogBuilder
                                    .withTitle(t.getMessage().toString())
                                    .withDialogColor(R.color.colorPrimary)
                                    .withTitleColor("#FFFFFF")
                                    .withDuration(700)                                          //def
                                    .withEffect(Effectstype.RotateBottom)
                                    .withMessage(getResources().getString(R.string.Responsenotsucusess)+"")
                                    .show();


                        }
                    });


                }


            }
        });




















        //Forgot password button
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Soon Message
                Toast.makeText(SignIn.this, getResources().getString(R.string.soon), Toast.LENGTH_LONG).show();

                //startActivity(new Intent(getApplication(),ForgetpasswordS1.class));
            }
        });

        //SignUp button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SignUp.class));
            }
        });






    }






    // Change language method
    public void setLang(int layout){
        String languageToLoad =SaveSharedPreference.getLangId(this);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(layout);
    }

}
