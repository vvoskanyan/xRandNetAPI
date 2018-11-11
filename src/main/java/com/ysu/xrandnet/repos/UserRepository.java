package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}