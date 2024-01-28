package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceItemDbModel
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem
import javax.inject.Inject

class UtilityServiceItemMapper @Inject constructor() {
    fun mapEntityToDbModel(entity: UtilityServiceItem): UtilityServiceItemDbModel {
        return UtilityServiceItemDbModel(
            id = entity.id,
            address = entity.address,
            name = entity.name,
            tariff = entity.tariff,
            isMeterAvailable = entity.isMeterAvailable,
            previousValue = entity.previousValue,
            currentValue = entity.currentValue,
            unitOfMeasurement = entity.unitOfMeasurement,
            isChecked = entity.isChecked
        )
    }

    fun mapDbModelToEntity(dbModel: UtilityServiceItemDbModel): UtilityServiceItem {
        return UtilityServiceItem(
            id = dbModel.id,
            address = dbModel.address,
            name = dbModel.name,
            tariff = dbModel.tariff,
            isMeterAvailable = dbModel.isMeterAvailable,
            previousValue = dbModel.previousValue,
            currentValue = dbModel.currentValue,
            unitOfMeasurement = dbModel.unitOfMeasurement,
            isChecked = dbModel.isChecked
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<UtilityServiceItemDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }
}