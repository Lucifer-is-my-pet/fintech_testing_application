package com.fintech.fasthub.Espresso;

import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.fastaccess.ui.modules.main.MainActivity;
import com.fastaccess.ui.modules.trending.TrendingActivity;
import com.fintech.fasthub.UIAutomator.FastHubApp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeoutException;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

public class FastHubTrendingTest extends ConcreteApplicationTest<TrendingActivity> {

    private static FastHubApp app;

    public FastHubTrendingTest()
    {
        super(TrendingActivity.class);
    }


//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        getActivity();
//    }

    @Before
    public void before() throws TimeoutException {
//        TrendingActivity act = launch();
//        app = new FastHubApp(UiDevice.getInstance(getInstrumentation()));
//        app.open();
    }

    @Test
    public void checkTrendingPage() throws UiObjectNotFoundException {
        getDevice().drag(
                1,
                getDevice().getDisplayHeight() / 2,
                getDevice().getDisplayWidth() / 2,
                getDevice().getDisplayHeight() / 2,
                70
        );
//        app.clickMenuItem("Trending");
//        app.hasObjectWithTitle("Trending");
    }
}
