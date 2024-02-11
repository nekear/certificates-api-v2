package com.github.nekear.certificates_api.web.dtos.certificates;

import com.github.nekear.certificates_api.web.dtos.general.FilterRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificatesFilterRequest extends FilterRequest{
    private CertificatesFilter filters;
}
