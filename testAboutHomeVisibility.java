package org.mozilla.gecko.tests;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.helpers.*;
import org.mozilla.gecko.tests.components.AboutHomeComponent.Page;

// TODO: Elaborate on test desc and use /**.
/* Test correct state for URL bar after loading pages.
 */
public class testAboutHomeVisibility extends UITest {
    public void testAboutHomeVisibility() {
        GeckoHelper.blockForReady();

        // Check initial state on about:home.
        TOOLBAR.assertTitle(StringHelper.ABOUT_HOME_TITLE);
        ABOUTHOME.assertVisible()
                 .assertCurrentPage(Page.TOP_SITES);

        // Go to blank 01.
        NavigationHelper.enterAndLoadUrl(StringHelper.ROBOCOP_BLANK_PAGE_01_URL);
        TOOLBAR.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_01_TITLE);
        ABOUTHOME.assertNotVisible();

        // Go to blank 02.
        NavigationHelper.enterAndLoadUrl(StringHelper.ROBOCOP_BLANK_PAGE_02_URL);
        TOOLBAR.assertTitle(StringHelper.ROBOCOP_BLANK_PAGE_02_TITLE);
        ABOUTHOME.assertNotVisible();

        // Enter editing mode, where the about:home UI should be visible.
        TOOLBAR.enterEditingMode();
        ABOUTHOME.assertVisible()
                 .assertCurrentPage(Page.TOP_SITES);

        // Dismiss editing mode, where the about:home UI should be gone.
        TOOLBAR.dismissEditingMode();
        ABOUTHOME.assertNotVisible();

        // Loading about:home should show about:home again.
        NavigationHelper.enterAndLoadUrl(StringHelper.ABOUT_HOME_URL);
        TOOLBAR.assertTitle(StringHelper.ABOUT_HOME_TITLE);
        ABOUTHOME.assertVisible()
                 .assertCurrentPage(Page.TOP_SITES);

        // TODO: Type in a url and assert the go button is visible.
    }
}
