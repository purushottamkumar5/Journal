package net.edigest.journal.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@Document
@Data
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private  String userName;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry>journalEnteries=new ArrayList<>();
    private List<String>roles;
}
