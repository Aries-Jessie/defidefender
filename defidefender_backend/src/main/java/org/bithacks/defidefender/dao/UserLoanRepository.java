package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.UserLoan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoanRepository extends JpaRepository<UserLoan, Integer> {
    List<UserLoan> findUserLoansByWeid(String weid);

    List<UserLoan> findUserLoansByWeidAndCompanyName(String weid, String companyName);
}
