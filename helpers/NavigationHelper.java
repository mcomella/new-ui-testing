/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.components.ToolbarComponent;
import org.mozilla.gecko.tests.StringHelper;
import org.mozilla.gecko.tests.UITestContext;
import org.mozilla.gecko.tests.UITestContext.ComponentType;

import com.jayway.android.robotium.solo.Solo;

import android.text.TextUtils;

final public class NavigationHelper {
    private final static String[] PREDEFINED_URLS = new String[] {
        StringHelper.ROBOCOP_BLANK_PAGE_01_URL,
        StringHelper.ROBOCOP_BLANK_PAGE_02_URL,
        StringHelper.ROBOCOP_BLANK_PAGE_03_URL
    };

    private static UITestContext sContext;
    private static Solo sSolo;

    private static ToolbarComponent sToolbarComponent;

    public static void init(final UITestContext context) {
        sContext = context;
        sSolo = context.getSolo();

        sToolbarComponent = (ToolbarComponent) context.getComponent(ComponentType.TOOLBAR);
    }

    public static void enterAndLoadUrl(String url) {
        assertNotNull("url is not null", url);

        url = adjustIfPredefined(url);
        sToolbarComponent.enterEditingMode()
                         .enterUrl(url)
                         .commitEditingMode();
    }

    /**
     * Returns a new URL with the docshell HTTP server host prefix if the given url is
     * predefined, otherwise returns the given url.
     */
    private static String adjustIfPredefined(final String url) {
        assertNotNull("url is not null", url);

        for (final String predefinedUrl : PREDEFINED_URLS) {
            if (TextUtils.equals(url, predefinedUrl)) {
                return sContext.getAbsoluteUrl(url);
            }
        }

        return url;
    }

    public static void goBack() {
        if (DeviceHelper.isTablet()) {
            sToolbarComponent.pressBackButton(); // Waits for page load & asserts isNotEditing.
            return;
        }

        sToolbarComponent.assertIsNotEditing();
        WaitHelper.waitForPageLoad(new Runnable() {
            @Override
            public void run() {
                // TODO: Lower soft keyboard first if applicable. Note that
                // Solo.hideSoftKeyboard() does not clear focus (which might be fine since
                // Gecko would be the element focused).
                sSolo.goBack();
            }
        });
    }

    public static void goForward() {
        if (DeviceHelper.isTablet()) {
            sToolbarComponent.pressForwardButton(); // Waits for page load & asserts isNotEditing.
            return;
        }

        sToolbarComponent.assertIsNotEditing();
        WaitHelper.waitForPageLoad(new Runnable() {
            @Override
            public void run() {
                // TODO: Press forward with APPMENU component
            }
        });
    }

    public static void reload() {
        // TODO: On tablets, press reload in TOOLBAR. Note that this is technically
        // an app menu item so tread carefully.
        //       On phones, press reload in APPMENU.
    }
}
