package com.seat.consorcial_backend.infrastructure.web

import com.seat.consorcial_backend.application.ChargingPointsRetriever
import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/charging-points")
class ChargingPointsController(
    private val chargingPointsRetriever: ChargingPointsRetriever
) {
    @GetMapping
    fun getChargingPoints(
        @RequestParam(required = false) lat: Double?,
        @RequestParam(required = false) lng: Double?,
        @RequestParam(required = false) radius: Double?,
        @RequestParam(required = false) brands: List<String>?,
        @RequestParam(required = false) status: String?,
        @RequestParam(required = false) demand: String?,
        @RequestParam(required = false) amenities: List<String>?,
        @RequestParam(required = false) connectorTypes: List<String>?,
        @RequestParam(required = false) search: String?
    ): List<ChargingPointResponse> {
        val request = ChargingPointsRetriever.Request(
            latitude = lat,
            longitude = lng,
            radius = radius,
            brands = brands,
            status = status?.let { ChargingPointStatus.valueOf(it) },
            demand = demand?.let { DemandLevel.valueOf(it) },
            amenities = amenities,
            connectorTypes = connectorTypes,
            search = search
        )
        
        return chargingPointsRetriever.doUseCase(request).map { it.toResponse() }
    }
} 