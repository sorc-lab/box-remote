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
    // TODO: Finalize these variable names with client's desired config file.

    // TODO: Do all validation at Bean level in this DTO.
    private String syncSrc;
    private String syncTarget; // TODO: Do we add "/" on end if it doesn't exist?
    private List<String> archives;

    // TODO: Run this DTO through some scenarios where src and target may not be best case
}
