package com.the3rocks.karumitestingcourseexercisetwo;

interface Executor {
    void post(Runnable runnable);
}

interface  MainThreadExecutor{
    void postInMainThread(Runnable runnable);
}

public class SessionApiClient {

    private MainThreadExecutor mainThreadExecutor;
    private Executor executor;
    private Clock clock;

    public SessionApiClient(MainThreadExecutor mainThreadExecutor, Executor executor, Clock clock) {
        this.mainThreadExecutor = mainThreadExecutor;
        this.executor = executor;
        this.clock = clock;
    }

    public void login(final String email, final String password, final LogInCallback callback) {
        executor.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (email != null
                        && password != null
                        && !email.isEmpty()
                        && !password.isEmpty()) {
                    if (email.equals("Luis") && password.equals("password")) {
                        returnLoginOnSuccess(callback);
                        return;
                    }
                }
                returnLoginOnError(callback);
            }
        });
    }

    public void logout(final LogOutCallback callback) {
        executor.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (clock.getCurrentTimeMillis() % 2 == 0) {
                    returnLogoutOnSuccess(callback);
                } else {
                    returnLogoutOnError(callback);
                }
            }
        });
    }

    private void returnLogoutOnSuccess(final LogOutCallback logOutCallback){
        mainThreadExecutor.postInMainThread(new Runnable() {
            @Override
            public void run() {
                logOutCallback.onSuccess();
            }
        });
    }

    private void returnLogoutOnError(final LogOutCallback logOutCallback){
        mainThreadExecutor.postInMainThread(new Runnable() {
            @Override
            public void run() {
                logOutCallback.onError();
            }
        });
    }

    private void returnLoginOnSuccess(final LogInCallback logInCallback){
        mainThreadExecutor.postInMainThread(new Runnable() {
            @Override
            public void run() {
                logInCallback.onSuccess();
            }
        });
    }

    private void returnLoginOnError(final LogInCallback logInCallback){
        mainThreadExecutor.postInMainThread(new Runnable() {
            @Override
            public void run() {
                logInCallback.onError();
            }
        });
    }
}

interface LogInCallback {
    void onSuccess();
    void onError();
}

interface LogOutCallback {
    void onSuccess();
    void onError();
}