package com.the3rocks.karumitestingcourseexercisetwo;

interface Executor {
    void post(Runnable runnable);
}

public class SessionApiClient {

    private Executor executor;

    public SessionApiClient(Executor executor){
        this.executor = executor;
    }

    public void login(final String email, final String password, final LogInCallback callback){
        executor.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if(email != null
                        && password != null
                        && !email.isEmpty()
                        && !password.isEmpty()){
                    if(email.equals("Luis") && password.equals("password")){
                        callback.onSuccess();
                        return;
                    }
                }
                callback.onError();
            }
        });
    }

    public void logout(final LogOutCallback callback){
        executor.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (System.currentTimeMillis() % 2 == 0) {
                    callback.onSuccess();
                } else {
                    callback.onError();
                }

                return;
            }
        });
    }
}

interface LogInCallback{
    void onSuccess();
    void onError();
}

interface LogOutCallback{
    void onSuccess();
    void onError();
}