package com.eqima.eqimaid.mapper;

import com.eqima.eqimaid.dto.FingerprintDto;
import com.eqima.eqimaid.dto.FingerprintListDto;
import com.eqima.eqimaid.model.Fingerprint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FingerprintMapper {
    FingerprintMapper mapper = Mappers.getMapper(FingerprintMapper.class);

    @Mapping(source = "uid", target = "ownerId")
    @Mapping(source = "fingerprint.path", target = "path")
    FingerprintDto toFingerprintDto(String uid, Fingerprint fingerprint);

    @Mapping(source = "fingerprintDto.path", target = "path")
    Fingerprint toFingerprint(FingerprintDto fingerprintDto);

    @Mapping(source = "fingerprint.path", target = "path")
    FingerprintListDto list(Fingerprint fingerprint);
}
