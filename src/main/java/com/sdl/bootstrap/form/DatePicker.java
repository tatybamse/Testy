package com.sdl.bootstrap.form;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * If you want more information about this datepicker visit this site: http://vitalets.github.io/bootstrap-datepicker/
 */
public class DatePicker extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatePicker.class);

    private WebLocator icon = new WebLocator(this).setClasses("icon-calendar").setInfoMessage("Open Calendar");
    private WebLocator dataPicker = new WebLocator().setClasses("datepicker-dropdown dropdown-menu").setStyle("display: block;");
    private WebLocator dataPickerDays = new WebLocator(dataPicker).setClasses("datepicker-days").setStyle("display: block;");
    private WebLocator dataPickerMonths = new WebLocator(dataPicker).setClasses("datepicker-months").setStyle("display: block;");
    private WebLocator dataPickerYear = new WebLocator(dataPicker).setClasses("datepicker-years").setStyle("display: block;");
    private WebLocator switchDay = new WebLocator(dataPickerDays).setClasses("switch").setInfoMessage("switchMonth");
    private WebLocator switchMonth = new WebLocator(dataPickerMonths).setClasses("switch").setInfoMessage("switchYear");

    public DatePicker() {
        setClassName("DatePicker");
        setClasses("date");
    }

    public DatePicker(WebLocator container) {
        this();
        setContainer(container);
    }

    public DatePicker(WebLocator container, String id) {
        this(container);
        setId(id);
    }

    /**
     * example new DatePicker().select("19/05/2013")
     *
     * @param date accept only this format: 'dd/MM/yyyy'
     * @return true if is selected date, false when DatePicker doesn't exist
     */
    public boolean select(String date) {
        return select(date, "dd/MM/yyyy", Locale.ENGLISH);
    }

    public boolean select(String date, String format, Locale locale) {
        SimpleDateFormat inDateFormat = new SimpleDateFormat(format, locale);
        SimpleDateFormat outDateForm = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            Date fromDate = inDateFormat.parse(date);
            date = outDateForm.format(fromDate);
        } catch (ParseException e) {
            LOGGER.error("ParseException: {}", e);
        }
        String[] dates = date.split("/");
        return setDate(Integer.parseInt(dates[0]) + "", dates[1], dates[2]);
    }

    public boolean setDate(String day, String month, String year) {
        if (icon.click()) {
            boolean ok = true;
            WebLocator monthSelect = new WebLocator(dataPickerMonths).setClasses("month").setText(month);
            String fullDate = switchDay.getHtmlText();
            if (!fullDate.contains(year)) {
                switchDay.click();
                switchMonth.click();
                WebLocator yearSelect = new WebLocator(dataPickerYear).setClasses("year").setText(year, SearchType.EQUALS);
                ok = ok && yearSelect.click() &&
                        monthSelect.click();
            } else if (!fullDate.contains(month)) {
                switchDay.click();
                ok = ok && monthSelect.click();
            }
            WebLocator daySelect = new WebLocator(dataPickerDays).setClasses("day").setText(day, SearchType.EQUALS);
            return ok && daySelect.click();
        }
        return false;
    }

    public String getDate() {
        WebLocator webLocator = new WebLocator(this, "//input");
        return webLocator.getAttribute("value");
    }
}