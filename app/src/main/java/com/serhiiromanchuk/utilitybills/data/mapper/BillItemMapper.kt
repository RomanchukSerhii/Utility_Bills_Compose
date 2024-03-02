package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.BillWithUtilityServicesDbModel
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillWithUtilityServices
import javax.inject.Inject

class BillItemMapper @Inject constructor(
    private val utilityServiceMapper: UtilityServiceItemMapper
) {
    fun mapEntityToDbModel(billItem: Bill): BillDbModel {
        return BillDbModel(
            id = billItem.id,
            packageCreatorId = billItem.packageCreatorId,
            payerName = billItem.payerName,
            address = billItem.address,
            date = billItem.date,
            billDescription = billItem.billDescription
        )
    }

    private fun mapDbModelToEntity(billItemDbModel: BillDbModel): Bill {
        return Bill(
            id = billItemDbModel.id,
            packageCreatorId = billItemDbModel.packageCreatorId,
            payerName = billItemDbModel.payerName,
            address = billItemDbModel.address,
            date = billItemDbModel.date,
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

    fun mapListEntityToListDbModel(billItems: List<Bill>) = billItems.map {
        mapEntityToDbModel(it)
    }

    fun mapListDbModelToListEntity(billDbModels: List<BillDbModel>) = billDbModels.map {
        mapDbModelToEntity(it)
    }
}