package com.fintech.fasthub;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.SearchCondition;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertTrue;

public class FastHubApp {

    private static final String PACKAGE = "com.fastaccess.github.debug";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(5);
    private final UiDevice device;
    private final Context context;
    private final Intent intent;
    private String[] config;

    public FastHubApp(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        this.intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
        this.config = new FileReaderToArray().readLines("config.txt");
    }

    void open() throws TimeoutException {
        if (!device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + device.getLauncherPackageName());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);

        if (!device.wait(Until.hasObject(By.pkg(PACKAGE)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + PACKAGE);
        }

        if (device.hasObject(By.res(PACKAGE + ":id/mainCard"))) {
            login();
        }
    }

    void login() { // todo
        device.findObject(title("Basic Authentication")).click(); // com.fastaccess.github.debug:id/basicAuth
        setTextToField(config[0], "Username");
        setTextToField(config[1], "Password");
        clickSubmit();
        assertTrue(hasObjectWithText("FastHub Debug"));
    }

    void clickMenuItem(String itemTitle, String submenuTitle) throws UiObjectNotFoundException {
        openMenu();
        scrollToItem(itemTitle);
        device.findObject(By.res(PACKAGE + ":id/design_menu_item_text").text(itemTitle)).click();
        assertTrue(device.wait(Until.hasObject(title(submenuTitle)), TIMEOUT));
    }

    void clickMenuItem(String itemTitle) throws UiObjectNotFoundException {
        clickMenuItem(itemTitle, itemTitle);
    }

    private void openMenu() {
        System.out.println("11111111111111111111");
        assertTrue(hasObjectWithText("FastHub Debug"));
        device.findObject(By.desc("Navigate up")).click();
    }

    void clickApply() {
        int last = device.findObjects(By.res(PACKAGE + ":id/apply")).size() - 1;
        device.findObjects(By.res(PACKAGE + ":id/apply")).get(last).click();
    }

    void clickSubmit() {
        int last = device.findObjects(By.res(PACKAGE + ":id/submit")).size() - 1;
        device.findObjects(By.res(PACKAGE + ":id/submit")).get(last).click();
    }

    void clickLogin() {
        device.findObject(By.res(PACKAGE + ":id/login")).click();
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

    void setTextToField(String text, String fieldTitle) {
        device.findObject(By.text(fieldTitle)).findObject(By.clazz("android.widget.EditText")).setText(text);
    }

    boolean textFieldContainsText(String fieldName, String text) {
        return device.findObject(By.res(PACKAGE + ":id/" + fieldName.toLowerCase())).getText().contains(text);
    }

    int itemPosition(String listName, String item) {
        int result = 0;
        List<UiObject2> list =
                device.findObject(By.text(listName)).getParent().findObjects(By.res(PACKAGE + ":id/mal_item_text"));
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

}
