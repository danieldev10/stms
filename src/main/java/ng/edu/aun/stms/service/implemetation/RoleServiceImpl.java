package ng.edu.aun.stms.service.implemetation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ng.edu.aun.stms.model.Role;
import ng.edu.aun.stms.repository.RoleRepository;
import ng.edu.aun.stms.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

}
