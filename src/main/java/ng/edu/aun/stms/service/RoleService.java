package ng.edu.aun.stms.service;

import ng.edu.aun.stms.model.Role;

public interface RoleService {
    public Role findByRoleName(String roleName);

    public void save(Role role);
}
