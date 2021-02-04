package com.savasys.notaryassignmentmobileapp.data.model

data class EscrowOfficer(
    val id: Long,
    var name: String,
    var lastName: String,
    var address: String,
    var phoneNumber: String,
    var email: String,
    var company: String
)