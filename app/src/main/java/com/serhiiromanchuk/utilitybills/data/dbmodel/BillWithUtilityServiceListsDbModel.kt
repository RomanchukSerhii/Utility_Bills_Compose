package com.serhiiromanchuk.utilitybills.data.dbmodel

import androidx.room.Embedded
import androidx.room.Relation

data class BillWithUtilityServiceListsDbModel(
    @Embedded val bill: BillItemDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "bill_creator_id"
    )
    val utilityServices: List<UtilityServiceItemDbModel>
)
