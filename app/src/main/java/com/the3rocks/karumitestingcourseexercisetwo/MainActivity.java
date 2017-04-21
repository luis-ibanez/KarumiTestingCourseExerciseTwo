package com.the3rocks.karumitestingcourseexercisetwo;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View{

    private boolean isReady = false;

    private MainActivityPresenter presenter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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

    @OnClick(R.id.loginButton)
    public void onLogin(){
        presenter.onLoginClick(
                nameField.getText().toString(),
                passwordField.getText().toString());
    }

    @OnClick(R.id.loginButton)
    public void onLogout(){
        presenter.onLogoutClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainActivityPresenter(new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runOnUiThread(runnable);
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        new Thread(runnable).run();
                    }
                },
                new Clock() {
                    @Override
                    public long getCurrentTimeMillis() {
                        return System.currentTimeMillis();
                    }
                })
        );

        presenter.setView(this);

        boolean logged = false;
        if(savedInstanceState != null){
            logged = savedInstanceState.getBoolean("logged", false);
        }

        presenter.setLoginState(logged);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isReady = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isReady = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("logged", presenter.getState());
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void lockLoginButton() {
        loginButton.setEnabled(false);
    }

    @Override
    public void unlockLoginButton() {
        loginButton.setEnabled(true);
    }

    @Override
    public void lockLogoutButton() {
        logoutButton.setEnabled(false);

    }

    @Override
    public void unlockLogoutButton() {
        logoutButton.setEnabled(true);
    }

    @Override
    public void showLoginForm() {
        nameField.setVisibility(View.VISIBLE);
        passwordField.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginForm() {
        nameField.setVisibility(View.GONE);
        passwordField.setVisibility(View.GONE);
    }

    @Override
    public void showLoginButton() {
        loginButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginButton() {
        loginButton.setVisibility(View.GONE);
    }

    @Override
    public void showLogoutButton() {
        logoutButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLogoutButton() {
        logoutButton.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public boolean isReady() {
        return isReady;
    }
}
