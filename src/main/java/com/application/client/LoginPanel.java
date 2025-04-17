package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;
import java.util.logging.Logger;

public class LoginPanel extends VerticalPanel {

    private static final Logger logger = Logger.getLogger(LoginPanel.class.getName());
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

        login.addClickHandler(event -> {
            String gmail = email.getText();
            String pass = password.getText();


            if(gmail.isEmpty() || pass.isEmpty()) {
                errorMessage.setText("Please enter both email and password");
                errorMessage.setVisible(true);
            }
            UserDto gettingUser = new UserDto();
            gettingUser.setEmail(gmail);
            gettingUser.setPassword(pass);


            actions.login(gettingUser, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    errorMessage.setText("Login failed due to server error.");
                    errorMessage.setVisible(true);
                }
                @Override
                public void onSuccess(Boolean result) {
                    if(result) {
                        actions.getUsers(new AsyncCallback<List<UserDto>>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                errorMessage.setText("Failed to fetch the data");
                                errorMessage.setVisible(true);
                            }
                            @Override
                            public void onSuccess(List<UserDto> users) {
                                String name = users.stream()
                                        .filter(s -> s.getEmail().equals(gettingUser.getEmail()))
                                        .map(UserDto::getName)
                                        .findFirst()
                                        .orElse("No user Got");

                                logger.info("received users : " + users);
                                contentPanel.setWidget(new ShowDataPanel(contentPanel,users,name));
                            }
                        });
                    } else {
                        errorMessage.setText("Wrong credentials");
                        errorMessage.setVisible(true);
                    }
                }
            });
        });


        back.addClickHandler(clickEvent -> contentPanel.setWidget(new HomePanel(contentPanel)));
        signup.addClickHandler(clickEvent -> contentPanel.setWidget(new SignupPanel(contentPanel)));
    }
}
