package ru.tinkoff.fintech.mobile.pages;

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
import android.support.v7.widget.RecyclerView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class YoutubePage {

    private static final String PACKAGE = "com.google.android.youtube";
    private static final long TIMEOUT = TimeUnit.SECONDS.toMillis(5);
    private final UiDevice device;
    private final Context context;

    public YoutubePage(UiDevice device) {
        this.device = device;
        this.context = InstrumentationRegistry.getInstrumentation().getContext();
    }

    public void open() {
        String launcherPackage = device.getLauncherPackageName();
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), TIMEOUT);
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), TIMEOUT);
    }

    public void clickSearch() {
        waitFindObject(By.desc("Search")).click();
    }

    public void enterSearchCondition(String text) {
        waitFindObject(By.text("Search YouTube")).setText(text);
    }

    public void clickViewAtPosition(int possition, Class clazz) {
        waitFindObject(By.clazz(clazz)).getChildren().get(possition).click();
    }

    public void scrollForward(int steps) throws UiObjectNotFoundException {
        final UiSelector uiSelector = new UiSelector().scrollable(true).className(RecyclerView.class);
        final UiScrollable recycler = new UiScrollable(uiSelector);
        recycler.scrollForward(steps);
    }

    private UiObject2 waitFindObject(BySelector byselector) {
        SearchCondition<UiObject2> condition = Until.findObject(byselector);
        return Objects.requireNonNull(device.wait(condition, TIMEOUT));
    }

}
