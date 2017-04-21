package com.the3rocks.karumitestingcourseexercisetwo;


import android.view.View;

public class MainActivityPresenter {

    private View view;
    private SessionApiClient sessionApiClient;
    private boolean logged;

    MainActivityPresenter(SessionApiClient sessionApiClient) {
        this.sessionApiClient = sessionApiClient;
    }

    public void setView(View view) {
        if (view == null) {
            throw new RuntimeException("View cannot be null");
        } else {
            this.view = view;
        }
    }

    public void onLoginClick(String email, String password) {
        view.showProgressBar();
        view.lockLoginButton();
        sessionApiClient.login(email, password, new LogInCallback() {
            @Override
            public void onSuccess() {
                if (view.isReady()) {
                    view.hideProgressBar();
                    view.unlockLoginButton();
                    logged = false;
                    view.hideLoginButton();
                    view.hideLoginForm();
                    view.showLogoutButton();
                }
            }

            @Override
            public void onError() {
                if (view.isReady()) {
                    view.hideProgressBar();
                    view.showError("ERROOOOOOOR!!!!");
                    view.unlockLoginButton();
                }
            }
        });
    }

    public void onLogoutClick() {
        view.showProgressBar();
        view.lockLogoutButton();
        sessionApiClient.logout(new LogOutCallback() {
            @Override
            public void onSuccess() {
                if (view.isReady()) {
                    view.hideProgressBar();
                    view.unlockLogoutButton();
                    view.showLoginForm();
                    view.showLoginButton();
                    view.hideLogoutButton();
                    logged = false;
                }
            }

            @Override
            public void onError() {
                if (view.isReady()) {
                    view.hideProgressBar();
                    view.showError("ERROOOOOOOR!!!!");
                    view.unlockLogoutButton();
                }
            }
        });
    }


    public void setLoginState(boolean loginState) {
        this.logged = loginState;
    }

    public boolean getState() {
        return logged;
    }


    public interface View {
        void showProgressBar();

        void hideProgressBar();

        void lockLoginButton();

        void unlockLoginButton();

        void lockLogoutButton();

        void unlockLogoutButton();

        void showLoginForm();

        void hideLoginForm();

        void showLoginButton();

        void hideLoginButton();

        void showLogoutButton();

        void hideLogoutButton();

        void showError(String error);

        boolean isReady();
    }
}

