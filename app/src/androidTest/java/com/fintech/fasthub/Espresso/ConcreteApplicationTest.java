package com.fintech.fasthub.Espresso;

import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

public class ConcreteApplicationTest<T extends Activity> extends AbstractApplicationTest {

    private final ActivityTestRule<T> rule;

    public ConcreteApplicationTest(Class<T> clazz) {
        rule = new ActivityTestRule<>(clazz, true, false);
    }

    public T launch() {
        return rule.launchActivity(null);
    }

    public T launch(Intent intent) {
        return rule.launchActivity(intent);
    }

}
