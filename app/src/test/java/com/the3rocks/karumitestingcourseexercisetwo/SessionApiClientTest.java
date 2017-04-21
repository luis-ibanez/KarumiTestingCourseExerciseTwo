package com.the3rocks.karumitestingcourseexercisetwo;


import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SessionApiClientTest {

    private static String EMPTY_STRING;
    private static String ANY_STRING = "hola";
    private static String VALID_USERNAME = "Luis";
    private static String VALID_PASSWORD = "password";

    @Test
    public void shold_return_error_if_username_is_empty() throws Exception {
        LogInCallback callback = mock(LogInCallback.class);

        SessionApiClient sessionApiClient = new SessionApiClient(new Executor() {
            @Override
            public void post(Runnable runnable) {
                runnable.run();
            }
        });

        sessionApiClient.login(EMPTY_STRING, VALID_PASSWORD, callback);

        verify(callback).onError();
        verify(callback, never()).onSuccess();
    }
}
