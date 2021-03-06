package com.extjs.selenium.button;

import com.sdl.bootstrap.button.RunExe;
import com.sdl.bootstrap.button.Upload;
import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebDriverConfig;
import com.sdl.selenium.web.WebLocator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class UploadButton extends Button implements Upload {

    public UploadButton() {
        setClassName("UploadButton");
    }

    public UploadButton(WebLocator container) {
        this();
        setContainer(container);
    }

    public UploadButton(WebLocator container, String text) {
        this(container);
        setText(text, SearchType.EQUALS);
    }

    @Override
    public boolean upload(String ...filePath){
        return upload(this, filePath);
    }

    private boolean upload(WebLocator el, String ...filePath) {
        browse(el);
        return RunExe.getInstance().upload(filePath);
    }

    private void browse(WebLocator el) {
        WebDriver driver = WebDriverConfig.getDriver();
        el.focus();
        Actions builder = new Actions(driver);
        builder.moveToElement(el.currentElement).perform();
        builder.click().perform();
    }
}
