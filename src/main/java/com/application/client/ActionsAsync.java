package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface ActionsAsync  {

    void getUsers(AsyncCallback<List<UserDto>> async);

    void register(UserDto user, AsyncCallback<Boolean> async);

    void login(UserDto user, AsyncCallback<Boolean> async);

    void delete(String id, AsyncCallback<Boolean> async);
}
