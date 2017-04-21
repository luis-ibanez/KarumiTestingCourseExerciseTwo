package com.the3rocks.karumitestingcourseexercisetwo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private boolean logged;

    private SessionApiClient sessionApiClient;

    @BindView(R.id.activity_main)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.nameField)
    EditText nameField;

    @BindView(R.id.passwordField)
    EditText passwordField;

    @BindView(R.id.loginButton)
    Button loginButton;

    @BindView(R.id.logoutButton)
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.sessionApiClient = new SessionApiClient(new Executor() {
            @Override
            public void post(Runnable runnable) {
                new Thread(runnable);
            }
        });

        logged = savedInstanceState.getBoolean("logged", false);;

    }

    private void initStatus(){
        setLoginListener();
        setLogoutListeners();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("logged", logged);
    }



    private void showSnackBarError(final String error){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, error, Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    private void setLogoutListeners(){
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutButton.setOnClickListener(null);
                logoutButton.setEnabled(false);
                sessionApiClient.logout(new LogOutCallback() {
                    @Override
                    public void onSuccess() {
                        nameField.setVisibility(View.VISIBLE);
                        passwordField.setVisibility(View.VISIBLE);
                        loginButton.setVisibility(View.VISIBLE);
                        logoutButton.setVisibility(View.GONE);
                        setLogoutListeners();
                        logoutButton.setEnabled(true);
                        logged = false;
                    }

                    @Override
                    public void onError() {
                        showSnackBarError("ERROOOOOOOR!!!");
                        setLogoutListeners();
                        logoutButton.setEnabled(true);
                    }
                });
            }
        });
    }

    private void setLoginListener(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setOnClickListener(null);
                loginButton.setEnabled(false);
                sessionApiClient.login(nameField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
                    @Override
                    public void onSuccess() {
                        nameField.setVisibility(View.GONE);
                        passwordField.setVisibility(View.GONE);
                        loginButton.setVisibility(View.GONE);
                        logoutButton.setVisibility(View.VISIBLE);
                        setLoginListener();
                        loginButton.setEnabled(true);
                        logged = true;
                    }

                    @Override
                    public void onError() {
                        showSnackBarError("ERROOOOOOOR!!!");
                        setLoginListener();
                        loginButton.setEnabled(true);
                    }
                });
            }
        });
    }
}
