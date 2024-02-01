package com.github.nekear.certificates_api.web.configs;

import com.github.nekear.certificates_api.web.repos.impls.MysqlCertificatesDAO;
import com.github.nekear.certificates_api.web.repos.prototypes.CertificatesDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CertificatesDAO mysqlCertificatesDAO() {
        return new MysqlCertificatesDAO();
    }
}
