package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.home.HomePager.Page;
import org.mozilla.gecko.tests.helpers.*;

// TODO: Elaborate on test desc and use /**.
/* Test correct state for URL bar after loading pages.
 */
public class testNewAboutHomeSwipes extends UITest {
    // TODO: Define this test dynamically by creating dynamic representations of the Page
    // enum for both phone and tablet, then swiping through the pages. This will also
    // benefit having a HomePager with custom pages.
    public void testNewAboutHomeSwipes() {
        GeckoHelper.blockForReady();

        mAboutHome.assertVisible()
                  .assertCurrentPage(Page.TOP_SITES);

        mAboutHome.swipeToPageOnRight();
        mAboutHome.assertCurrentPage(Page.BOOKMARKS);

        mAboutHome.swipeToPageOnRight();
        mAboutHome.assertCurrentPage(Page.READING_LIST);

        if (!DeviceHelper.isTablet()) {
            // Edge case.
            mAboutHome.swipeToPageOnRight();
            mAboutHome.assertCurrentPage(Page.READING_LIST);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.BOOKMARKS);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.TOP_SITES);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.HISTORY);

            // Edge case.
            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.HISTORY);

            mAboutHome.swipeToPageOnRight();
            mAboutHome.assertCurrentPage(Page.TOP_SITES);
        } else {
            mAboutHome.swipeToPageOnRight();
            mAboutHome.assertCurrentPage(Page.HISTORY);

            // Edge case.
            mAboutHome.swipeToPageOnRight();
            mAboutHome.assertCurrentPage(Page.HISTORY);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.READING_LIST);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.BOOKMARKS);

            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.TOP_SITES);

            // Edge case.
            mAboutHome.swipeToPageOnLeft();
            mAboutHome.assertCurrentPage(Page.TOP_SITES);
        }
    }
}
