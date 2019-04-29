package com.fintech.fasthub.Espresso;


import com.fastaccess.R;
import com.fastaccess.ui.modules.main.MainActivity;
import com.fastaccess.ui.modules.trending.TrendingActivity;

import org.junit.Test;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.fintech.fasthub.Espresso.TrendingRecyclerViewMatchers.withItemTitle;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TrendingActivityTest extends ConcreteApplicationTest<MainActivity> {

    public TrendingActivityTest() {
        super(MainActivity.class);
    }

    @Test
    public void recyclerScroll() throws InterruptedException {
        // запустим нужный нам экран
        MainActivity activity = launch();
        // получаем доступ к активности
        assertThat("Вход в приложение не был выполнен", activity.isLoggedIn(), is(equalTo(true)));
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withText("Trending")).perform(click());
//        onView(withText("Trending")).check(matches());
    }

}