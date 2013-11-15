/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.UITestContext;

import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import java.lang.Class;
import java.lang.ClassLoader;
import java.lang.reflect.Method;

public final class DeviceHelper {
    private static final String APP_SHELL_CLASS = "org.mozilla.gecko.GeckoAppShell";

    public enum Type {
        PHONE,
        TABLET
    }

    public enum AndroidVersion {
        v2x,
        v3x,
        v4x
    }

    private static UITestContext sContext;
    private static Activity sActivity;
    private static Solo sSolo;

    private static Type sDeviceType;
    private static AndroidVersion sAndroidVersion;

    private static int sScreenHeight;
    private static int sScreenWidth;

    private DeviceHelper() { /* To disallow instantiation. */ }

    public static void assertIsTablet() {
        assertTrue("The device is a tablet", isTablet());
    }

    public static void init(final UITestContext context) {
        sContext = context;
        sActivity = context.getActivity();
        sSolo = context.getSolo();

        setAndroidVersion();
        setScreenDimensions();
        setDeviceType();
    }

    private static void setAndroidVersion() {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            sAndroidVersion = AndroidVersion.v2x;
        } else if (sdk > Build.VERSION_CODES.HONEYCOMB_MR2) {
            sAndroidVersion = AndroidVersion.v4x;
        } else {
            sAndroidVersion = AndroidVersion.v3x;
        }
    }

    private static void setScreenDimensions() {
        final DisplayMetrics dm = new DisplayMetrics();
        sActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        sScreenHeight = dm.heightPixels;
        sScreenWidth = dm.widthPixels;
    }

    private static void setDeviceType() {
        boolean isTablet = false;
        try {
            // TODO: Bug 709230: Remove reflection?
            final ClassLoader cl = sActivity.getClassLoader();
            final Class appShellClass = cl.loadClass(APP_SHELL_CLASS);
            final Method isTabletMethod = appShellClass.getMethod("isTablet");
            isTablet = ((Boolean) isTabletMethod.invoke(null)).booleanValue();
        } catch (Exception e) {
            sContext.dumpLog("Exception in detectDevice", e);
        }

        sDeviceType = (isTablet ? Type.TABLET : Type.PHONE);
    }

    public static int getScreenHeight() {
        return sScreenHeight;
    }

    public static int getScreenWidth() {
        return sScreenWidth;
    }

    public static AndroidVersion getAndroidVersion() {
        return sAndroidVersion;
    }

    public static boolean isPhone() {
        return (sDeviceType == Type.PHONE);
    }

    public static boolean isTablet() {
        return (sDeviceType == Type.TABLET);
    }

    public static void setLandscapeRotation() {
        sSolo.setActivityOrientation(Solo.LANDSCAPE);
    }

    public static void setPortraitOrientation() {
        sSolo.setActivityOrientation(Solo.LANDSCAPE);
    }
}
