package com.fintech.fasthub;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FastHubTest {

    private static FastHubApp app;

    @BeforeClass
    public static void openAndLogin() throws TimeoutException {
        app = new FastHubApp(UiDevice.getInstance(getInstrumentation()));
        app.open();
    }

    @Before
    public void before() {
        // открыть главное меню
//        device = UiDevice.getInstance(getInstrumentation());
//        assertThat(device, notNullValue());
    }

    /**
     * Приоритет: Высокий
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkTrendingPage() throws UiObjectNotFoundException {
        app.clickMenuItem("Trending");
    }

    /**
     * Приоритет: Низкий
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkThemeChange() throws UiObjectNotFoundException {
        app.clickMenuItem("Settings");
        app.clickObjectWithText("Theme");
        assertTrue(app.hasObjectWithText("FastHub Premium Features"));
        app.scrollForward(2);
        app.clickApply();
        assertTrue(app.hasObjectWithText("FastHub")); // цвет не проверить
    }

    /**
     * Приоритет: Низкий
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkRestorePurchases() throws UiObjectNotFoundException {
        app.clickMenuItem("Restore Purchases", "FastHub");
        assertFalse(app.hasObjectWithText("Menu")); // Intent не проверить
    }

    /**
     * Приоритет: Средний
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkReportAnIssue() throws UiObjectNotFoundException {
        app.clickMenuItem("Report an issue", "Submit feedback");
        app.setTextToField("hello", "Title");
        app.clickObjectWithText("Description");
        app.clickSubmit();
        assertTrue(app.hasObjectWithText("Submit feedback"));
        assertTrue(app.textFieldContainsText("Description", "Xiaomi"));
        assertTrue(app.textFieldContainsText("Description", "Redmi 3"));
        app.clickSubmit();
        assertTrue(app.hasObjectWithText("Successfully submitted")); // Toast с текстом не детектится UI Automator Viewer'ом
    }

    /**
     * Приоритет: Средний
     * Предусловие – Пользователь залогинен в приложение
     */
    @Test
    public void checkMarkup() throws UiObjectNotFoundException {
        app.clickMenuItem("About", "FastHub");
        app.scrollToItem("About");
        assertEquals(2, app.itemPosition("About", "Changelog")); // иконку не проверить
    }
}
