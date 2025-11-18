package org.example.synergy.controller;

import lombok.RequiredArgsConstructor;
import org.example.synergy.dto.ActionRequestDto;
import org.example.synergy.dto.AppraisalRequestDetailDto;
import org.example.synergy.dto.CreateAppraisalRequestDto;
import org.example.synergy.service.AppraisalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appraisal-requests")
@RequiredArgsConstructor
public class AppraisalController {

    private final AppraisalService appraisalService;

    // Lưu nháp
    @PostMapping("/draft")
    public ResponseEntity<AppraisalRequestDetailDto> saveDraft(@RequestBody CreateAppraisalRequestDto dto) {
        AppraisalRequestDetailDto result = appraisalService.saveDraft(dto);
        return ResponseEntity.ok(result);
    }

    // Gửi duyệt (submit)
    @PostMapping("/submit")
    public ResponseEntity<AppraisalRequestDetailDto> submit(@RequestBody CreateAppraisalRequestDto dto) {
        AppraisalRequestDetailDto result = appraisalService.submit(dto);
        return ResponseEntity.ok(result);
    }

    // Duyệt
    @PostMapping("/{id}/approve")
    public ResponseEntity<AppraisalRequestDetailDto> approve(@PathVariable Long id,
                                                             @RequestBody ActionRequestDto dto) {
        AppraisalRequestDetailDto result = appraisalService.approve(id, dto);
        return ResponseEntity.ok(result);
    }

    // Từ chối
    @PostMapping("/{id}/reject")
    public ResponseEntity<AppraisalRequestDetailDto> reject(@PathVariable Long id,
                                                            @RequestBody ActionRequestDto dto) {
        AppraisalRequestDetailDto result = appraisalService.reject(id, dto);
        return ResponseEntity.ok(result);
    }

    // Lấy chi tiết (kèm giá trị dynamic fields + files)
    @GetMapping("/{id}")
    public ResponseEntity<AppraisalRequestDetailDto> getById(@PathVariable Long id) {
        AppraisalRequestDetailDto dto = appraisalService.getById(id);
        return ResponseEntity.ok(dto);
    }

    // Danh sách rút gọn
    @GetMapping
    public ResponseEntity<List<AppraisalRequestDetailDto>> listAll() {
        List<AppraisalRequestDetailDto> list = appraisalService.listAll();
        return ResponseEntity.ok(list);
    }
}
