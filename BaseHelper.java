/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.Assert;
import org.mozilla.gecko.Driver;
import org.mozilla.gecko.tests.UITestContext.Component;

import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;

public class BaseHelper {
    protected static UITestContext sContext;

    // TODO: These are included when AssertionHelper is imported statically. Make sure they're
    // not when the packages are created.
    protected static Activity sActivity;
    protected static Solo sSolo;
    protected static Driver sDriver;
    protected static Actions sActions;
    protected static Assert sAsserter;

    protected static AboutHomeComponent ABOUTHOME;
    protected static ToolbarComponent TOOLBAR;

    protected BaseHelper() { /* Do not instantiate - protected to allow access from subclasses. */ }

    public static void init(final UITestContext context) {
        sContext = context;

        sActivity = sContext.getActivity();
        sSolo = sContext.getSolo();
        sDriver = sContext.getDriver();
        sActions = sContext.getActions();
        sAsserter = sContext.getAsserter();

        ABOUTHOME = (AboutHomeComponent) sContext.getComponent(Component.ABOUTHOME);
        TOOLBAR = (ToolbarComponent) sContext.getComponent(Component.TOOLBAR);
    }
}
