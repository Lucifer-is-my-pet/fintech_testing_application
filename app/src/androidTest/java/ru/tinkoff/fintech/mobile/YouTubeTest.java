package ru.tinkoff.fintech.mobile;

import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import org.junit.Test;

import ru.tinkoff.fintech.mobile.base.AbstractApplicationTest;
import ru.tinkoff.fintech.mobile.pages.YoutubePage;

public final class YouTubeTest extends AbstractApplicationTest {

    @Test
    public void search() throws UiObjectNotFoundException {
        YoutubePage app = new YoutubePage(getDevice());
        // TODO (13) открыть приложение, обсудить как оно окрывается
        // отключить addFlag открытым поиском, потом отключичть ожидание
        // тут же посмотреть постаивть ожидание в секундах и посммотреть дебагером
        app.open();
        app.clickSearch();
        app.enterSearchCondition("Мстители");
        app.clickViewAtPosition(0, ListView.class);
        // app.scrollForward(10); // TODO (14) после обсуждения удалить
        app.clickViewAtPosition(0, RecyclerView.class);
    }

}
