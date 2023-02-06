package com.eqima.eqimaid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("fingerprint")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fingerprint {
    @Id
    private ObjectId id;
    private String path;
}
