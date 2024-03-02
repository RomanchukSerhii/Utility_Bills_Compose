package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceDbModel
import com.serhiiromanchuk.utilitybills.domain.model.UtilityService
import javax.inject.Inject

class UtilityServiceItemMapper @Inject constructor() {
    fun mapEntityToDbModel(entity: UtilityService): UtilityServiceDbModel {
        return UtilityServiceDbModel(
            id = entity.id,
            billCreatorId = entity.billCreatorId,
            name = entity.name,
            tariff = entity.tariff,
            isMeterAvailable = entity.isMeterAvailable,
            previousValue = entity.previousValue,
            currentValue = entity.currentValue,
            unitOfMeasurement = entity.unitOfMeasurement
        )
    }

    fun mapDbModelToEntity(dbModel: UtilityServiceDbModel): UtilityService {
        return UtilityService(
            id = dbModel.id,
            billCreatorId = dbModel.billCreatorId,
            name = dbModel.name,
            tariff = dbModel.tariff,
            isMeterAvailable = dbModel.isMeterAvailable,
            previousValue = dbModel.previousValue,
            currentValue = dbModel.currentValue,
            unitOfMeasurement = dbModel.unitOfMeasurement
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<UtilityServiceDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }
}