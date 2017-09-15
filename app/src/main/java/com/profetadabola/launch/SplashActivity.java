package com.profetadabola.launch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.profetadabola.R;
import com.profetadabola.api.API;
import com.profetadabola.api.model.User;
import com.profetadabola.signIn.SignInActivity;
import com.profetadabola.tools.PersistenceHawk;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.profetadabola.api.APITools.syncUser;

public class SplashActivity extends AppCompatActivity {

    private API mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loadingSplash();
    }

    private void loadingSplash() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        anim.reset();

        ImageView imgSplash = (ImageView) findViewById(R.id.img_splash);
        if (imgSplash != null) {
            imgSplash.clearAnimation();
            imgSplash.startAnimation(anim);
        }

        if(PersistenceHawk.getUser("syncUser") == null){
            syncUserProfeta();
        } else {
            starProfeta();
        }

    }

    private void syncUserProfeta() {
        mService = syncUser();
        mService.syncUser()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        starProfeta();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), R.string.error_conection_sync_user,
                                Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onNext(User user) {
                        persistUser(user);
                    }
                });
    }

    private void starProfeta() {
        Intent intent = new Intent(SplashActivity.this,
                SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    private void persistUser(User user) {
        PersistenceHawk.setUser("syncUser",user);
    }


}