package com.fintech.fasthub.Espresso.Application;


import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AbstractApplication {

    public UiDevice getDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

}