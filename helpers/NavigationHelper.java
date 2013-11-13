/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.tests.StringHelper;

import android.text.TextUtils;

final public class NavigationHelper extends BaseHelper {
    private static String[] sPredefinedUrls = new String[] {
        StringHelper.ROBOCOP_BLANK_PAGE_01_URL,
        StringHelper.ROBOCOP_BLANK_PAGE_02_URL,
        StringHelper.ROBOCOP_BLANK_PAGE_03_URL
    };

    public static void enterAndLoadUrl(String url) {
        assertNotNull("url is not null", url);

        url = adjustIfPredefined(url);
        TOOLBAR.enterEditingMode()
               .enterUrl(url)
               .commitEditingMode();
    }

    /**
     * Returns a new URL with the docshell HTTP server host prefix if the given url is
     * predefined, otherwise returns the given url.
     */
    private static String adjustIfPredefined(final String url) {
        assertNotNull("url is not null", url);

        for (final String predefinedUrl : sPredefinedUrls) {
            if (TextUtils.equals(url, predefinedUrl)) {
                return sContext.getAbsoluteUrl(url);
            }
        }

        return url;
    }

    public static void goBack() {
        // TODO: Assert !editing?
        if (DeviceHelper.isTablet()) {
            TOOLBAR.pressBackButton(); // Waits for page load.
            return;
        }

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
            TOOLBAR.pressForwardButton(); // Waits for page load.
            return;
        }

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
