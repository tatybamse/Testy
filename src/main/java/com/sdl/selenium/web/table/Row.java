package com.sdl.selenium.web.table;

import com.sdl.selenium.web.SearchType;
import com.sdl.selenium.web.WebLocator;
import com.sdl.selenium.web.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public abstract class Row extends WebLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Row.class);

    public Row() {
        setClassName("Row");
    }

    protected void setRowCells(Cell... cells) {
        String path = "";
        for (Cell cell : cells) {
            String text = cell.getText();
            Set<SearchType> searchTextType = cell.getSearchTextType();
            WebLocator cellEl = new WebLocator().setText(cell.getText(), searchTextType.toArray(new SearchType[searchTextType.size()]));
            String tagCell = cell.getTag();
            if (cell.getPosition() != -1 && !"".equals(text) && text != null) {
                path += " and " + getSearchPath(cell.getPosition(), cellEl, tagCell);
            } else if (cell.getPosition() == -1 && !"".equals(text) && text != null) {
                path += " and " + getSearchPath(cellEl, tagCell);
            } else {
                LOGGER.warn("cell.getPosition()=" + cell.getPosition());
                LOGGER.warn("text=" + text);
                LOGGER.warn("Please use : new TableCell(3, \"1234\", \"eq\")");
            }
        }
        setElPathSuffix("row-cells", Utils.fixPathSelector(path));
    }

    protected String getSearchPath(int columnIndex, WebLocator cellEl, String tag) {
        if (tag == null || "".equals(tag)) {
            tag = "td";
        }
        return "count(" + tag + "[" + columnIndex + "]" + cellEl.getPath().substring(3) + ") > 0";
    }

    protected String getSearchPath(WebLocator cellEl, String tag) {
        if (tag == null || "".equals(tag)) {
            tag = "td";
        }
        return "count(" + tag + cellEl.getPath().substring(1) + ") > 0";
    }
}
