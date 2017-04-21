package com.the3rocks.karumitestingcourseexercisetwo;


import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SessionApiClientTest {

    private static String EMPTY_STRING = "";
    private static String ANY_STRING = "hola";
    private static String VALID_USERNAME = "Luis";
    private static String VALID_PASSWORD = "password";

    @Test
    public void shold_return_error_if_username_is_empty() throws Exception {
        LogInCallback callback = mock(LogInCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return 2000;
            }
        });

        sessionApiClient.login(EMPTY_STRING, VALID_PASSWORD, callback);

        verify(callback).onError();
        verify(callback, never()).onSuccess();
    }

    @Test
    public void shold_return_error_if_username_is_null() throws Exception {
        LogInCallback callback = mock(LogInCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return 2000;
            }
        });

        sessionApiClient.login(null, VALID_PASSWORD, callback);

        verify(callback).onError();
        verify(callback, never()).onSuccess();
    }

    @Test
    public void shold_return_error_if_username_and_pass_incorrect() throws Exception {
        LogInCallback callback = mock(LogInCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return 2000;
            }
        });

        sessionApiClient.login(ANY_STRING, ANY_STRING, callback);

        verify(callback).onError();
        verify(callback, never()).onSuccess();
    }

    @Test
    public void should_return_success_if_valid_login() throws Exception {
        LogInCallback callback = mock(LogInCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return 2000;
            }
        });

        sessionApiClient.login(VALID_USERNAME, VALID_PASSWORD, callback);

        verify(callback).onSuccess();
        verify(callback, never()).onError();
    }

    @Test
    public void returnOnSuccessDuringTheLogOutIfTheMilliseconsIsEven() throws Exception {
        LogOutCallback logOutCallback = mock(LogOutCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
            @Override
            public long getCurrentTimeMillis() {
                return 2000;
            }
        });

        sessionApiClient.logout(logOutCallback);

        verify(logOutCallback).onSuccess();
        verify(logOutCallback, never()).onError();
    }

    @Test
    public void returnOnErrorDuringTheLogOutIfTheMilliseconsIsOdd() throws Exception {
        LogOutCallback logOutCallback = mock(LogOutCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(
                new MainThreadExecutor() {
                    @Override
                    public void postInMainThread(Runnable runnable) {
                        runnable.run();
                    }
                },
                new Executor() {
                    @Override
                    public void post(Runnable runnable) {
                        runnable.run();
                    }
                }, new Clock() {
                    @Override
                    public long getCurrentTimeMillis() {
                        return 2001;
                    }
                });

        sessionApiClient.logout(logOutCallback);

        verify(logOutCallback).onError();
        verify(logOutCallback, never()).onSuccess();
    }
}
