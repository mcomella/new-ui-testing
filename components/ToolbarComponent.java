/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.gecko.tests.components;

import static org.mozilla.gecko.tests.helpers.AssertionHelper.*;

import org.mozilla.gecko.tests.helpers.*;
import org.mozilla.gecko.tests.UITestContext;

import com.jayway.android.robotium.solo.Condition;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ToolbarComponent extends BaseComponent {
    private static final String BROWSER_TOOLBAR_ID = "browser_toolbar";
    private static final String URL_EDIT_TEXT_ID = "url_edit_text";
    private static final String URL_DISPLAY_CONTAINER_ID = "url_display_container";
    private static final String URL_TITLE_TEXT_ID = "url_bar_title";
    private static final String GO_BUTTON_ID = "go";
    private static final String BACK_BUTTON_ID = "back";
    private static final String FORWARD_BUTTON_ID = "forward";

    public ToolbarComponent(final UITestContext testContext) {
        super(testContext);
    }

    public ToolbarComponent assertIsEditing() {
        assertTrue("The toolbar is in the editing state", isEditing());
        return this;
    }

    public ToolbarComponent assertIsNotEditing() {
        assertFalse("The toolbar is not in the editing state", isEditing());
        return this;
    }

    public ToolbarComponent assertTitle(final String expected) {
        // TODO: How is this affected by the showURL preference?
        assertEquals("The Toolbar title is " + expected, expected, getTitle());
        return this;
    }

    public ToolbarComponent assertUrl(final String expected) {
        assertIsEditing();
        assertEquals("The Toolbar url is " + expected, expected, getUrlEditText().getText());
        return this;
    }

    /**
     * Returns the root View for the browser toolbar.
     */
    private View getToolbarView() {
        return mSolo.getView(BROWSER_TOOLBAR_ID);
    }

    private EditText getUrlEditText() {
        return (EditText) ViewHelper.findViewById(getToolbarView(), URL_EDIT_TEXT_ID);
    }

    private View getUrlDisplayContainer() {
        return ViewHelper.findViewById(getToolbarView(), URL_DISPLAY_CONTAINER_ID);
    }

    private TextView getUrlTitleText() {
        return (TextView) ViewHelper.findViewById(getToolbarView(), URL_TITLE_TEXT_ID);
    }

    /**
     * Returns the View for the go button in the browser toolbar.
     */
    private ImageButton getGoButton() {
        return (ImageButton) ViewHelper.findViewById(getToolbarView(), GO_BUTTON_ID);
    }

    private ImageButton getBackButton() {
        DeviceHelper.assertIsTablet();
        return (ImageButton) ViewHelper.findViewById(getToolbarView(), BACK_BUTTON_ID);
    }

    private ImageButton getForwardButton() {
        DeviceHelper.assertIsTablet();
        return (ImageButton) ViewHelper.findViewById(getToolbarView(), FORWARD_BUTTON_ID);
    }

    private CharSequence getTitle() {
        return getTitleHelper(true);
    }

    /**
     * Returns the title of the page. Note that this makes no assertions to Toolbar state and
     * may return a value that may never be visible to the user. Callers likely want to use
     * {@link assertTitle} instead.
     */
    public CharSequence getPotentiallyInconsistentTitle() {
        return getTitleHelper(false);
    }

    private CharSequence getTitleHelper(final boolean shouldAssertNotEditing) {
        if (shouldAssertNotEditing) {
            assertIsNotEditing();
        }
        return getUrlTitleText().getText();
    }

    private boolean isEditing() {
        return getUrlDisplayContainer().getVisibility() != View.VISIBLE &&
                getUrlEditText().getVisibility() == View.VISIBLE;
    }

    public ToolbarComponent enterEditingMode() {
        assertIsNotEditing();

        mSolo.clickOnView(getUrlTitleText(), true);

        waitForEditing();
        WaitHelper.waitFor("UrlEditText to be input method target", new Condition() {
            @Override
            public boolean isSatisfied() {
                return getUrlEditText().isInputMethodTarget();
            }
        });

        return this;
    }

    public ToolbarComponent commitEditingMode() {
        assertIsEditing();

        WaitHelper.waitForPageLoad(new Runnable() {
            @Override
            public void run() {
                mSolo.clickOnView(getGoButton());
            }
        });
        waitForNotEditing();

        return this;
    }

    public ToolbarComponent dismissEditingMode() {
        assertIsEditing();

        if (getUrlEditText().isInputMethodTarget()) {
            // Drop the soft keyboard.
            // TODO: Solo.hideSoftKeyboard() does not clear focus, causing unexpected
            // behavior, but we may want to use it over goBack().
            mSolo.goBack();
        }

        mSolo.goBack();

        waitForNotEditing();

        return this;
    }

    public ToolbarComponent enterUrl(final String url) {
        assertNotNull("url is not null", url);

        assertIsEditing();

        final EditText urlEditText = getUrlEditText();
        assertTrue("The UrlEditText is the input method target",
                urlEditText.isInputMethodTarget());

        mSolo.clearEditText(urlEditText);
        mSolo.enterText(urlEditText, url);

        return this;
    }

    public ToolbarComponent pressBackButton() {
        final ImageButton backButton = getBackButton();
        return pressButton(backButton, "back");
    }

    public ToolbarComponent pressForwardButton() {
        final ImageButton forwardButton = getForwardButton();
        return pressButton(forwardButton, "forward");
    }

    private ToolbarComponent pressButton(final View view, final String buttonName) {
        assertNotNull("The " + buttonName + " button View is not null", view);
        assertTrue("The " + buttonName + " button is enabled", view.isEnabled());
        assertEquals("The " + buttonName + " button is visible",
                View.VISIBLE, view.getVisibility());

        WaitHelper.waitForPageLoad(new Runnable() {
            @Override
            public void run() {
                mSolo.clickOnView(view);
            }
        });

        return this;
    }

    private void waitForEditing() {
        WaitHelper.waitFor("Toolbar to enter editing mode", new Condition() {
            @Override
            public boolean isSatisfied() {
                return isEditing();
            }
        });
    }

    private void waitForNotEditing() {
        WaitHelper.waitFor("Toolbar to exit editing mode", new Condition() {
            @Override
            public boolean isSatisfied() {
                return !isEditing();
            }
        });
    }
}
