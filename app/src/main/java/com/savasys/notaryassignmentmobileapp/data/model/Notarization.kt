package com.savasys.notaryassignmentmobileapp.data.model

import java.util.*

data class Notarization(
    val id: Long,
    var escrowNumber: String,
    var escrowOfficerId: Long,
    var category: NotarizationCategory,
    var powerOfAttorneySigning: Boolean,
    var fee: Float,
    var date: Date,
    var status: NotarizationStatus,
    var deliveryMethod: NotarizationDeliveryMethod,
    var comments: String,
    var client: NotarizationClient,
    var notaryId: Long?
) {
    override fun toString(): String {
        return "id:$id escrowNumber:$escrowNumber escrowOfficerId:$escrowOfficerId category:$category powerOfAttorneySigning:$powerOfAttorneySigning fee:$fee" +
                " date:$date status:$status deliveryMethod:$deliveryMethod comments:$comments client:$client notaryId:$notaryId"
    }
}
