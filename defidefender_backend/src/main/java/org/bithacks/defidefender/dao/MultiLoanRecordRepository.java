package org.bithacks.defidefender.dao;

import org.bithacks.defidefender.model.Po.MultiLoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultiLoanRecordRepository extends JpaRepository<MultiLoanRecord, Integer> {
    List<MultiLoanRecord> findMultiLoanRecordsByLoanRecordId(int loanRecordId);

    List<MultiLoanRecord> findMultiLoanRecordsByCompanyName(String companyName);
}
