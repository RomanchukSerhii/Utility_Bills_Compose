package com.serhiiromanchuk.utilitybills.domain.model

data class PackageWithBills(
    val billPackage: BillPackage,
    val bills: List<Bill>
)