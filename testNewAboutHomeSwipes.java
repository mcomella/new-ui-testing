package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.AssertionHelper.*;

import org.mozilla.gecko.tests.AboutHomeComponent.Page;

// TODO: Elaborate on test desc and use /**.
/* Test correct state for URL bar after loading pages.
 */
public class testNewAboutHomeSwipes extends UITest {
    // TODO: Make this abstract using the Page enum.
    public void testNewAboutHomeSwipes() {
        GeckoHelper.blockForReady();

        ABOUTHOME.assertVisible()
                 .assertCurrentPage(Page.TOP_SITES);

        ABOUTHOME.swipeToNext();
        ABOUTHOME.assertCurrentPage(Page.BOOKMARKS);

        ABOUTHOME.swipeToNext();
        ABOUTHOME.assertCurrentPage(Page.READING_LIST);

        // Edge case.
        ABOUTHOME.swipeToNext();
        ABOUTHOME.assertCurrentPage(Page.READING_LIST);

        ABOUTHOME.swipeToPrevious();
        ABOUTHOME.assertCurrentPage(Page.BOOKMARKS);

        ABOUTHOME.swipeToPrevious();
        ABOUTHOME.assertCurrentPage(Page.TOP_SITES);

        ABOUTHOME.swipeToPrevious();
        ABOUTHOME.assertCurrentPage(Page.HISTORY);

        // Edge case.
        ABOUTHOME.swipeToPrevious();
        ABOUTHOME.assertCurrentPage(Page.HISTORY);

        ABOUTHOME.swipeToNext();
        ABOUTHOME.assertCurrentPage(Page.TOP_SITES);
    }
}
