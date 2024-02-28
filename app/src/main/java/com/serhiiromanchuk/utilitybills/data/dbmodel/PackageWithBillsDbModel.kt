package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.Embedded
import androidx.room.Relation
import com.serhiiromanchuk.utilitybills.domain.model.Bill
import com.serhiiromanchuk.utilitybills.domain.model.BillPackage

data class PackageWithBillsDbModel(
    @Embedded val billPackage: BillPackage,
    @Relation(
        parentColumn = "id",
        entityColumn = "package_creator_id"
    )
    val bills: List<Bill>
)