/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests;

import org.mozilla.gecko.Element;
import static org.mozilla.gecko.tests.AssertionHelper.*;

import android.view.View;

final class ViewHelper extends BaseHelper {
    private ViewHelper() { /* To disallow instantiation. */ }

    /**
     * Finds the View with the given ID at the given root. To find a View among all views, see
     * Robotium's {@see Solo.getView}. Note that only the first View found with the given id
     * is returned.
     *
     * This method can be used to find view which is unique from the given root View, but not
     * necessarily unique in the View hierarchy.
     */
    public static View findViewById(final View root, final String id) {
        assertNotNull("Asserting the root view is not null", root);
        assertNotNull("Asserting the id is not null", id);

        final Element element = sDriver.findElement(sActivity, id);
        return root.findViewById(element.getId());
    }
}
