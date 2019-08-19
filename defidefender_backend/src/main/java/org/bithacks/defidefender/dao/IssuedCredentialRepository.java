package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.IssuedCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedCredentialRepository extends JpaRepository<IssuedCredential, Integer> {
}
