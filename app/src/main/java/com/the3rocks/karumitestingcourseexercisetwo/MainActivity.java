package com.the3rocks.karumitestingcourseexercisetwo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private SessionApiClient sessionApiClient;

    @BindView(R.id.activity_main)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.nameField)
    EditText nameField;

    @BindView(R.id.passwordField)
    EditText passwordField;

    @BindView(R.id.loginButton)
    EditText loginButton;

    @BindView(R.id.logoutButton)
    EditText logoutButton;

    @OnClick(R.id.loginButton)
    public void onLoginClick(){
        sessionApiClient.login(nameField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
            @Override
            public void onSuccess() {
                nameField.setVisibility(View.GONE);
                passwordField.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
                logoutButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                showSnackBarError("ERROOOOOOOR!!!");
            }
        });
    }

    @OnClick(R.id.logoutButton)
    public void onLogoutClick(){
        sessionApiClient.logout(new LogOutCallback() {
            @Override
            public void onSuccess() {
                nameField.setVisibility(View.VISIBLE);
                passwordField.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                logoutButton.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                showSnackBarError("ERROOOOOOOR!!!");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sessionApiClient = new SessionApiClient();


    }

    private void showSnackBarError(final String error){
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, error, Snackbar.LENGTH_LONG);

        snackbar.show();
    }
}
