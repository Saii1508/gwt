package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.List;
import java.util.logging.Logger;

public class ShowDataPanel extends VerticalPanel {

    private static final Logger logger = Logger.getLogger(ShowDataPanel.class.getName());

    public ShowDataPanel(List<UserDto> users, String uName) {
        this.getElement().setId("data-grid");
        Label userName = new Label("Welcome " + uName + " hope doing great!");
        userName.setStyleName("gwt-Label");
        add(userName);
        logger.info("Show Data panel is created");

        DataAgGridPanel dataAgGridPanel = new DataAgGridPanel();
        dataAgGridPanel.setHeight("400px");
        dataAgGridPanel.setWidth("800px");
        add(dataAgGridPanel);
        dataAgGridPanel.getElement().setId("girdMy");
        String id = dataAgGridPanel.getElement().getId();
        JavaScriptObject rowData = JavaScriptObject.createArray();
        for (UserDto eachUser : users) {
            JavaScriptObject row = JavaScriptObject.createObject();
            JSHelper.setAttribute(row, "name", eachUser.getName());
            JSHelper.setAttribute(row, "email", eachUser.getEmail());
            JSHelper.setAttribute(row, "password", eachUser.getPassword());
            JSHelper.setAttribute(row, "contact", eachUser.getContact());
            JSHelper.setAttribute(row, "delete", "delete");
            JSHelper.addElementInArray(rowData, row);
            logger.info("Obj ref: " + row.hashCode());
        }
        dataAgGridPanel.addAttachHandler(event -> {
            DataAgGridPanel.createDataGrid(id, rowData);
        });

    }

}



        /*JavaScriptObject columnDef = JavaScriptObject.createArray();

        JavaScriptObject name = JavaScriptObject.createObject();
        JSHelper.setAttribute(name,"field","name");*/

        /*JavaScriptObject email = JavaScriptObject.createObject();
        JSHelper.setAttribute(email,"field","email");

        JavaScriptObject password = JavaScriptObject.createObject();
        JSHelper.setAttribute(password,"field","password");

        JavaScriptObject contact = JavaScriptObject.createObject();
        JSHelper.setAttribute(contact,"field","contact");

        JavaScriptObject delete = JavaScriptObject.createObject();
        JSHelper.setAttribute(delete,"field","delete");

        JSHelper.addElementInArray(columnDef,name);
        JSHelper.addElementInArray(columnDef,email);
        JSHelper.addElementInArray(columnDef,password);
        JSHelper.addElementInArray(columnDef,contact);
        JSHelper.addElementInArray(columnDef,delete);*/