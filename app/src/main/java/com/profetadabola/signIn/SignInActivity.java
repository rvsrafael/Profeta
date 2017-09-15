package com.profetadabola.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.profetadabola.Navigator;
import com.profetadabola.R;
import com.profetadabola.api.model.User;
import com.profetadabola.main.games.GameActivity;
import com.profetadabola.tools.InputValidator;
import com.profetadabola.tools.PersistenceHawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_password)
    EditText editTextPassword;

    @BindView(R.id.edit_text_username)
    EditText editTextUsername;

    @BindView(R.id.input_layout_username)
    TextInputLayout usernameLayout;

    @BindView(R.id.input_layout_password)
    TextInputLayout passwordLayout;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        isLoggedIn();
    }

    public boolean isLoggedInFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    private void setupFacebook() {

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)
                findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                //Toast.makeText(SignInActivity.this, "onSuccess - Deu bom", Toast.LENGTH_LONG).show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null){
                    Navigator.startGames(getApplicationContext(), profile.getFirstName());
                } else {
                    Navigator.startGames(getApplicationContext(), "");
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignInActivity.this, R.string.cancel_conection_login, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignInActivity.this, R.string.error_conection_login, Toast.LENGTH_LONG).show();

            }
        });
    }

    @OnClick(R.id.button_signin)
    void signIn(View view){
        User userAutenticator= null;
        String error = null;
        String user = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();


        if (InputValidator.isInputEmpty(user)){
            error = getString(R.string.empty_email);
            usernameLayout.setError(error);
        }
        if (InputValidator.isInputEmpty(password)){
            error = getString(R.string.empty_password);
            passwordLayout.setError(error);
        }


        if(PersistenceHawk.getUser("syncUser") != null) {
            userAutenticator = PersistenceHawk.getUser("syncUser");
        }

        if (password.equals(userAutenticator.getSenha()) &&
                user.equals(userAutenticator.getUsuario())){

            Navigator.startGames(getApplicationContext(), user);

        } else {

            Toast.makeText(this, R.string.error_incorrect_signin, Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void isLoggedIn() {

        if (isLoggedInFacebook()){
            Profile profile = Profile.getCurrentProfile();
            if (profile != null){
                Navigator.startGames(getApplicationContext(), profile.getFirstName());
            } else {
                Navigator.startGames(getApplicationContext(), "");
            }

        } else {
            setupFacebook();
        }

    }
}

