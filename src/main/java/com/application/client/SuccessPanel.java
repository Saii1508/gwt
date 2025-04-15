package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.List;
import java.util.logging.Logger;

public class SuccessPanel extends VerticalPanel {

    private final static Logger logger = Logger.getLogger(SuccessPanel.class.getName());

    public SuccessPanel(SimplePanel contentPanel, List<UserDto> users) {
        logger.info("Creating Success Panel");

        AgGridPanel agGridPanel = new AgGridPanel();
        agGridPanel.setHeight("400px");
        agGridPanel.setWidth("800px");
        add(agGridPanel);
        agGridPanel.getElement().setId("myGrid");
        final String id = agGridPanel.getElement().getId();

        JavaScriptObject rowData = JavaScriptObject.createArray();

        for (UserDto user : users) {
            JavaScriptObject row = JavaScriptObject.createObject();
            JSHelper.setAttribute(row, "name", user.getName());
            JSHelper.setAttribute(row, "email", user.getEmail());
            JSHelper.setAttribute(row, "password", user.getPassword());
            JSHelper.setAttribute(row, "contact", user.getContact());
            JSHelper.addElementInArray(rowData, row);
            logger.info("Obj ref : " + row.hashCode());
        }


        JavaScriptObject columnDef = JavaScriptObject.createArray();
        JavaScriptObject name = JavaScriptObject.createObject();
        JSHelper.setAttribute(name, "field", "name");

        JavaScriptObject email = JavaScriptObject.createObject();
        JSHelper.setAttribute(email, "field", "email");

        JavaScriptObject password = JavaScriptObject.createObject();
        JSHelper.setAttribute(password, "field", "password");

        JavaScriptObject contact = JavaScriptObject.createObject();
        JSHelper.setAttribute(contact, "field", "contact");

        JSHelper.addElementInArray(columnDef, name);
        JSHelper.addElementInArray(columnDef, email);
        JSHelper.addElementInArray(columnDef, password);
        JSHelper.addElementInArray(columnDef, contact);

        agGridPanel.addAttachHandler((event -> {
            agGridPanel.createAgGrid(id, rowData, columnDef);
        }));
    }
}
