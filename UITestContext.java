/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.Assert;
import org.mozilla.gecko.Driver;
import org.mozilla.gecko.tests.components.BaseComponent;

import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.app.Instrumentation;

public interface UITestContext {
    public Activity getActivity();
    public Solo getSolo();
    public Assert getAsserter();
    public Driver getDriver();
    public Actions getActions();
    public Instrumentation getInstrumentation();

    public void dumpLog(final String message);
    public void dumpLog(final String message, final Throwable t);

    public String getAbsoluteUrl(final String url);
    public String getAbsoluteRawUrl(final String url);

    public static enum ComponentType {
        ABOUTHOME,
        TOOLBAR
    }

    public BaseComponent getComponent(final ComponentType type);
}
