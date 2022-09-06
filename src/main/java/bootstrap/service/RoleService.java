package bootstrap.service;

import bootstrap.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    void add(Role role);
}
