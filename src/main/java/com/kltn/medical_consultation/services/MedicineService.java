package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Medicine;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BasePaginationResponse;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.medicine.*;
import com.kltn.medical_consultation.repository.database.MedicineRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MedicineService extends BaseService {

    @Autowired
    private MedicineRepository medicineRepository;

    public BaseResponse listMedicine(ListMedicineRequest listMedicineRequest) throws ApiException {
        listMedicineRequest.buildJpaQuery();
        ListMedicinePagingResponse medicinePagingResponse = new ListMedicinePagingResponse();
        if (listMedicineRequest.getIsPage()) {
            Pageable pageable = PageRequest.of(listMedicineRequest.getPageIndex(),
                    listMedicineRequest.getPageSize(),
                    Sort.by(Sort.Direction.fromString(listMedicineRequest.getOrder()), listMedicineRequest.getOrderBy()));

            Page<Medicine> medicinePage = medicineRepository.listMedicinePage(listMedicineRequest.getName(), pageable);

            List<MedicineDTO> medicines = medicinePage.getContent().stream().map(medicine -> {
                MedicineDTO medicineDTO = new MedicineDTO(medicine);
                return medicineDTO;
            }).collect(Collectors.toList());

            medicinePagingResponse.setMedicines(medicines);
            medicinePagingResponse.setTotalItems(medicinePage.getTotalElements());
            medicinePagingResponse.setPageIndex(listMedicineRequest.getPageIndex());
            medicinePagingResponse.setPageSize(listMedicineRequest.getPageSize());
            medicinePagingResponse.setOrderBy(listMedicineRequest.getOrderBy());
            medicinePagingResponse.setOrder(listMedicineRequest.getOrder());
        } else {
            List<MedicineDTO> medicines = medicineRepository.listMedicine(listMedicineRequest.getName())
                .stream().map(medicine -> {
                    MedicineDTO medicineDTO = new MedicineDTO(medicine);
                    return medicineDTO;
                }).collect(Collectors.toList());

            medicinePagingResponse.setMedicines(medicines);
            medicinePagingResponse.setTotalItems(Long.valueOf(medicines.size()));
        }

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(medicinePagingResponse);
        return baseResponse;
    }

    public BaseResponse addMedicine(AddMedicineRequest request) {

        if (StringUtils.isEmpty(request.getName())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Name"));
        }
        if (StringUtils.isEmpty(request.getActiveIngredient())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("ActiveIngredient"));
        }
        if (StringUtils.isEmpty(request.getUnit())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Unit"));
        }
        if (StringUtils.isEmpty(request.getDosageStrength())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("DosageStrength"));
        }
        if (StringUtils.isEmpty(request.getQuantity().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Quantity"));
        }

        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setActiveIngredient(request.getActiveIngredient());
        medicine.setUnit(request.getUnit());
        medicine.setDosageStrength(request.getDosageStrength());
        medicine.setQuantity(request.getQuantity());
        medicine.setPricePerUnit(request.getPricePerUnit());
        medicine.setNote(request.getNote());

        medicine = medicineRepository.save(medicine);
        BaseResponse baseResponse = new BaseResponse<>();
        baseResponse.setData(new MedicineDTO(medicine));
        return baseResponse;
    }

    public BaseResponse deleteMedicine(DeleteMedicineRequest request) {
        if (StringUtils.isEmpty(request.getId().toString())) {
            throw new ApiException(ERROR.INVALID_PARAM, MessageUtils.paramRequired("Id"));
        }

        Optional<Medicine> optionalMedicine = medicineRepository.findByIdAndIsDeleteFalse(request.getId());
        if (optionalMedicine.isEmpty()) {
            return new BaseResponse(MedicineMessageCode.MEDICINE_NOT_FOUND);
        }

        Medicine medicine = optionalMedicine.get();
        medicine.setIsDelete(true);
        medicineRepository.save(medicine);
        return new BaseResponse(ERROR.SUCCESS);
    }
}
