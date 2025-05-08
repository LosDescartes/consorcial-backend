package com.seat.consorcial_backend.domain

data class ChargingPoint(
    val id: String,
    val name: String,
    val operator: String,
    val brand: String,
    val status: ChargingPointStatus,
    val latitude: Double,
    val longitude: Double,
    val demand: DemandLevel
)

enum class ChargingPointStatus {
    AVAILABLE, BUSY, OFFLINE
}

enum class DemandLevel {
    LOW, MEDIUM, HIGH
} 