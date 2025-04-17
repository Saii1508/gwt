package com.application.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace = "ds", name = "DataAgGridPanel")
public class DataAgGridPanel extends VerticalPanel {

    public DataAgGridPanel() {
    }

    @JsMethod
    public static native void createDataGrid(String id, JavaScriptObject rowData/*, JavaScriptObject columnDefs*/);

}
