package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillItemDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillWithUtilityServicesDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillItem
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import java.util.Locale
import javax.inject.Inject

class BillItemMapper @Inject constructor(
    private val utilityServiceMapper: UtilityServiceItemMapper
) {
    fun mapEntityToDbModel(billItem: BillItem): BillItemDbModel {
        return BillItemDbModel(
            id = billItem.id,
            payerName = billItem.payerName,
            address = billItem.address,
            month = billItem.month,
            year = billItem.year,
            indexPosition = billItem.indexPosition,
            billDescription = billItem.billDescription
        )
    }

    private fun mapDbModelToEntity(billItemDbModel: BillItemDbModel): BillItem {
        return BillItem(
            id = billItemDbModel.id,
            payerName = billItemDbModel.payerName,
            address = billItemDbModel.address,
            month = billItemDbModel.month.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            },
            year = billItemDbModel.year,
            indexPosition = billItemDbModel.indexPosition,
            billDescription = billItemDbModel.billDescription
        )
    }

    fun mapBillWithServicesDbModelToEntity(
        billWithServicesDbModel: BillWithUtilityServicesDbModel
    ): BillWithUtilityServices {
        return BillWithUtilityServices(
            bill = mapDbModelToEntity(billWithServicesDbModel.bill),
            utilityServices = utilityServiceMapper.mapListDbModelToListEntity(
                billWithServicesDbModel.utilityServices
            )
        )
    }

    fun mapListDbModelToListEntity(dbModelList: List<BillItemDbModel>) = dbModelList.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(billItems: List<BillItem>) = billItems.map {
        mapEntityToDbModel(it)
    }

//    fun mapListBillWithServiceDbModelToListEntity(
//        dbModelList: List<BillWithUtilityServicesDbModel>
//    ) = dbModelList.map { mapBillWithServicesDbModelToEntity(it) }

}