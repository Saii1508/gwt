package com.application.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;


public class Loader implements EntryPoint {

    public Loader() {
        SimplePanel contentPanel = new SimplePanel();
        contentPanel.setWidget(new HomePanel(contentPanel));
        RootPanel.get().add(contentPanel);
    }
    @Override
    public void onModuleLoad() {
    }
}
