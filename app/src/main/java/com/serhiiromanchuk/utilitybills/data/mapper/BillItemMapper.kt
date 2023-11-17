package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillItemDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillItem

class BillItemMapper {
    fun mapEntityToDbModel(billItem: BillItem): BillItemDbModel {
        return BillItemDbModel(
            id = billItem.id,
            address = billItem.address,
            month = billItem.month,
            year = billItem.year,
            cardNumber = billItem.cardNumber,
            utilityServices = billItem.utilityServices,
            billDescription = billItem.billDescription
        )
    }

    private fun mapDbModelToEntity(billItemDbModel: BillItemDbModel): BillItem {
        return BillItem(
            id = billItemDbModel.id,
            address = billItemDbModel.address,
            month = billItemDbModel.month,
            year = billItemDbModel.year,
            cardNumber = billItemDbModel.cardNumber,
            utilityServices = billItemDbModel.utilityServices,
            billDescription = billItemDbModel.billDescription
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<BillItemDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }

}