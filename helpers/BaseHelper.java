/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.Assert;
import org.mozilla.gecko.Driver;
import org.mozilla.gecko.tests.components.*;
import org.mozilla.gecko.tests.UITestContext;
import org.mozilla.gecko.tests.UITestContext.ComponentType;

import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;

public class BaseHelper {
    protected static UITestContext sContext;

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

        ABOUTHOME = (AboutHomeComponent) sContext.getComponent(ComponentType.ABOUTHOME);
        TOOLBAR = (ToolbarComponent) sContext.getComponent(ComponentType.TOOLBAR);
    }
}
