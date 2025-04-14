package com.application.client;

import com.google.gwt.user.client.ui.*;

public class HomePanel extends VerticalPanel {

    public HomePanel(SimplePanel contentPanel) {
        setSpacing(5);
        setHorizontalAlignment(ALIGN_CENTER);
        setStyleName("center-container");
        Image logo = new Image("images/welcome.png");
        logo.setStyleName("logo");
        Button login = new Button("Login");
        login.setStyleName("black-button");
        Button signup = new Button("Sign Up");
        signup.setStyleName("black-button");

        add(logo);
        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(5);
        buttonPanel.add(login);
        buttonPanel.add(signup);
        add(buttonPanel);

        login.addClickHandler(clickEvent -> contentPanel.setWidget(new LoginPanel(contentPanel)));
        signup.addClickHandler(clickEvent -> contentPanel.setWidget(new SignupPanel(contentPanel)));

    }
}
