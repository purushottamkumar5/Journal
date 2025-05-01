package net.edigest.journal.repository;

import net.edigest.journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepoitory extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByuserName(String userName);
}
