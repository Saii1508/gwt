package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class SuccessPanel extends VerticalPanel {
    public SuccessPanel(SimplePanel contentPanel,List<UserDto> users) {
        setSpacing(10);
        setHorizontalAlignment(ALIGN_CENTER);
        setStyleName("center-container");

        Label title = new Label("All Registered Users");
        title.setStyleName("table-title");
        add(title);

        FlexTable table = new FlexTable();
        table.setStyleName("user-table");

        // Header
        table.setText(0, 0, "Name");
        table.setText(0, 1, "Email");

        table.getRowFormatter().addStyleName(0, "table-header");

        // Data Rows
        for (int i = 0; i < users.size(); i++) {
            UserDto user = users.get(i);
            table.setText(i + 1, 0, user.getName());
            table.setText(i + 1, 1, user.getEmail());
        }

        add(table);

        // Back Button
        Button back = new Button("Back to Home");
        back.setStyleName("black-button");
        back.addClickHandler(event -> contentPanel.setWidget(new HomePanel(contentPanel)));

        add(back);
    }
}
