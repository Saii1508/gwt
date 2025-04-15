package com.application.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace = "js", name = "AgGridPanel")
public class AgGridPanel extends VerticalPanel {

    public AgGridPanel() {
    }

    @JsMethod
    public static native void createAgGrid(String id, JavaScriptObject rowData, JavaScriptObject columnDefs);

}
