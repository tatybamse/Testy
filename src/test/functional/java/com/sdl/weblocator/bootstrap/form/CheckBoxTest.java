package com.sdl.weblocator.bootstrap.form;

import com.sdl.bootstrap.form.CheckBox;
import com.sdl.bootstrap.form.Form;
import com.sdl.selenium.web.SearchType;
import com.sdl.weblocator.InputData;
import com.sdl.weblocator.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckBoxTest extends TestBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckBoxTest.class);

    Form form = new Form(null, "Form Title");
    CheckBox checkBox = new CheckBox(form);
    CheckBox withEnterWebLocator = new CheckBox(form).setLabel("Label with Enter.", SearchType.CHILD_NODE).setLabelPosition("//");

    @BeforeClass
    public void startTests() {
        driver.get(InputData.BOOTSTRAP_URL);
    }

    @Test
    public void check() {
        assertTrue(checkBox.click());
        assertTrue(checkBox.isSelected());
    }

    @Test
    public void clickWith() {
        assertTrue(withEnterWebLocator.click());
    }
}
