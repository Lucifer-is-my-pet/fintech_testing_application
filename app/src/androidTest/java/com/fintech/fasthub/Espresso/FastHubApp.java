package com.fintech.fasthub.Espresso;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.fastaccess.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertTrue;

public class FastHubApp {

    private static final String PACKAGE = "com.fastaccess.github.debug";
    private static final String APP_NAME = "FastHub Debug";
    private static final int APP_NAME_INT = R.string.app_name;
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(7);
    private final UiDevice device;
    private final Context context;
    private final Intent intent;

    public FastHubApp(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        this.intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
    }

    public void open() throws TimeoutException {
        assertTrue(device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)), TIMEOUT));

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        assertTrue(device.wait(Until.hasObject(By.pkg(PACKAGE)), TIMEOUT));
    }

    public void clickMenuItem(String itemTitle) throws UiObjectNotFoundException {
        dragLeftMenu();
//        scrollToItem(itemTitle);
//        device.findObject(byId("design_menu_item_text").text(itemTitle)).click();
        clickObjectWithText(itemTitle);
    }

    private void dragLeftMenu() {
        device.drag(
                1,
                device.getDisplayHeight() / 2,
                device.getDisplayWidth() / 2,
                device.getDisplayHeight() / 2,
                70
        );
    }

    public void clickApply() {
//        int last = onView(allOf(withId(R.id.apply))).size() - 1;
//        int last = device.findObjects(byId("apply")).size() - 1;
//        device.findObjects(byId("apply")).get(last).click();
    }

    public void clickSubmit() {
        onView(withId(R.id.submit)).perform(click());
//        int last = device.findObjects(byId("submit")).size() - 1;
//        device.findObjects(byId("submit")).get(last).click();
    }

    public void clickObjectWithText(String text) {
        onView(withText(text)).perform(click());
//        device.findObject(By.text(text)).click();
    }

    public void scrollToItem(String elementText) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text(elementText));
    }

    public boolean hasObjectWithText(String text) {
        return device.wait(Until.hasObject(By.text(text)), TIMEOUT);
    }

    public void hasObjectWithTitle(String title) {
        onView(withId(R.id.textView)).check(matches(withText(title)));
//        return device.wait(Until.hasObject(title(title)), TIMEOUT);
    }

    public void setTextToField(String text, String fieldTitle) {
        onView(allOf(withId(R.id.editText),
                isDescendantOfA(withText(fieldTitle)))).perform(typeText(text));
//        device.findObject(By.text(fieldTitle)).findObject(By.clazz("android.widget.EditText")).setText(text);
    }

    public boolean textFieldContainsText(String fieldName, String text) {
        return device.findObject(byId(fieldName.toLowerCase())).getText().contains(text);
    }

    public int itemPosition(String listName, String item) {
        int result = 0;
        List<UiObject2> list =
                device.findObject(By.text(listName)).getParent().findObjects(byId("mal_item_text"));
        for (UiObject2 listItem : list) {
            if (listItem.getText().equals(item)) {
                return list.indexOf(listItem) + 1;
            }
        }
        return result;
    }

    private BySelector title(String title) {
        return By.clazz("android.widget.TextView").text(title);
    }

    private BySelector byId(String id) {
        return By.res(PACKAGE + ":id/" + id);
    }

    public String getAppName() {
        return APP_NAME;
    }

}
