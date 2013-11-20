/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.components.ToolbarComponent;
import org.mozilla.gecko.tests.UITestContext;
import org.mozilla.gecko.tests.UITestContext.ComponentType;

import com.jayway.android.robotium.solo.Solo;

import android.text.TextUtils;

/**
 * Provides helper functionality for navigating around the Firefox UI. These functions will often
 * combine actions taken on multiple components to perform larger interactions.
 */
final public class NavigationHelper {
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

        url = adjustUrl(url);
        sToolbarComponent.enterEditingMode()
                         .enterUrl(url)
                         .commitEditingMode();
    }

    /**
     * Returns a new URL with the docshell HTTP server host prefix.
     */
    private static String adjustUrl(final String url) {
        assertNotNull("url is not null", url);

        if (!url.startsWith("about:")) {
            return sContext.getAbsoluteUrl(url);
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
                throw new UnsupportedOperationException("Not yet implemented.");
            }
        });
    }

    public static void reload() {
        // TODO: On tablets, press reload in TOOLBAR. Note that this is technically
        // an app menu item so tread carefully.
        //       On phones, press reload in APPMENU.
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}
