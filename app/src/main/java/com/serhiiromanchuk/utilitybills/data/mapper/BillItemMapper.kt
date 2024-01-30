package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillItemDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillWithUtilityServiceListsDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServiceLists
import javax.inject.Inject

class BillItemMapper @Inject constructor(
    private val utilityServiceMapper: UtilityServiceItemMapper
) {
    fun mapEntityToDbModel(billItem: BillItem): BillItemDbModel {
        return BillItemDbModel(
            id = billItem.id,
            address = billItem.address,
            month = billItem.month,
            year = billItem.year,
            cardNumber = billItem.cardNumber,
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
            billDescription = billItemDbModel.billDescription
        )
    }
    private fun mapBillWithServicesDbModelToEntity(
        billWithServicesDbModel: BillWithUtilityServiceListsDbModel
    ): BillWithUtilityServiceLists {
        return BillWithUtilityServiceLists(
            bill = mapDbModelToEntity(billWithServicesDbModel.bill),
            utilityServices = utilityServiceMapper.mapListDbModelToListEntity(
                billWithServicesDbModel.utilityServices
            )
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<BillItemDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }

    fun mapListBillWithServiceDbModelToListEntity(
        dbModelList: List<BillWithUtilityServiceListsDbModel>
    ) = dbModelList.map { mapBillWithServicesDbModelToEntity(it) }

}