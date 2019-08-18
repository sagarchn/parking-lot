import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { catchError, retry } from 'rxjs/operators';
import { throwError } from 'rxjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  // your code goes here

  server_url = 'http://localhost:8080/api/parkings';

  errorAlert = false;
  error_message = '';
  newParking: ParkingLot;
  parkings: ParkingLot[] = [];

  vehicleForm = new FormGroup({
    vehicleLot: new FormControl(),
    vehicleNumber: new FormControl(),
    vehicleDuration: new FormControl(),
    vehicleAmount: new FormControl(),
  });

  constructor(private http: HttpClient) {

  }

  ngOnInit() {
    this.getAllParking();
  }
  validateForm(): boolean {

    if (this.newParking.lot <= 0) {
      this.errorAlert = true;
      this.error_message = 'LOT ADDRESS IS MANDATORY!';
      return true;
    }
    if (this.newParking.vehicleNumber <= 0) {
      this.errorAlert = true;
      this.error_message = 'VEHICLE NUMBER IS MANDATORY!';
      return true;
    }
    if (this.newParking.parkingDuration <= 0) {
      this.errorAlert = true;
      this.error_message = 'PARKING DURATION IS MANDATORY!';
      return true;
    }

    return false;
    //  || this.newParking.parkingDuration == 0 || this.newParking.vehicleNumber == 0;
  }

  onSubmit() {



    this.newParking = new ParkingLot();
    this.newParking.lot = this.vehicleForm.value['vehicleLot'];
    this.newParking.parkingDuration = +this.vehicleForm.value['vehicleDuration'];
    this.newParking.vehicleNumber = +this.vehicleForm.value['vehicleNumber'];
    this.newParking.parkingAmount = +this.vehicleForm.value['vehicleAmount'];

    if (this.validateForm()) {
      return;
    }

    this.parkNewvehicle();
  }

  parkNewvehicle() {

    console.log(this.vehicleForm.value);
    this.http.post<ParkingLot>(this.server_url, JSON.stringify(this.newParking), { headers: new HttpHeaders().set('content-type', 'application/json') }).pipe(
      catchError(this.handleError)
    ).subscribe(data => {

      this.vehicleForm.reset();
      this.getAllParking();
    }, error => {
      this.errorAlert = true;
      this.error_message = error;
    });

  }

  private handleError(error: HttpErrorResponse) {
    console.error(error.error.message);

    if (`VEHICLE ALREADY PARKED!` === error.error.message
    )
      return throwError(`${error.error.message}
      `);
    else return throwError('SOMETHING WENT WRONG!');
  };

  getAllParking() {
    this.errorAlert = false;
    this.error_message = '';
    console.log('server ......');
    this.http.get<ParkingLot[]>(this.server_url).subscribe(data => { this.parkings = data; }, error => { alert(error); });
  }

  calculateAmount(e) {
    
    let amount = 0;
    let time = e.target.value;
    if (time > 0) {
      if (time <= 60) {
        amount = 20;
      }else{
        amount=20+(time-60)*0.333;
      }
    }else{
      amount=20;
    }
    this.vehicleForm.patchValue({vehicleAmount:Math.trunc(amount)});
    console.log(amount);
  }





}

export class ParkingLot {

  id: number;
  createdAt: any;
  lot: number;
  parkingAmount: number;
  parkingDuration: number;
  updatedAt;
  vehicleNumber: number;
}
