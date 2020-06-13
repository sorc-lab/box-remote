package com.sorclab.boxremote.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class ArchiveDto
{
    // TODO: Do all validation at Bean level in this DTO.
    private String syncSrc;
    private String syncTarget;
    private List<String> archives;
}
