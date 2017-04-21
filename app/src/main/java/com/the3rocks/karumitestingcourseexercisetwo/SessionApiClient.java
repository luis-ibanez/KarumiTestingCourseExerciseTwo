package com.the3rocks.karumitestingcourseexercisetwo;


import android.os.AsyncTask;

public class SessionApiClient {

    public void login(final String email, final String password, final LogInCallback callback){
            AsyncTask tareitaBuena = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                    }

                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    if(email.equals("Luis") && password.equals("password")){
                        callback.onSuccess();
                    }else{
                        callback.onError();
                    }

                    return;
                }
            };

        tareitaBuena.execute();

    }

    public void logout(final LogOutCallback callback){

        AsyncTask tareitaMala = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (System.currentTimeMillis() % 2 == 0){
                    callback.onSuccess();
                }else{
                    callback.onError();
                }

                return;
            }
        };

        tareitaMala.execute();
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