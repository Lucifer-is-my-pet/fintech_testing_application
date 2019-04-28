package com.fintech.fasthub.UIAutomator;

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

    public void open() throws TimeoutException {
        if (!device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + device.getLauncherPackageName());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        if (!device.wait(Until.hasObject(By.pkg(PACKAGE)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + PACKAGE);
        }
    }

    public void clickMenuItem(String itemTitle) throws UiObjectNotFoundException {
        dragLeftMenu();
        scrollToItem(itemTitle);
        device.findObject(byId("design_menu_item_text").text(itemTitle)).click();
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
        int last = device.findObjects(byId("apply")).size() - 1;
        device.findObjects(byId("apply")).get(last).click();
    }

    public void clickSubmit() {
        int last = device.findObjects(byId("submit")).size() - 1;
        device.findObjects(byId("submit")).get(last).click();
    }

    public void clickObjectWithText(String text) {
        device.findObject(By.text(text)).click();
    }

    public void scrollForward(int steps) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.setAsHorizontalList();
        appViews.scrollForward(steps);
    }

    public void scrollToItem(String elementText) throws UiObjectNotFoundException {
        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));
        appViews.scrollIntoView(new UiSelector().text(elementText));
    }

    public boolean hasObjectWithText(String text) {
        return device.wait(Until.hasObject(By.text(text)), TIMEOUT);
    }

    public boolean hasObjectWithTitle(String title) {
        return device.wait(Until.hasObject(title(title)), TIMEOUT);
    }

    public void setTextToField(String text, String fieldTitle) {
        device.findObject(By.text(fieldTitle)).findObject(By.clazz("android.widget.EditText")).setText(text);
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
