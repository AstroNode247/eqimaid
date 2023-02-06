package com.eqima.eqimaid.dto;

import com.eqima.eqimaid.model.Fingerprint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class ExistingUserDto {
    private ObjectId id;
    private Integer uid;
    private String firstName;
    private String lastName;
    private String entity;
    private List<Fingerprint> fingerprints;
}
