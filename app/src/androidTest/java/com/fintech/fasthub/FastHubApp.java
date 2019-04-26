package com.fintech.fasthub;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FastHubApp {

    private static final String PACKAGE = "com.fastaccess.github.debug";
    private static final String APP_NAME = "FastHub Debug";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(7);
    private final UiDevice device;
    private final Context context;
    private final Intent intent;

    public FastHubApp(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        this.intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
    }

    void open() throws TimeoutException {
        if (!device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + device.getLauncherPackageName());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        if (!device.wait(Until.hasObject(By.pkg(PACKAGE)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + PACKAGE);
        }
    }

    void clickMenuItem(String itemTitle) throws UiObjectNotFoundException {
        dragLeftMenu();
        scrollToItem(itemTitle);
        device.findObject(byId("design_menu_item_text").text(itemTitle)).click();
    }

    // работает через раз
    private void openLeftMenu() {
        device.findObject(By.desc("Navigate up")).click();
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

    void clickApply() {
        int last = device.findObjects(byId("apply")).size() - 1;
        device.findObjects(byId("apply")).get(last).click();
    }

    void clickSubmit() {
        int last = device.findObjects(byId("submit")).size() - 1;
        device.findObjects(byId("submit")).get(last).click();
    }

    void clickObjectWithText(String text) {
        device.findObject(By.text(text)).click();
    }

    void scrollForward(int steps) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        appViews.scrollForward(steps);
    }

    void scrollToItem(String elementText) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text(elementText));
    }

    boolean hasObjectWithText(String text) {
        return device.wait(Until.hasObject(By.text(text)), TIMEOUT);
    }

    boolean hasObjectWithTitle(String title) {
        return device.wait(Until.hasObject(title(title)), TIMEOUT);
    }

    void setTextToField(String text, String fieldTitle) {
        device.findObject(By.text(fieldTitle)).findObject(By.clazz("android.widget.EditText")).setText(text);
    }

    boolean textFieldContainsText(String fieldName, String text) {
        return device.findObject(byId(fieldName.toLowerCase())).getText().contains(text);
    }

    int itemPosition(String listName, String item) {
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

    String getAppName() {
        return APP_NAME;
    }

}
