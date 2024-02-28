package com.serhiiromanchuk.utilitybills.data.mapper

import com.serhiiromanchuk.utilitybills.data.dbmodel.BillPackageDbModel
import com.serhiiromanchuk.utilitybills.data.dbmodel.PackageWithBillsDbModel
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage
import com.serhiiromanchuk.utilitybills.domain.model.PackageWithBills
import javax.inject.Inject

class BillPackageMapper @Inject constructor() {

    fun mapEntityToDbModel(entity: BillPackage): BillPackageDbModel {
        return BillPackageDbModel(
            id = entity.id,
            name = entity.name,
            indexPosition = entity.indexPosition
        )
    }

    private fun mapDbModelToEntity(dbModel: BillPackageDbModel): BillPackage {
        return BillPackage(
            id = dbModel.id,
            name = dbModel.name,
            indexPosition = dbModel.indexPosition
        )
    }

    fun mapPackageWithBillDbModelToEntity(dbModel: PackageWithBillsDbModel): PackageWithBills {
        return PackageWithBills(
            billPackage = dbModel.billPackage,
            bills = dbModel.bills
        )
    }

    fun mapListDbModelToListEntity(dbModels: List<BillPackageDbModel>) = dbModels.map {
        mapDbModelToEntity(it)
    }

    fun mapListEntityToListDbModel(entities: List<BillPackage>) = entities.map {
        mapEntityToDbModel(it)
    }

}