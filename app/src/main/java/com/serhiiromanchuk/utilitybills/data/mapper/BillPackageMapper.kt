package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillPackageDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.PackageWithBillsDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.model.PackageWithBills
import javax.inject.Inject

class BillPackageMapper @Inject constructor(
    private val billItemMapper: BillItemMapper
) {

    fun mapEntityToDbModel(entity: BillPackage): BillPackageDbModel {
        return BillPackageDbModel(
            id = entity.id,
            name = entity.name,
            payerName = entity.payerName,
            address = entity.address,
            indexPosition = entity.indexPosition
        )
    }

    private fun mapDbModelToEntity(dbModel: BillPackageDbModel): BillPackage {
        return BillPackage(
            id = dbModel.id,
            name = dbModel.name,
            payerName = dbModel.payerName,
            address = dbModel.address,
            indexPosition = dbModel.indexPosition
        )
    }

    fun mapPackageWithBillDbModelToEntity(dbModel: PackageWithBillsDbModel): PackageWithBills {
        return PackageWithBills(
            billPackage = mapDbModelToEntity(dbModel.billPackage),
            bills = billItemMapper.mapListDbModelToListEntity(dbModel.bills)
        )
    }

    fun mapListDbModelToListEntity(dbModels: List<BillPackageDbModel>) = dbModels.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(entities: List<BillPackage>) = entities.map {
        mapEntityToDbModel(it)
    }

}