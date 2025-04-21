package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;
import java.util.logging.Logger;

public class DeleteRowHandler {

    private final static Logger logger = Logger.getLogger(DeleteRowHandler.class.getName());

    public static native void exposeDeleteFunction() /*-{
        $wnd.deleteRowFromGWT = $entry(function (email, onSuccessCallback) {
            @com.application.client.DeleteRowHandler::deleteRow(Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(email, onSuccessCallback);
        });
    }-*/;

    public static void deleteRow(String email, JavaScriptObject onSuccessCallback) {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.setText("Confirm Deletion");
        dialogBox.setAnimationEnabled(true);

        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);
        panel.add(new Label("Do you really want to delete this user?"));

        HorizontalPanel buttons = new HorizontalPanel();
        buttons.setSpacing(10);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        buttons.add(yesButton);
        buttons.add(noButton);
        panel.add(buttons);

        dialogBox.setWidget(panel);
        dialogBox.center();
        dialogBox.show();

        yesButton.addClickHandler(event -> {
            dialogBox.hide();
            final ActionsAsync actions = GWT.create(Actions.class);
            actions.delete(email, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("No user found with this ID.");
                }

                @Override
                public void onSuccess(Boolean result) {
                    if (result) {
                        logger.info("successfully deleted");
                        ActionsAsync actions = GWT.create(Actions.class);
                        actions.getUsers(new AsyncCallback<List<UserDto>>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                Window.alert("Sorry");
                            }

                            @Override
                            public void onSuccess(List<UserDto> users) {
                                //Element element = Document.get().getElementById("data-grid");
                                RootPanel.get().getElement().removeAllChildren();
                                RootPanel.get().add(new ShowDataPanel(users, "same dude"));
                            }
                        });

                    } else {
                        Window.alert("Unable to delete user");
                    }
                }
            });
        });
        noButton.addClickHandler(event -> dialogBox.hide());
    }
}