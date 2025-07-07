package ng.edu.aun.stms.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.stms.model.User;

public interface UserService {
    public void save(User user);

    public void update(User user);

    public User findByUsername(String un);

    public User getCurrentUser();

    List<User> get();

    void deleteById(Long id);

    Optional<User> findById(Long id);

    public long countUsers();
}
