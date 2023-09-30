package com.cbo.core.persistence.repository;

import com.cbo.core.persistence.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
//    @Query(" SELECT CDS FROM CommitteeDocstore CDS  "
//            + " JOIN FETCH CDS.committee WHERE CDS.committee.id = :id "
//            + " AND CDS.status = :status")

    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.organizationalUnit WHERE AT.organizationalUnit.id = :organizationalUnitId" +
            " AND AT.status = :status")
    Authority findAuthorityByOrganizationalUnitAndState(@Param("organizationalUnitId") Long organizationalUnitId, @Param("status") String status);

    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.subProcess WHERE AT.subProcess.id = :subProcessId" +
            " AND AT.status = :status")
    Authority findAuthorityBySubProcessAndState(@Param("subProcessId") Long subProcessId, @Param("status") String status);

    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.process WHERE AT.process.id = :processId" +
            " AND AT.status = :status")
    Authority findAuthorityByProcessAndState(@Param("processId") Long processId, @Param("status") String status);


    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.branch WHERE AT.branch.id = :branchId" +
            " AND AT.status = :status")
    Authority findAuthorityByBranchAndState(@Param("branchId") Long branchId, @Param("status") String status);


    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.district WHERE AT.district.id = :districtId" +
            " AND AT.status = :status")
    Authority findAuthorityByDistrictAndState(@Param("districtId") Long districtId, @Param("status") String status);

    @Query(" SELECT AT FROM Authority AT JOIN FETCH AT.employee WHERE AT.employee.id = :employeeId" +
            " AND AT.status = :status")
    Authority findAuthorityByEmployeeAndState(@Param("employeeId") Long employeeId, @Param("status") String status);

    @Query(" SELECT AT FROM Authority AT WHERE" +
            " AT.status = :status")
    List<Authority> findAllActiveAuthorities(@Param("status") String status);

    @Query(" SELECT AT FROM Authority AT WHERE" +
            " AT.status = :active OR AT.status = :inactive ")
    List<Authority> findAllAuthorities(@Param("active") String active, @Param("inactive") String inactive);

}
