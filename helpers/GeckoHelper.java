/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.Actions.EventExpecter;
import org.mozilla.gecko.tests.UITestContext;

import android.app.Activity;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.reflect.Method;

public final class GeckoHelper {
    private static final String GECKO_THREAD_CLASS = "org.mozilla.gecko.GeckoThread";
    private static final String LAUNCH_STATE_CLASS = GECKO_THREAD_CLASS + "$LaunchState";

    private static UITestContext sContext;
    private static Activity sActivity;
    private static Actions sActions;

    private GeckoHelper() { /* To disallow instantiation. */ }

    public static void init(final UITestContext context) {
        sContext = context;
        sActivity = context.getActivity();
        sActions = context.getActions();
    }

    public static void blockForReady() {
        try {
            final EventExpecter geckoReady = sActions.expectGeckoEvent("Gecko:Ready");

            // TODO: Bug 709230: Remove reflection?
            final ClassLoader cl = sActivity.getClassLoader();
            final Class geckoThreadClass = cl.loadClass(GECKO_THREAD_CLASS);
            final Class launchStateClass = cl.loadClass(LAUNCH_STATE_CLASS);

            final Method checkLaunchStateMethod =
                    geckoThreadClass.getMethod("checkLaunchState", launchStateClass);
            final boolean isReady = ((Boolean) checkLaunchStateMethod.invoke(null,
                    launchStateClass.getEnumConstants()[3])).booleanValue();

            if (!isReady) {
                geckoReady.blockForEvent();
            }

            geckoReady.unregisterListener();
        } catch (Exception e) {
            sContext.dumpLog("Exception in blockForGeckoReady", e);
        }
    }
}
