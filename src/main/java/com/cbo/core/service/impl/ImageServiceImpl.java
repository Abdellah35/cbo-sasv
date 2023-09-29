package com.cbo.core.service.impl;

import com.cbo.core.dto.ResultWrapper;
import com.cbo.core.dto.SignatureDTO;
import com.cbo.core.dto.StampDTO;
import com.cbo.core.mappers.SignatureMapper;
import com.cbo.core.mappers.StampMapper;
import com.cbo.core.persistence.model.Authority;
import com.cbo.core.persistence.model.Signature;
import com.cbo.core.persistence.model.Stamp;
import com.cbo.core.persistence.repository.SignatureRepository;
import com.cbo.core.persistence.repository.StampRepository;
import com.cbo.core.response.ImageRes;
import com.cbo.core.service.*;
import com.cbo.core.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service("imageService")
@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private SignatureRepository signatureRepository;

    @Autowired
    private StampRepository stampRepository;

    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ProcessService processService;

    @Autowired
    private SubProcessService subProcessService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ResultWrapper<SignatureDTO> addEmployeeSignature(SignatureDTO signatureDTO) throws IOException {

        ResultWrapper<SignatureDTO> resultWrapper = new ResultWrapper<>();

        Signature signature = new Signature();
        MultipartFile sigImage = signatureDTO.getSignature();
        if (sigImage != null) {
            String uploadDir = "auth-images/signatures/" + signatureDTO.getEmployeeId();
            FileUploadUtil.saveFile(uploadDir, sigImage.getOriginalFilename(), sigImage);
            signature.setSignatureLink(uploadDir + "/" + sigImage.getOriginalFilename());
            signature.setEmployee(employeeService.findEmployeeById(signatureDTO.getEmployeeId()));
            signature.setActive(true);
            Signature existingSignature = signatureRepository.findByEmployeeAndIsActive(signatureDTO.getEmployeeId(), true);
            if (existingSignature != null) {
                existingSignature.setActive(false);
                signatureRepository.save(existingSignature);
            }
            signatureRepository.save(signature);
            resultWrapper.setStatus(true);
            resultWrapper.setMessage("signature image added successfully");
            resultWrapper.setResult(SignatureMapper.INSTANCE.toDTO(signature));
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("image is null");
            return resultWrapper;
        }
    }

    @Override
    public ResultWrapper<StampDTO> addStamp(StampDTO stampDTO) throws IOException {

        ResultWrapper<StampDTO> resultWrapper = new ResultWrapper<>();

        Stamp stamp = new Stamp();
        MultipartFile stampImg = stampDTO.getStamp();
        if (stampImg != null) {
            if (stampDTO.getOrganizationUnitId() != null) {
                String uploadDir = "auth-images/stamp/orgUnit" + stampDTO.getOrganizationUnitId();
                FileUploadUtil.saveFile(uploadDir, stampImg.getOriginalFilename(), stampImg);
                stamp.setStampLink(uploadDir + "/" + stampImg.getOriginalFilename());

                stamp.setOrganizationalUnit(organizationService.findOrganizationUnitById(stampDTO.getOrganizationUnitId()));
                Stamp existingOrgStamp = stampRepository.findByOrganizationUnitIdIdAndIsActive(stampDTO.getOrganizationUnitId(), true);
                if (existingOrgStamp != null) {
                    existingOrgStamp.setActive(false);
                    stampRepository.save(existingOrgStamp);
                }
            } else if (stampDTO.getSubProcessId() != null) {
                String uploadDir = "auth-images/stamp/subProcess" + stampDTO.getSubProcessId();
                FileUploadUtil.saveFile(uploadDir, stampImg.getOriginalFilename(), stampImg);
                stamp.setStampLink(uploadDir + "/" + stampImg.getOriginalFilename());
                stamp.setSubProcess(subProcessService.findSubProcess(stampDTO.getSubProcessId()));
                Stamp existingSubStamp = stampRepository.findBySubProcessIdAndIsActive(stampDTO.getSubProcessId(), true);
                if (existingSubStamp != null) {
                    existingSubStamp.setActive(false);
                    stampRepository.save(existingSubStamp);
                }
            } else if (stampDTO.getProcessId() != null) {
                String uploadDir = "auth-images/stamp/process" + stampDTO.getProcessId();
                FileUploadUtil.saveFile(uploadDir, stampImg.getOriginalFilename(), stampImg);
                stamp.setStampLink(uploadDir + "/" + stampImg.getOriginalFilename());
                stamp.setProcess(processService.findProcess(stampDTO.getProcessId()));
                Stamp existingProcStamp = stampRepository.findByProcessIdAndIsActive(stampDTO.getProcessId(), true);
                if (existingProcStamp != null) {
                    existingProcStamp.setActive(false);
                    stampRepository.save(existingProcStamp);
                }
            } else {
                resultWrapper.setStatus(false);
                resultWrapper.setMessage("process or subProcess or unit id must be provided");
                return resultWrapper;
            }
            stamp.setActive(true);
            stampRepository.save(stamp);
            resultWrapper.setStatus(true);
            resultWrapper.setMessage("stamp image added successfully");
            resultWrapper.setResult(StampMapper.INSTANCE.toDTO(stamp));
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("image is null");
            return resultWrapper;
        }
    }

    @Override
    public ResultWrapper<SignatureDTO> getEmployeeSignature(Long employeeId) {
        ResultWrapper<SignatureDTO> resultWrapper = new ResultWrapper<>();
        Signature signature = signatureRepository.findByEmployeeAndIsActive(employeeId, true);
        if (signature != null) {
            resultWrapper.setResult(SignatureMapper.INSTANCE.toDTO(signature));
            resultWrapper.setStatus(true);
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("No active signature found");
            return resultWrapper;
        }
    }

    @Override
    public ResultWrapper<StampDTO> getStampByOrgUnitId(Long orgUnitId) {
        ResultWrapper<StampDTO> resultWrapper = new ResultWrapper<>();
        Stamp stamp = stampRepository.findByOrganizationUnitIdIdAndIsActive(orgUnitId, true);
        if (stamp != null) {
            resultWrapper.setResult(StampMapper.INSTANCE.toDTO(stamp));
            resultWrapper.setStatus(true);
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("No active stamp found");
            return resultWrapper;
        }
    }

    @Override
    public ResultWrapper<StampDTO> getStampBySubProcessId(Long subProcessId) {
        ResultWrapper<StampDTO> resultWrapper = new ResultWrapper<>();
        Stamp stamp = stampRepository.findBySubProcessIdAndIsActive(subProcessId, true);
        if (stamp != null) {
            resultWrapper.setResult(StampMapper.INSTANCE.toDTO(stamp));
            resultWrapper.setStatus(true);
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("No active stamp found");
            return resultWrapper;
        }
    }

    @Override
    public ResultWrapper<StampDTO> getStampByProcessId(Long processId) {
        ResultWrapper<StampDTO> resultWrapper = new ResultWrapper<>();
        Stamp stamp = stampRepository.findByProcessIdAndIsActive(processId, true);
        if (stamp != null) {
            resultWrapper.setResult(StampMapper.INSTANCE.toDTO(stamp));
            resultWrapper.setStatus(true);
            return resultWrapper;
        } else {
            resultWrapper.setStatus(false);
            resultWrapper.setMessage("No active stamp found");
            return resultWrapper;
        }
    }

    @Override
    public ImageRes getImages(Long authorityId) throws IOException {
        Authority autho = authorityService.findAuthority(authorityId);
        if (autho == null) {
            return null;
        }


        ImageRes imgRes = new ImageRes();
        if (autho.getOrganizationalUnit() != null) {
            Stamp stamp = stampRepository.findByOrganizationUnitIdIdAndIsActive(autho.getOrganizationalUnit().getId(), true);
            BufferedImage stampImage = ImageIO.read(new File(stamp.getStampLink()));
            ByteArrayOutputStream stbos = new ByteArrayOutputStream();
            ImageIO.write(stampImage, "png", stbos);
            byte[] stadata = stbos.toByteArray();
            imgRes.setStamp(stadata);
            imgRes.setOrganizationalUnit(autho.getOrganizationalUnit());
        } else if (autho.getSubProcess() != null) {
            Stamp stamp = stampRepository.findBySubProcessIdAndIsActive(autho.getSubProcess().getId(), true);
            BufferedImage stampImage = ImageIO.read(new File(stamp.getStampLink()));
            ByteArrayOutputStream stbos = new ByteArrayOutputStream();
            ImageIO.write(stampImage, "png", stbos);
            byte[] stadata = stbos.toByteArray();
            imgRes.setStamp(stadata);
            imgRes.setSubProcess(autho.getSubProcess());
        } else if (autho.getProcess() != null) {
            Stamp stamp = stampRepository.findByProcessIdAndIsActive(autho.getProcess().getId(), true);
            BufferedImage stampImage = ImageIO.read(new File(stamp.getStampLink()));
            ByteArrayOutputStream stbos = new ByteArrayOutputStream();
            ImageIO.write(stampImage, "png", stbos);
            byte[] stadata = stbos.toByteArray();
            imgRes.setStamp(stadata);
            imgRes.setProcess(autho.getProcess());
        } else {
            return null;
        }

        Signature signature = signatureRepository.findByEmployeeAndIsActive(autho.getEmployee().getId(), true);
        BufferedImage sigImage = ImageIO.read(new File(signature.getSignatureLink()));
        ByteArrayOutputStream sibos = new ByteArrayOutputStream();
        ImageIO.write(sigImage, "png", sibos);
        byte[] sidata = sibos.toByteArray();
        imgRes.setSignature(sidata);
        imgRes.setEmployee(autho.getEmployee());

        return imgRes;
    }

    @Override
    public ImageRes getSignatureImages(Long signatureId) throws IOException {

        ImageRes imageRes = new ImageRes();
        Signature signature = signatureRepository.findById(signatureId).orElse(null);
        if (signature != null){
            BufferedImage sigImage = ImageIO.read(new File(signature.getSignatureLink()));
            ByteArrayOutputStream sibos = new ByteArrayOutputStream();
            ImageIO.write(sigImage, "png", sibos);
            byte[] sidata = sibos.toByteArray();
            imageRes.setSignature(sidata);
            imageRes.setEmployee(signature.getEmployee());
        }

        return imageRes;
    }

    @Override
    public ImageRes getStampImages(Long stampId) throws IOException {
        ImageRes imageRes = new ImageRes();
        Stamp stamp = stampRepository.findById(stampId).orElse(null);
        if (stamp != null){
            BufferedImage stampImage = ImageIO.read(new File(stamp.getStampLink()));
            ByteArrayOutputStream stbos = new ByteArrayOutputStream();
            ImageIO.write(stampImage, "png", stbos);
            byte[] stadata = stbos.toByteArray();
            imageRes.setStamp(stadata);
            imageRes.setProcess(stamp.getProcess());
            imageRes.setSubProcess(stamp.getSubProcess());
            imageRes.setOrganizationalUnit(stamp.getOrganizationalUnit());
        }

        return imageRes;
    }

    @Override
    public ResultWrapper<List<StampDTO>> getAllStamps() {

        ResultWrapper<List<StampDTO>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(StampMapper.INSTANCE.stampsToStampDTOs(stampRepository.findAllActiveStamps(true)));
        resultWrapper.setStatus(true);
        return resultWrapper;
    }

    @Override
    public ResultWrapper<List<SignatureDTO>> getAllSignatures() {
        ResultWrapper<List<SignatureDTO>> resultWrapper = new ResultWrapper<>();
        resultWrapper.setResult(SignatureMapper.INSTANCE.signatureToSignatureDTOs(signatureRepository.findAllActiveStamps(true)));
        resultWrapper.setStatus(true);
        return resultWrapper;
    }
}
