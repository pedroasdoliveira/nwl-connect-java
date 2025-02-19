package br.com.nwl.Events.repositories;

import br.com.nwl.Events.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
