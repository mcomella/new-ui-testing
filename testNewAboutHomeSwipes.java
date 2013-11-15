package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.home.HomePager.Page;
import org.mozilla.gecko.tests.helpers.*;

// TODO: Elaborate on test desc and use /**.
/* Test correct state for URL bar after loading pages.
 */
public class testNewAboutHomeSwipes extends UITest {
    public void testNewAboutHomeSwipes() {
        GeckoHelper.blockForReady();

        mAboutHome.assertVisible()
                  .assertCurrentPage(Page.TOP_SITES);

        mAboutHome.swipeToNext();
        mAboutHome.assertCurrentPage(Page.BOOKMARKS);

        mAboutHome.swipeToNext();
        mAboutHome.assertCurrentPage(Page.READING_LIST);

        // Edge case.
        mAboutHome.swipeToNext();
        mAboutHome.assertCurrentPage(Page.READING_LIST);

        mAboutHome.swipeToPrevious();
        mAboutHome.assertCurrentPage(Page.BOOKMARKS);

        mAboutHome.swipeToPrevious();
        mAboutHome.assertCurrentPage(Page.TOP_SITES);

        mAboutHome.swipeToPrevious();
        mAboutHome.assertCurrentPage(Page.HISTORY);

        // Edge case.
        mAboutHome.swipeToPrevious();
        mAboutHome.assertCurrentPage(Page.HISTORY);

        mAboutHome.swipeToNext();
        mAboutHome.assertCurrentPage(Page.TOP_SITES);
    }
}
