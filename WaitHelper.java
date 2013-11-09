/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.AssertionHelper.*;

import org.mozilla.gecko.Actions.EventExpecter;

import com.jayway.android.robotium.solo.Condition;

final class WaitHelper extends BaseHelper {
    /**
     * Performs the given action to start an event to be waited for. Implementations of this
     * interface are used in methods that need to check for changes in state from before the
     * given initiating action to after it.
     */
    public static interface InitiatingAction {
        public void doAction();
    }

    private static final int DEFAULT_MAX_WAIT_MS = 5000;
    private static final int PAGE_LOAD_WAIT_MS = 10000;
    private static final int CHANGE_WAIT_MS = 2000;

    private static ChangeVerifier[] sPageLoadVerifiers = new ChangeVerifier[] {
        new ToolbarTitleTextChangeVerifier()
    };

    private WaitHelper() { /* To disallow instantiation. */ }

    /**
     * Waits for the given {@link solo.Condition} using the default wait duration; will throw an
     * AssertionError if the duration is elapsed and the condition is not satisfied.
     */
    public static void waitFor(String message, final Condition condition) {
        message = "Waiting for " + message + ".";
        assertTrue(message, sSolo.waitForCondition(condition, DEFAULT_MAX_WAIT_MS));
    }

    /**
     * Waits for the given {@link solo.Condition} using the given wait duration; will throw an
     * AssertionError if the duration is elapsed and the condition is not satisfied.
     */
    public static void waitFor(String message, final Condition condition, final int waitMillis) {
        message = "Waiting for " + message + " with timeout " + waitMillis + ".";
        assertTrue(message, sSolo.waitForCondition(condition, waitMillis));
    }

    static void waitForPageLoad(final InitiatingAction initiatingAction) {
        assertNotNull("Asserting initiatingAction is not null", initiatingAction);

        // Some changes to the UI occur in response to the same event we listen to for when
        // the page has finished loading (e.g. a page title update). As such, we ensure this
        // UI state has changed before returning from this method; here we store the initial
        // state.
        final ChangeVerifier[] pageLoadVerifiers = sPageLoadVerifiers;
        for (final ChangeVerifier verifier : pageLoadVerifiers) {
            verifier.storeState();
        }

        // Wait for the page load event.
        final EventExpecter contentEventExpecter = sActions.expectGeckoEvent("DOMContentLoaded");
        initiatingAction.doAction();
        contentEventExpecter.blockForEventDataWithTimeout(PAGE_LOAD_WAIT_MS);
        contentEventExpecter.unregisterListener();

        // Verify remaining state has changed.
        for (final ChangeVerifier verifier : pageLoadVerifiers) {
            // If we timeout, either the state is set to the same value (which is fine), or
            // the state has not yet changed. Since we can't be sure it will ever change, move
            // on and let the assertions fail if applicable.
            final boolean hasTimedOut = !sSolo.waitForCondition(new Condition() {
                @Override
                public boolean isSatisfied() {
                    return verifier.hasStateChanged();
                }
            }, CHANGE_WAIT_MS);

            if (hasTimedOut) {
                // TODO: Bug 709230: Remove reflection?
                sContext.dumpLog(verifier.getClass().getName() + " timed out.");
            }
        }
    }

    /**
     * Implementations of this interface verify that the state of the test has changed from
     * the invocation of storeState to the invocation of hasStateChanged. A boolean will be
     * returned from hasStateChanged, indicating this change of status.
     */
    private static interface ChangeVerifier {
        public void storeState();
        public boolean hasStateChanged();
    }

    private static class ToolbarTitleTextChangeVerifier implements ChangeVerifier {
        private CharSequence oldTitleText;

        @Override
        public void storeState() {
            oldTitleText = TOOLBAR.getTitle();
        }

        @Override
        public boolean hasStateChanged() {
            // TODO: Robocop sleeps .5 sec between calls. Cache title view?
            return !oldTitleText.equals(TOOLBAR.getTitle());
        }
    }
}
