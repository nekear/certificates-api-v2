package com.github.nekear.certificates_api.web.repos.impls;


import com.github.nekear.certificates_api.web.entities.Certificate;
import com.github.nekear.certificates_api.web.repos.prototypes.CertificatesDAO;

import java.util.List;

public class MysqlCertificatesDAO implements CertificatesDAO {
    @Override
    public List<Certificate> getCertificates() {
        return List.of(
                new Certificate("Java", "Java certificate", 101.0),
                new Certificate("Python", "Python certificate", 201.0),
                new Certificate("JavaScript", "JavaScript certificate", 301.0)
        );
    }
}
