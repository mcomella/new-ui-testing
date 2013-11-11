/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import com.jayway.android.robotium.solo.Solo;

public final class GestureHelper extends BaseHelper {
    private static int DEFAULT_DRAG_STEP_COUNT = 10;

    private GestureHelper() { /* To disallow instantation. */ }

    private static void swipeOnScreen(final int direction) {
        final int halfWidth = sDriver.getGeckoWidth() / 2;
        final int halfHeight = sDriver.getGeckoHeight() / 2;

        sSolo.drag(direction == Solo.LEFT ? halfWidth : 0,
                     direction == Solo.LEFT ? 0 : halfWidth,
                     halfHeight, halfHeight, DEFAULT_DRAG_STEP_COUNT);
    }

    public static void swipeLeft() {
        swipeOnScreen(Solo.LEFT);
    }

    public static void swipeRight() {
        swipeOnScreen(Solo.RIGHT);
    }
}
