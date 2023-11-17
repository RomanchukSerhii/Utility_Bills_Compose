package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.UtilityServiceItemDbModel
import com.serhiiromanchuk.utilitybills.domain.model.UtilityServiceItem

class UtilityServiceItemMapper {
    fun mapEntityToDbModel(entity: UtilityServiceItem): UtilityServiceItemDbModel {
        return UtilityServiceItemDbModel(
            id = entity.id,
            address = entity.address,
            name = entity.name,
            tariff = entity.tariff,
            isMeterAvailable = entity.isMeterAvailable,
            previousValue = entity.previousValue,
            currentValue = entity.currentValue
        )
    }

    private fun mapDbModelToEntity(dbModel: UtilityServiceItemDbModel): UtilityServiceItem {
        return UtilityServiceItem(
            id = dbModel.id,
            address = dbModel.address,
            name = dbModel.name,
            tariff = dbModel.tariff,
            isMeterAvailable = dbModel.isMeterAvailable,
            previousValue = dbModel.previousValue,
            currentValue = dbModel.currentValue
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<UtilityServiceItemDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }
}