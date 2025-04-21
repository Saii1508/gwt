package com.application.client;

import com.application.shared.UserDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("actions")
public interface Actions extends RemoteService {
    boolean login(UserDto user);
    boolean register(UserDto user);
    List<UserDto> getUsers();
    boolean delete(String id);

}
