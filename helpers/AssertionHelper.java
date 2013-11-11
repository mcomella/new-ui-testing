/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.helpers;

import org.mozilla.gecko.Assert;

// TODO: Add ispixel assertions.
public final class AssertionHelper extends BaseHelper {
    private AssertionHelper() { /* To disallow instantation. */ }

    public static void assertEquals(final String message, final Object expected, final Object actual) {
        sAsserter.is(actual, expected, message);
    }

    public static void assertEquals(final String message, final int expected, final int actual) {
        sAsserter.is(actual, expected, message);
    }

    public static void assertFalse(final String message, final boolean actual) {
        // TODO: What is diag? (Also assertTrue)
        sAsserter.ok(!actual, message, "");
    }

    public static void assertNotNull(final String message, final Object actual) {
        sAsserter.isnot(actual, null, message);
    }

    public static void assertNull(final String message, final Object actual) {
        sAsserter.is(actual, null, message);
    }

    public static void assertTrue(final String message, final boolean actual) {
        sAsserter.ok(actual, message, "");
    }

    public static void fail(final String message) {
        // TODO: diag?
        sAsserter.ok(false, message, "");
    }
}
