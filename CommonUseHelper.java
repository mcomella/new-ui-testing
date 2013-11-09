/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests;

/**
 * A Helper for common use cases combining multiple components or not belonging to a particular
 * component.
 *
 * To prevent code crufting, this should not be a miscellaneous "dump everything!" class - new
 * Components should be freely created, even for lone methods.
 */
public final class CommonUseHelper extends BaseHelper {
    private CommonUseHelper() { /* To disallow instantiation. */ }
}
