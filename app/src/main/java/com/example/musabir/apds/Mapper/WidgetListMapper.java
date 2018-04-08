package com.example.musabir.apds.Mapper;

/**
 * Created by Musabir on 3/8/2018.
 */

public class WidgetListMapper {

    public WidgetListMapper(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    private String name;
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
