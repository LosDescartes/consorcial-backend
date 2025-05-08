package com.seat.consorcial_backend.application

import com.seat.consorcial_backend.domain.ChargingPoint
import org.springframework.stereotype.Service

@Service
class ChargingPointsRetrieverImpl : ChargingPointsRetriever {
    override fun doUseCase(request: ChargingPointsRetriever.Request): List<ChargingPoint> {
        // TODO: Implementar la lógica real de negocio
        return emptyList()
    }
} 