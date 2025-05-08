package com.seat.consorcial_backend.infrastructure.web

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.seat.consorcial_backend.application.ChargingPointsRetriever
import com.seat.consorcial_backend.application.ChargingPointsRetrieverImpl
import com.seat.consorcial_backend.domain.ChargingPoint
import com.seat.consorcial_backend.domain.ChargingPointRepository
import com.seat.consorcial_backend.domain.ChargingPointStatus
import com.seat.consorcial_backend.domain.DemandLevel
import org.hamcrest.Matchers.equalTo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import com.seat.consorcial_backend.infrastructure.web.GlobalExceptionHandler
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ChargingPointsControllerAcceptanceTest {

    private lateinit var controller: ChargingPointsController
    private lateinit var exceptionHandler: GlobalExceptionHandler

    @LocalServerPort
    val port: Int = 0

    @MockBean
    private lateinit var repository: ChargingPointRepository

    @BeforeEach
    fun setup() {
        val useCase = ChargingPointsRetrieverImpl(repository)
        controller = ChargingPointsController(useCase)
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
        
        Mockito.`when`(repository.findByFilters(
            latitude = null,
            longitude = null,
            radius = null,
            brands = null,
            status = null,
            demand = null,
            amenities = null,
            connectorTypes = null,
            search = null
        )).thenReturn(chargingPoints)

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
        
        Mockito.`when`(repository.findByFilters(
            latitude = 41.3851,
            longitude = 2.1734,
            radius = 50.0,
            brands = null,
            status = ChargingPointStatus.AVAILABLE,
            demand = DemandLevel.LOW,
            amenities = null,
            connectorTypes = null,
            search = null
        )).thenReturn(chargingPoints)

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
            .body("message", equalTo("Parameter 'radius' should be of type Double"))
    }

} 