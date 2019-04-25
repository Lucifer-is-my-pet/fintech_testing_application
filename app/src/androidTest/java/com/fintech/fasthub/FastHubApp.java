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

import com.fastaccess.App;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertTrue;

public class FastHubApp {

    private static final String PACKAGE = "com.fastaccess.github.debug";
    private static final String APP_NAME = "FastHub Debug";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(5);
    private final UiDevice device;
    private final Context context;
    private final Intent intent;
    private String[] config;
    String RESOURCES_PATH = new StringBuilder().append(".").append(File.separator).append("app").append(File.separator).append("src")
                    .append(File.separator).append("androidTest").append(File.separator).append("java")
            .append(File.separator).append("com").append(File.separator).append("fintech")
            .append(File.separator).append("fasthub").append(File.separator).toString();

    public FastHubApp(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
        this.intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
        this.config = new FileReaderToArray().readLines(RESOURCES_PATH + "config.txt");
        System.out.println();
    }

    void open() throws TimeoutException, InterruptedException {
        if (!device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + device.getLauncherPackageName());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        sleep(300);

        if (!device.wait(Until.hasObject(By.pkg(PACKAGE)), TIMEOUT)) {
            throw new TimeoutException("Нет пакета " + PACKAGE);
        }

        if (device.hasObject(byId("mainCard"))) {
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

    void clickMenuItem(String itemTitle) throws UiObjectNotFoundException {
        openMenu();
        scrollToItem(itemTitle);
        device.findObject(byId("design_menu_item_text").text(itemTitle)).click();
    }

    private void openMenu() {
        device.findObject(By.desc("Navigate up")).click();
    }

    void clickApply() {
        int last = device.findObjects(byId("apply")).size() - 1;
        device.findObjects(byId("apply")).get(last).click();
    }

    void clickSubmit() {
        int last = device.findObjects(byId("submit")).size() - 1;
        device.findObjects(byId("submit")).get(last).click();
    }

    void clickLogin() {
        device.findObject(byId("login")).click();
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
