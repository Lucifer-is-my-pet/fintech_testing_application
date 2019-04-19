package ru.tinkoff.fintech.mobile.base;

import android.Manifest;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AbstractApplicationTest {

    // TODO (10) вынесем логику полученмя устройства
    @Before
    public void setUp() {
        grantPermissions();
        getDevice().pressHome();
        getDevice().pressBack();
    }

    public UiDevice getDevice() {
        return UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    // TODO (11) управляем разрещениями
    @RequiresApi(21)
    private void grantPermissions() {
        // TODO (12) получим контекст и обсудим чем они отличается
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        try {
            getDevice().executeShellCommand(
                    String.format(
                            "pm grant %s %s",
                            context.getPackageName(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
            );
            getDevice().executeShellCommand(
                    String.format(
                            "pm grant %s %s",
                            targetContext.getPackageName(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
            );
        } catch (IOException e) {
            Log.e("AbstractApplicationTest", "Unable to grant permissions");
        }
    }

}
