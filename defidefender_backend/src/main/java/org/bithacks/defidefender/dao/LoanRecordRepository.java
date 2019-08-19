package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRecordRepository extends JpaRepository<LoanRecord, Integer> {
    List<LoanRecord> findLoanRecordsByStatus(int status);

    List<LoanRecord> findLoanRecordsByCompanyNameAndStatus(String companyName, int status);

    List<LoanRecord> findLoanRecordsByWeidAndStatus(String weid, int status);

    List<LoanRecord> findLoanRecordsByWeidAndStatusGreaterThanEqual(String weid, int status);

    List<LoanRecord> findLoanRecordsByCompanyNameAndStatusGreaterThanEqual(String companyName, int status);
}
