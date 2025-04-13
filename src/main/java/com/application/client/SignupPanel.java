package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class SignupPanel extends VerticalPanel {

    private final ActionsAsync actions;

    public SignupPanel(SimplePanel contentPanel) {
        actions = GWT.create(Actions.class);
        setSpacing(5);
        setHorizontalAlignment(ALIGN_CENTER);
        setStyleName("center-container");
        TextBox username = new TextBox();
        TextBox email = new TextBox();
        PasswordTextBox password = new PasswordTextBox();
        TextBox contact = new TextBox();

        Image png = new Image("images/signup.png");
        png.setStyleName("logo");
        add(png);
        add(new Label("Username:"));
        add(username);
        add(new Label("Email:"));
        add(email);
        add(new Label("Password:"));
        add(password);
        add(new Label("Contact:"));
        add(contact);

        Button signUp = new Button("Sign Up");
        signUp.setStyleName("black-button");
        Button login = new Button("Login");
        login.setStyleName("black-button");
        Button back = new Button("Back to Home");
        back.setStyleName("black-button");

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(5);
        buttonPanel.add(signUp);
        buttonPanel.add(login);
        buttonPanel.add(back);
        add(buttonPanel);

        back.addClickHandler(clickEvent -> contentPanel.setWidget(new HomePanel(contentPanel)));
        login.addClickHandler(clickEvent -> contentPanel.setWidget(new LoginPanel(contentPanel)));
        signUp.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                String mail = email.getText();
                String nam = username.getText();
                String pass = password.getText();
                String cont = contact.getText();

                UserDto user = new UserDto();
                user.setName(nam);
                user.setPassword(pass);
                user.setEmail(mail);
                user.setContact(cont);

                actions.register(user,new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        Window.alert("Error: " + throwable.getMessage());
                    }
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            contentPanel.setWidget(new LoginPanel(contentPanel));
                        } else {
                            Window.alert("Login failed");
                        }
                    }
                });
            }
        });

        RootPanel.get().add(contentPanel);

    }
}
