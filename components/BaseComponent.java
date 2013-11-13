/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.components;

import org.mozilla.gecko.Actions;
import org.mozilla.gecko.tests.UITestContext;

import com.jayway.android.robotium.solo.Solo;

public abstract class BaseComponent {
    private final UITestContext mTestContext;
    protected final Solo mSolo;
    protected final Actions mActions;

    public BaseComponent(final UITestContext testContext) {
        mTestContext = testContext;
        mSolo = mTestContext.getSolo();
        mActions = mTestContext.getActions();
    }

    protected UITestContext getTestContext() {
        return mTestContext;
    }
}