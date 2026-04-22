package com.studwarcraft.resource;

import com.studwarcraft.model.Player;
import com.studwarcraft.model.TimeResponse;
import com.studwarcraft.rest.client.IpApi;
import com.studwarcraft.rest.client.TimeApi;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@QuarkusTest
class PlayerResourceTest {

    private Long playerId;

    @Inject
    EntityManager em;

    @InjectMock
    @RestClient
    IpApi ipApi;

    @InjectMock
    @RestClient
    TimeApi timeApi;

    @BeforeEach
    @Transactional
    void setUp() {
        em.createQuery("DELETE FROM PlayerTimeZone").executeUpdate();
        em.createQuery("DELETE FROM PlayerDetails").executeUpdate();
        em.createQuery("DELETE FROM Profile").executeUpdate();
        em.createQuery("DELETE FROM Player").executeUpdate();

        Player player = new Player();
        player.setName("Test");
        player.setLastname("User");
        em.persist(player);
        em.flush();
        playerId = player.getPlayerid();
    }

    @Test
    void shouldAssignTimeZoneToExistingPlayer() {
        when(ipApi.getIp()).thenReturn("94.102.228.55");

        TimeResponse response = new TimeResponse();
        response.setYear(2026);
        response.setMonth(4);
        response.setDay(22);
        response.setHour(14);
        response.setMinute(30);
        response.setSeconds(0);
        response.setMilliSeconds(0);
        response.setDateTime("2026-04-22T14:30:00");
        response.setDate("2026-04-22");
        response.setTime("14:30");
        response.setTimeZone("Europe/Podgorica");
        response.setDayOfWeek("Wednesday");
        response.setDstActive(true);

        when(timeApi.getTimeByIp(eq("94.102.228.55"))).thenReturn(response);

        given()
                .when()
                .get("/getTimezoneByIP?userid=" + playerId)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("timeZone", is("Europe/Podgorica"))
                .body("dayOfWeek", is("Wednesday"));
    }

    @Test
    void shouldReturnNotFoundWhenPlayerDoesNotExist() {
        given()
                .when()
                .get("/getTimezoneByIP?userid=999")
                .then()
                .statusCode(404);
    }
}
