package com.fintech.fasthub.Espresso.Application;


import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

public class ConcreteApplication<T extends Activity> extends AbstractApplication {

    private final ActivityTestRule<T> rule;

    public ConcreteApplication(Class<T> clazz) {
        rule = new ActivityTestRule<>(clazz, true, false);
    }

    public T launch() {
        return rule.launchActivity(null);
    }

    public T launch(Intent intent) {
        return rule.launchActivity(intent);
    }

}