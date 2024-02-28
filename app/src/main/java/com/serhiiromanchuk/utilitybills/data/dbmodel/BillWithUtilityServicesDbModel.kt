package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.Embedded
import androidx.room.Relation

data class BillWithUtilityServicesDbModel(
    @Embedded val bill: BillDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "bill_creator_id"
    )
    val utilityServices: List<UtilityServiceDbModel>
)
