package com.skillenza.parkinglotjava;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ParkingLotController {

	// your code goes here

	@Autowired
	private ParkingLotRepository parkingLotRepository;

	@GetMapping("/parkings")
	public List<ParkingLot> getParking() {
		try {

			return parkingLotRepository.findAll();
		} catch (Exception e) {
			return new LinkedList<>();
		}
	}

	@PostMapping("/parkings")
	public ParkingLot saveParking(@RequestBody ParkingLot lot) throws Exception {
		try {

			Assert.notNull(lot, "Lot cannot be null!");
			// validate posted data
			lot.validate();

			ParkingLot lotPresent = parkingLotRepository.findByLot(lot.getLot());

			if (lotPresent != null)
				throw new Exception("VEHICLE ALREADY PARKED!");
			// calculate amount
			calculateParkingAmount(lot);

			return parkingLotRepository.save(lot);
		} catch (Exception e) {
			System.out.println(e);
			throw new Exception(e.getMessage());

		}
	}

	/**
	 * set parking amount
	 * 
	 * @param lot
	 */
	private void calculateParkingAmount(ParkingLot lot) {

		if (lot.getParkingDuration() <= 60) {
			lot.setParkingAmount(20);
		} else {
			lot.setParkingAmount(20 + (int) ((lot.getParkingDuration() - 60) * 0.333));
		}

	}

}
