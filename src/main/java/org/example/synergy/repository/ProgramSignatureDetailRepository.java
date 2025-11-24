package org.example.synergy.repository;

import org.example.synergy.dto.request.voffice.SearchHistoryRequest;
import org.example.synergy.dto.request.voffice.SearchSignatureDetailRequest;
import org.example.synergy.dto.response.SignatureDetailResponse;
import org.example.synergy.dto.response.VofficeHistoryResponse;
import org.example.synergy.entity.ProgramSignatureDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProgramSignatureDetailRepository extends JpaRepository<ProgramSignatureDetail, Long> {
    Optional<ProgramSignatureDetail> findByProgramPlanVersionId(Long programPlanVersionId);
    Optional<ProgramSignatureDetail> findByTransCode(String signTransCode);

    List<ProgramSignatureDetail> findAllByProgramPlanVersionIdOrderByUpdatedDateDesc(Long programPlanVersionId);

//    @Transactional(readOnly = true)

//    @Query(value = "SELECT NEW com.viettel.vss.dto.response.VofficeHistoryResponse( " +
//            "p.id, " +
//            "p.programPlanVersionId, " +
//            "pv.programPlanName, " +
//            "p.documentCode, " +
//            "p.documentName, " +
//            "p.status, " +
//            "osv.name, " +
//            "p.createdBy, " +
//            "s.fullName, " +
//            "p.signDate, " +
//            "p.areaName, " +
//            "p.typeName, " +
//            "p.stypeName, " +
//            "p.priorityName, " +
//            "p.documentContent, " +
//            "p.receivingPlace, " +
//            "p.autoPromulgateText, " +
//            "p.transCode, " +
//            "p.documentId " +
//            ") " +
//            "FROM ProgramSignatureDetail p " +
//            "LEFT JOIN ProgramPlanVersion pv ON pv.id = p.programPlanVersionId " +
//            "LEFT JOIN Staff s ON p.createdBy = s.userName " +
//            "LEFT JOIN OptionSetValueEntity osv ON osv.value = p.status " +
//            "LEFT JOIN OptionSetEntity os ON os.id = osv.optionSetId AND os.code = 'VOFFICE_STATUS' " +
//            "WHERE (:#{#dto.documentCode} IS NULL OR p.documentCode LIKE CONCAT('%', :#{#dto.documentCode}, '%')) " +
//            "AND (COALESCE(:#{#dto.status}, NULL) IS NULL OR p.status IN :#{#dto.status} ) " +
//            "AND (:#{#dto.id} IS NULL OR p.id = :#{#dto.id} ) " +
//            "AND ((:programId IS NOT NULL AND pv.programId = :programId ) OR :programId IS NULL) " +
//            "AND (:#{#dto.documentName} IS NULL OR p.documentName LIKE CONCAT('%', :#{#dto.documentName}, '%')) " +
//            "ORDER BY p.createdDate DESC")
//    Page<VofficeHistoryResponse> searchVofficeHistory(
//            @Param("programId") Long programId,
//            @Param("dto") SearchHistoryRequest dto,
//            Pageable pageable);

//    @Query(value = "SELECT NEW com.viettel.vss.dto.response.SignatureDetailResponse( " +
//            "p.id, " +
//            "p.programPlanVersionId, " +
//            "pv.programPlanName, " +
//            "p.documentCode, " +
//            "p.documentName, " +
//            "p.status, " +
//            "osv.name, " +
//            "p.createdBy, " +
//            "s.fullName, " +
//            "p.signDate, " +
//            "p.areaName, " +
//            "p.typeName, " +
//            "p.stypeName, " +
//            "p.priorityName, " +
//            "p.documentContent, " +
//            "p.receivingPlace, " +
//            "p.autoPromulgateText, " +
//            "pr.name, " +
//            "pr.programTypeId " +
//            ") " +
//            "FROM ProgramSignatureDetail p " +
//            "LEFT JOIN ProgramPlanVersion pv ON pv.id = p.programPlanVersionId " +
//            "LEFT JOIN ProgramEntity pr ON pr.id = pv.programId " +
//            "LEFT JOIN Staff s ON p.createdBy = s.userName " +
//            "LEFT JOIN OptionSetValueEntity osv ON osv.value = p.status " +
//            "LEFT JOIN OptionSetEntity os ON os.id = osv.optionSetId AND os.code = 'VOFFICE_STATUS' " +
//            "WHERE (:#{#dto.documentCode} IS NULL OR p.documentCode LIKE CONCAT('%', :#{#dto.documentCode}, '%')) " +
//            "AND (:#{#dto.status} IS NULL OR p.status IN :#{#dto.status} ) " +
//            "AND (:#{#dto.id} IS NULL OR p.id = :#{#dto.id} ) " +
//            "AND (:#{#dto.documentName} IS NULL OR p.documentName LIKE CONCAT('%', :#{#dto.documentName}, '%')) " +
//            "AND (:#{#dto.programPlanName} IS NULL OR pv.programPlanName LIKE CONCAT('%', :#{#dto.programPlanName}, '%')) " +
//            "AND (:#{#dto.programName} IS NULL OR pr.name LIKE CONCAT('%', :#{#dto.programName}, '%')) " +
//            "AND (:#{#dto.programTypeId} IS NULL OR pr.programTypeId IN :#{#dto.programTypeId}) " +
//            "ORDER BY p.createdDate DESC")
//    Page<SignatureDetailResponse> searchVofficeHistory(
//            @Param("dto") SearchSignatureDetailRequest dto,
//            Pageable pageable);
}
