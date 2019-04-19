package ru.tinkoff.fintech.mobile;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.widget.Switch;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

//TODO(4) добавляем класс отвечающий за запуск тестов
@RunWith(AndroidJUnit4.class)
public final class AirplaneModeTest {

    //TODO(5) попробем написать простейший тест
    @Test
    public void openMenu_airplaneModeIfOff() {
        // TODO (6) получим инструментацию
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        // TODO (7) получим устройство для управления через инструментацию
        final UiDevice device = UiDevice.getInstance(instrumentation);
        // TODO (8) попробуем открыть меню и проверить airlineMode
        device.pressHome();
        device.pressBack();
        openTopMenu(device);
        assertAirPlaneMode(device, false);
        // TODO (9) Для обсуждения, что можно улучшить?
    }

    private void openTopMenu(UiDevice device) {
        device.drag(
                device.getDisplayWidth() / 2,
                10,
                device.getDisplayWidth() / 2,
                device.getDisplayHeight() / 2,
                100
        );
    }

    private void assertAirPlaneMode(UiDevice device, boolean isOn) {
        final BySelector airplaneModeSelector = By.clazz(Switch.class).desc("Airplane mode");
        final UiObject2 airplaneModeObject = device.findObject(airplaneModeSelector);
        Assert.assertEquals("Неверное состояние Airplane mode", airplaneModeObject.isChecked(), isOn);
    }

}
