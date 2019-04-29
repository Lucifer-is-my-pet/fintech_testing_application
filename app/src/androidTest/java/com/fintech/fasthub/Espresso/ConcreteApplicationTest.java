package com.fintech.fasthub.Espresso;


import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

// создадим обертку для тестов
public class ConcreteApplicationTest<T extends Activity> extends AbstractApplicationTest {

    private final ActivityTestRule<T> rule;

    // создадим и не будем запускать правило
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