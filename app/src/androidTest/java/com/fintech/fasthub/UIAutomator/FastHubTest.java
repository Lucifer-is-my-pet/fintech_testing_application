package com.fintech.fasthub.UIAutomator;

import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class FastHubTest {

    private static FastHubApp app;

    @Before
    public void before() throws TimeoutException {
        app = new FastHubApp(UiDevice.getInstance(getInstrumentation()));
        app.open();
    }

    @Test
    public void checkTrendingPage() throws UiObjectNotFoundException {
        app.clickMenuItem("Trending");
        assertTrue(app.hasObjectWithTitle("Trending"));
    }

    @Test
    public void checkThemeChange() throws UiObjectNotFoundException {
        app.clickMenuItem("Settings");
        assertTrue(app.hasObjectWithTitle("Settings"));
        app.clickObjectWithText("Theme");
        assertTrue(app.hasObjectWithText("FastHub Premium Features"));
        app.scrollForward(2);
        app.clickApply();
        assertTrue(app.hasObjectWithText(app.getAppName())); // цвет не проверить
    }

    @Test
    public void checkRestorePurchases() throws UiObjectNotFoundException {
        app.clickMenuItem("Restore Purchases");
        assertTrue(app.hasObjectWithTitle(app.getAppName())); // Intent не проверить
    }

    @Test
    public void checkReportAnIssue() throws UiObjectNotFoundException {
        app.clickMenuItem("Send feedback");
        assertTrue(app.hasObjectWithText("You are currently using a debug build"));
        app.clickObjectWithText("OK");
        assertTrue(app.hasObjectWithTitle("Submit feedback"));
        app.setTextToField("hello", "Title");
        app.clickObjectWithText("Description");
        app.clickSubmit();
        assertTrue(app.hasObjectWithTitle("Submit feedback"));
        assertTrue(app.textFieldContainsText("Description", "Xiaomi"));
        assertTrue(app.textFieldContainsText("Description", "Redmi 3"));
        app.clickSubmit(); // Toast с текстом не детектится UI Automator Viewer'ом
    }

    @Test
    public void checkMarkup() throws UiObjectNotFoundException {
        app.clickMenuItem("About");
        assertTrue(app.hasObjectWithTitle(app.getAppName()));
        app.scrollToItem("About");
        assertEquals(2, app.itemPosition("About", "Changelog")); // иконку не проверить
    }
}
