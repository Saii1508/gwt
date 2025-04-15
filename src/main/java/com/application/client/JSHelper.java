package com.application.client;

import com.google.gwt.core.client.JavaScriptObject;

public class JSHelper {

    public static native void setAttribute(JavaScriptObject obj, String key, String value) /*-{
        obj[key] = value;
    }-*/;

    public static native void addElementInArray(JavaScriptObject array, JavaScriptObject element) /*-{
        array.push(element);
    }-*/;
}

