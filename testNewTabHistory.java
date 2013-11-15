package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.helpers.*;

// TODO: Class name confusing with history list.
// TODO: Elaborate on test desc and use /**.
/* Test correct state for URL bar after loading pages.
 */
public class testNewTabHistory extends UITest {
    public void testNewTabHistory() {
        GeckoHelper.blockForReady();

        NavigationHelper.enterAndLoadUrl(StringHelper.ROBOCOP_BLANK_PAGE_01_URL);
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_01_TITLE);

        NavigationHelper.enterAndLoadUrl(StringHelper.ROBOCOP_BLANK_PAGE_02_URL);
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_02_TITLE);

        NavigationHelper.enterAndLoadUrl(StringHelper.ROBOCOP_BLANK_PAGE_03_URL);
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_03_TITLE);

        NavigationHelper.goBack();
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_02_TITLE);

        NavigationHelper.goBack();
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_01_TITLE);

        // TODO: Implement this functionality and uncomment.
        /*
        NavigationHelper.goForward();
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_02_TITLE);

        NavigationHelper.reload();
        mToolbar.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_02_TITLE);
        */
    }
}
