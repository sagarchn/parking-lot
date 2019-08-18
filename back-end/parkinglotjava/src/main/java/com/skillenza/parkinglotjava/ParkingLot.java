package com.skillenza.parkinglotjava;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "parkinglots")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class ParkingLot {

	// your code goes here

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;

	@Column(unique = true, nullable = false)
	private int lot;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "parking_amount", nullable = false)
	private int parkingAmount;

	@Column(name = "parking_duration", nullable = false)
	private int parkingDuration;

	@Column(name = "vehicle_number", unique = true, nullable = false)
	private int vehicleNumber;

	public ParkingLot() {
	}

	public ParkingLot(int lot, LocalDateTime createdAt, LocalDateTime updatedAt, int parkingDuration,
			int vehicleNumber) {
		this.lot = lot;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.parkingDuration = parkingDuration;
		this.vehicleNumber = vehicleNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLot() {
		return lot;
	}

	public void setLot(int lot) {
		this.lot = lot;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getParkingAmount() {
		return parkingAmount;
	}

	public void setParkingAmount(int parkingAmount) {
		this.parkingAmount = parkingAmount;
	}

	public int getParkingDuration() {
		return parkingDuration;
	}

	public void setParkingDuration(int parkingDuration) {
		this.parkingDuration = parkingDuration;
	}

	public int getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(int vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public void validate() throws Exception {

		// custom validation can be written example one written

		if (vehicleNumber == 0)
			throw new Exception("");

	}

}
