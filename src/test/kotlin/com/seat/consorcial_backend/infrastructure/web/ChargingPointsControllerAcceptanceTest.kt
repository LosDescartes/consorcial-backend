package com.seat.consorcial_backend.infrastructure.web

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.seat.consorcial_backend.application.ChargingPointsRetriever
import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import com.seat.consorcial_backend.infrastructure.web.GlobalExceptionHandler

class ChargingPointsControllerAcceptanceTest {

    private lateinit var chargingPointsRetriever: ChargingPointsRetriever
    private lateinit var controller: ChargingPointsController
    private lateinit var exceptionHandler: GlobalExceptionHandler

    @BeforeEach
    fun setup() {
        chargingPointsRetriever = Mockito.mock(ChargingPointsRetriever::class.java)
        controller = ChargingPointsController(chargingPointsRetriever)
        exceptionHandler = GlobalExceptionHandler()
        
        val mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(exceptionHandler)
            .build()
            
        RestAssuredMockMvc.mockMvc(mockMvc)
    }

    @Test
    fun `should return charging points when no filters are provided`() {
        // Given
        val chargingPoints = listOf(
            ChargingPoint(
                id = "1",
                name = "Test Charger 1",
                operator = "Test Operator",
                brand = "Test Brand",
                status = ChargingPointStatus.AVAILABLE,
                latitude = 41.3851,
                longitude = 2.1734,
                demand = DemandLevel.LOW
            )
        )
        
        Mockito.`when`(chargingPointsRetriever.doUseCase(any<ChargingPointsRetriever.Request>()))
            .thenReturn(chargingPoints)

        // When/Then
        RestAssuredMockMvc.given()
            .`when`()
            .get("/charging-points")
            .then()
            .statusCode(200)
            .body("[0].id", equalTo("1"))
            .body("[0].name", equalTo("Test Charger 1"))
            .body("[0].operator", equalTo("Test Operator"))
            .body("[0].brand", equalTo("Test Brand"))
            .body("[0].status", equalTo("AVAILABLE"))
            .body("[0].demand", equalTo("LOW"))
    }

    @Test
    fun `should return filtered charging points when filters are provided`() {
        // Given
        val chargingPoints = listOf(
            ChargingPoint(
                id = "1",
                name = "Test Charger 1",
                operator = "Test Operator",
                brand = "Test Brand",
                status = ChargingPointStatus.AVAILABLE,
                latitude = 41.3851,
                longitude = 2.1734,
                demand = DemandLevel.LOW
            )
        )
        
        Mockito.`when`(chargingPointsRetriever.doUseCase(any<ChargingPointsRetriever.Request>()))
            .thenReturn(chargingPoints)

        // When/Then
        RestAssuredMockMvc.given()
            .queryParam("lat", 41.3851)
            .queryParam("lng", 2.1734)
            .queryParam("radius", 50)
            .queryParam("status", "AVAILABLE")
            .queryParam("demand", "LOW")
            .`when`()
            .get("/charging-points")
            .then()
            .statusCode(200)
            .body("[0].id", equalTo("1"))
            .body("[0].status", equalTo("AVAILABLE"))
            .body("[0].demand", equalTo("LOW"))
    }

    @Test
    fun `should return 400 when latitude is invalid`() {
        RestAssuredMockMvc.given()
            .queryParam("lat", "invalid")
            .queryParam("lng", 2.1734)
            .`when`()
            .get("/charging-points")
            .then()
            .statusCode(400)
            .body("status", equalTo(400))
            .body("error", equalTo("Bad Request"))
            .body("message", equalTo("Parameter 'lat' should be of type Double"))
    }

    @Test
    fun `should return 400 when longitude is invalid`() {
        RestAssuredMockMvc.given()
            .queryParam("lat", 41.3851)
            .queryParam("lng", "invalid")
            .`when`()
            .get("/charging-points")
            .then()
            .statusCode(400)
            .body("status", equalTo(400))
            .body("error", equalTo("Bad Request"))
            .body("message", equalTo("Parameter 'lng' should be of type Double"))
    }

    @Test
    fun `should return 400 when radius is invalid`() {
        RestAssuredMockMvc.given()
            .queryParam("radius", "invalid")
            .`when`()
            .get("/charging-points")
            .then()
            .statusCode(400)
            .body("status", equalTo(400))
            .body("error", equalTo("Bad Request"))
            .body("message", equalTo("Parameter 'radius' should be of type double"))
    }

} 