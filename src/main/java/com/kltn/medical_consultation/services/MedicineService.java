package com.kltn.medical_consultation.services;

import com.kltn.medical_consultation.entities.database.Medicine;
import com.kltn.medical_consultation.models.ApiException;
import com.kltn.medical_consultation.models.BaseResponse;
import com.kltn.medical_consultation.models.ERROR;
import com.kltn.medical_consultation.models.medicine.AddMedicineRequest;
import com.kltn.medical_consultation.models.medicine.MedicineDTO;
import com.kltn.medical_consultation.repository.database.MedicineRepository;
import com.kltn.medical_consultation.utils.MessageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MedicineService extends BaseService {

    @Autowired
    private MedicineRepository medicineRepository;

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
}
