package com.skillenza.parkinglotjava;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkinglotjavaApplicationTests {

	@Autowired
	private ParkingLotRepository parkingLotRepo;

	// your test goes here

	/**
	 * assuming 2 data available! fetch and check size
	 * 
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	@Test
	public void checkForFetchAllParkingLotAPI() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> data = restTemplate.getForEntity("http://localhost:8080/api/parkings", List.class);

		List list = data.getBody();

		Assert.assertEquals(list.size(), 5);
	}

	/**
	 * assuming 2 data available! fetch and check size
	 * 
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	@Test
	public void saveParkingLot() throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		ParkingLot requestBody = new ParkingLot(201, LocalDateTime.now(), LocalDateTime.now(), 30, 1231);
		ResponseEntity<ParkingLot> data = restTemplate.postForEntity("http://localhost:8080/api/parkings", requestBody,
				ParkingLot.class);

		ParkingLot responseBody = data.getBody();

		Assert.assertEquals(responseBody.getParkingAmount(), 20);

	}

}
