package com.fintech.fasthub.Espresso;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.fastaccess.R;
import com.fastaccess.helper.PrefHelper;
import com.fastaccess.ui.modules.main.donation.CheckPurchaseActivity;
import com.fintech.fasthub.Espresso.Application.ConcreteApplication;
import com.fintech.fasthub.Espresso.Matcher.DrawableMatcher;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withParentIndex;
import static android.support.test.espresso.matcher.ViewMatchers.withSubstring;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static org.junit.Assert.assertEquals;

public class MainActivity extends ConcreteApplication<com.fastaccess.ui.modules.main.MainActivity> {

    public MainActivity() {
        super(com.fastaccess.ui.modules.main.MainActivity.class);
    }

    @Rule
    public ActivityTestRule<com.fastaccess.ui.modules.main.MainActivity> mainActivityRule
            = new ActivityTestRule<>(com.fastaccess.ui.modules.main.MainActivity.class, true, true);

    @Rule
    public IntentsTestRule<CheckPurchaseActivity> intentsTestRule = new IntentsTestRule<>(CheckPurchaseActivity.class);

    @Test
    public void checkTrendingPage() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withText(R.string.trending)).perform(click());
        onView(allOf(withText(R.string.trending), withParent(withId(R.id.toolbar)))).check(matches(isDisplayed()));
    }

    @Test
    public void checkThemeChange() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.drawerViewPager)).perform(swipeUp());
        onView(withText(R.string.settings)).perform(click());
        onView(withText(R.string.theme_title)).perform(click());
        onView(withText("FastHub Premium Features")).check(matches(isDisplayed()));
        onView(withId(R.id.pager)).perform(swipeLeft());
        onView(withClassName(is("android.widget.TextView"))).check(doesNotExist());
        onView(allOf(withId(R.id.apply), isCompletelyDisplayed())).perform(click());
        assertEquals("Dark Theme", PrefHelper.getString("appTheme"));
    }

    @Test
    public void checkRestorePurchases() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.drawerViewPager)).perform(swipeUp());
        onView(withText(R.string.restore_purchases)).perform(click());
        intended(hasComponent(CheckPurchaseActivity.class.getName()));
    }

    @Test
    public void checkSendFeedback() throws InterruptedException {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withText(R.string.send_feedback)).perform(click());
        try {
            onView(withText("You are currently using a debug build")).check(matches(isDisplayed()));
            onView(withText(R.string.ok)).perform(click());
        } catch (NoMatchingViewException ignored) {
        }
        onView(withClassName(endsWith(".TextInputEditText"))).perform(typeText("hello"));
        onView(withId(R.id.description)).perform(click());
        onView(withText(R.string.markdown)).check(matches(isDisplayed()));
        onView(withId(R.id.submit)).perform(click(), closeSoftKeyboard());
        onView(withId(R.id.description)).check(matches(withSubstring("Xiaomi")));
        onView(withId(R.id.description)).check(matches(withSubstring("Redmi 3")));
        onView(withId(R.id.submit)).perform(click());
        onView(withText("Message was sent")).inRoot(withDecorView(not(is(mainActivityRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkMarkup() {
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.drawerViewPager)).perform(swipeUp());
        onView(withText(R.string.about)).perform(click());
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(allOf(withParent(withParent(withParent(withParent(withChild(withText(R.string.about)))))),
                withParent(withParent(withParentIndex(1))), withParent(withParentIndex(1)), withParentIndex(0), withId(R.id.mal_item_text)))
                .check(matches(withText(R.string.changelog)));
        onView(allOf(withParent(withParent(withParent(withChild(withText(R.string.about))))),
                withParent(withParentIndex(1)), withParentIndex(0), withId(R.id.mal_item_image)))
                .check(matches(withDrawable(R.drawable.ic_track_changes)));
    }

    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }
}