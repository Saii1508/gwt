package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class LoginPanel extends VerticalPanel {

    private final ActionsAsync actions;

    public LoginPanel(SimplePanel contentPanel) {
        actions = GWT.create(Actions.class);

        setSpacing(5);
        setHorizontalAlignment(ALIGN_CENTER);
        setStyleName("center-container");
        TextBox email = new TextBox();
        PasswordTextBox password = new PasswordTextBox();

        final Label errorMessage = new Label();

        Image png = new Image("images/login.jpeg");
        png.setStyleName("logo");
        add(png);
        add(new Label("Email:"));
        add(email);
        add(new Label("Password:"));
        add(password);
        errorMessage.setVisible(false);
        errorMessage.setStyleName("error-message");
        add(errorMessage);

        Button login = new Button("Login");
        login.setStyleName("black-button");
        Button signup = new Button("Sign up");
        signup.setStyleName("black-button");
        Button back = new Button("Back to Home");
        back.setStyleName("black-button");

        HorizontalPanel buttonPanel = new HorizontalPanel();
        buttonPanel.setSpacing(5);
        buttonPanel.add(login);
        buttonPanel.add(signup);
        buttonPanel.add(back);
        add(buttonPanel);

        login.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {
                String mail = email.getText();
                String pass = password.getText();

                UserDto user = new UserDto();
                user.setEmail(mail);
                user.setPassword(pass);

                actions.login(user,new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable throwable) {
                        errorMessage.setText("Login failed due to server error.");
                        errorMessage.setVisible(true);
                    }

                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        if (aBoolean) {
                            actions.getUsers(new AsyncCallback<List<UserDto>>() {
                                @Override
                                public void onFailure(Throwable throwable) {
                                    Window.alert("Failed to fetch user list.");
                                }
                                @Override
                                public void onSuccess(List<UserDto> userDtos) {
                                    contentPanel.setWidget(new SuccessPanel(contentPanel,userDtos));
                                }
                            });
                    } else {
                            errorMessage.setText("Invalid email or password.");
                            errorMessage.setVisible(true);
                        }
                    }
                });
            }
        });

        back.addClickHandler(clickEvent -> contentPanel.setWidget(new HomePanel(contentPanel)));
        signup.addClickHandler(clickEvent -> contentPanel.setWidget(new SignupPanel(contentPanel)));
        RootPanel.get().add(contentPanel);
    }
}
