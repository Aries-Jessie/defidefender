package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CredentialRepository extends JpaRepository<Credential, Integer> {

    List<Credential> findCredentialsByWeid(String weid);

    List<Credential> findCredentialsByWeidAndTypeIn(String weid, List<Integer> types);

    List<Credential> findCredentialsByWeidAndType(String weid, int type);

    List<Credential> findCredentialsByType(int type);

}
