package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.Embedded
import androidx.room.Relation

data class PackageWithBillsDbModel(
    @Embedded val billPackage: BillPackageDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "package_creator_id"
    )
    val bills: List<BillDbModel>
)