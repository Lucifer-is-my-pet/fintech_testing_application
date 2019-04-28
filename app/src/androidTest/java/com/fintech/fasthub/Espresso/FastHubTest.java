package com.fintech.fasthub.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;

import com.fintech.fasthub.UIAutomator.FastHubApp;

import org.junit.Before;
import org.junit.Rule;

import java.util.concurrent.TimeoutException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class FastHubTest {

    private static FastHubApp app;

    @Before
    public void before() throws TimeoutException {
        app = new FastHubApp(UiDevice.getInstance(getInstrumentation()));
        app.open();
    }
}
