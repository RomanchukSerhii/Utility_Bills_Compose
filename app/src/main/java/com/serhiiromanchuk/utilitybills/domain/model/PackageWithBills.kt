package com.serhiiromanchuk.utilitybills.domain.model

data class PackageWithBills(
    val billPackage: BillPackage,
    private val bills: List<Bill>
) {

    private val billComparator = Comparator { bill1: Bill, bill2: Bill ->
//        val dateFormat = SimpleDateFormat("LLLL yyyy", Locale.getDefault())
//        val date1 = dateFormat.parse(bill1.date)
//        val date2 = dateFormat.parse(bill2.date)
//        if (date1 == null || date2 == null) return@Comparator 0
//        date1.compareTo(date2)
        bill1.date.compareTo(bill2.date)
    }

    val sortedBills = bills.sortedWith(billComparator)
}