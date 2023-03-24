import {Component, OnInit} from '@angular/core';
import {Station} from "../models/station";
import {StationService} from "../services/station.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Stop} from "../models/stop";
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, map} from "rxjs";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  stations: Station[] = [];
  results: string[] = ['Loading...'];
  stationNames: string[] = [];
  displayResults: boolean = false;
  displaySearch: boolean = true;
  submitted: boolean = false;
  errorResponse: boolean = false;
  errorResults: string = '';
  connectionInfo: string = '';

  form: FormGroup = new FormGroup({
    from: new FormControl(''),
    to: new FormControl(''),
    time: new FormControl(''),
  });

  constructor(private formBuilder: FormBuilder, private stationService: StationService, private http: HttpClient) {
  }

  private getStations(): void {
    this.stationService.findAll().subscribe(res => res.map(result => this.stations.push(result)));
  }

  ngOnInit() {
    this.getStations();

    this.form = this.formBuilder.group(
      {
        from: ['', [Validators.required]],
        to: ['', [Validators.required]],
        time: ['', [Validators.required]]
      });
  }

  private loadNames(): string[] {
    let result: string[] = [];

    this.stations.forEach(function (current) {
      result.push(current.name);
    })

    return result;
  }

  public initNames(): void {
    this.submitted = false;
    this.stationNames = this.loadNames();
    this.stationNames.sort();
  }

  public onSubmit(): void {
    this.submitted = true;

    if (this.form.invalid) {
      return;
    }

    this.connectionInfo = this.form.value.from + " : " + this.form.value.to + ", " + this.form.value.time;

    let query: string = this.createQueryLink(this.form.value.from, this.form.value.to, this.form.value.time);
    this.http.get(query).subscribe(
      connectionList => {
        let queriedResult;

        let connections: Stop[][] = [];
        this.results = ['results'];

        for (const [key, value] of Object.entries(connectionList))
          if (key == 'connection') queriedResult = value;

        queriedResult.forEach(function (current: Object) {
          let route: Stop[] = [];
          for (const [key, value] of Object.entries(current)) {
            // console.log(key)
            // console.log(value)
            if (key == 'departure') {
              for (const [departureKey, departureObject] of Object.entries(value)) {

                if (departureKey == 'station') {
                  route.push(new Stop(departureObject as string, '', new Date(0), ''));
                }

                if (departureKey == 'time') {
                  let timestamp = departureObject as number * 1000;
                  route.at(-1)!.time = (new Date(timestamp));
                }

                if (departureKey == 'vehicle') {
                  route.at(-1)!.trainName = departureObject as string;
                }

                if (departureKey == 'stops') {
                  for (const [stopsKey, stopsObject] of Object.entries(departureObject as Object)) {

                    let i: number = 0;
                    if (stopsKey == "stop") {
                      stopsObject.forEach(function (currentStop: Object) {
                        let currentStopArray = Object.values(currentStop);

                        if (i != 0) {
                          let timestamp: number = Number(currentStopArray.at(6) * 1000);
                          route.push(new Stop('', '', new Date(0), ''));

                          route.at(-1)!.departureStation = route.at(-2)!.arrivalStation;
                          route.at(-1)!.arrivalStation = currentStopArray.at(1);
                          route.at(-1)!.time = new Date(timestamp);
                          route.at(-1)!.trainName = route.at(-2)!.trainName;

                          i++;
                        } else {
                          route.at(-1)!.arrivalStation = currentStopArray.at(1);
                          i++;
                        }
                      })
                    }
                  }
                }
              }
            }

            if (key == 'arrival') {
              for (const [arrivalKey, arrivalObject] of Object.entries(value)) {
                if (arrivalKey == 'station') {
                  route.push(new Stop('FINAL STATION', arrivalObject as string, new Date(0), ''));
                }

                if (arrivalKey == 'time') {
                  let timestamp = arrivalObject as number * 1000;
                  route.at(-1)!.time = (new Date(timestamp));
                }

                if (arrivalKey == 'vehicle') {
                  route.at(-1)!.trainName = arrivalObject as string;
                }
              }
            }

            if (key == 'vias') {
              let viaArray = Object.values(value);
              viaArray.forEach(function (currentVia: any) {
                if (typeof currentVia != "string") {
                  for (const [viaKey, viaObject] of Object.entries(currentVia.at(0))) {

                    if(viaKey == "arrival") {
                      for(const [viaArrivalKey, viaArrivalObject] of Object.entries(viaObject as Object)) {
                        if(viaArrivalKey == 'time') {
                          //sort by time so stops are in correct order
                          route.sort((a, b) => {
                            return a.time > b.time ? 1 : -1;
                          });

                          let timestamp = viaArrivalObject as number * 1000;
                          route.push(new Stop('', '', new Date(0), ''))

                          route.at(-1)!.time = new Date(timestamp);
                          route.at(-1)!.departureStation = route.at(-3)!.arrivalStation;
                          route.at(-1)!.trainName = route.at(-3)!.trainName;
                        }
                      }
                    }

                    if(viaKey == "departure") {
                      for(const [viaDepartureKey, viaDepartureObject] of Object.entries(viaObject as Object)) {

                        if(viaDepartureKey == "stops") {
                          for (const [stopsKey, stopsObject] of Object.entries(viaDepartureObject as Object)) {
                            let i: number = 0;
                            if (stopsKey == "stop") {
                              stopsObject.forEach(function (currentStop: Object) {
                                let currentStopArray = Object.values(currentStop);

                                if (i != 0) {
                                  let timestamp: number = Number(currentStopArray.at(6) * 1000);
                                  route.push(new Stop('', '', new Date(0), ''));

                                  route.at(-1)!.departureStation = route.at(-2)!.arrivalStation;
                                  route.at(-1)!.arrivalStation = currentStopArray.at(1);
                                  route.at(-1)!.time = new Date(timestamp);
                                  route.at(-1)!.trainName = route.at(-2)!.trainName;

                                  i++;
                                } else {
                                  route.at(-1)!.arrivalStation = currentStopArray.at(1);
                                  i++;
                                }
                              })
                            }
                          }
                        }

                        if(viaDepartureKey == 'time') {
                          let timestamp = viaDepartureObject as number * 1000;
                          route.push(new Stop('', '', new Date(0), ''))

                          route.at(-1)!.time = new Date(timestamp);
                          route.at(-1)!.departureStation = route.at(-2)!.arrivalStation;
                        }

                        if(viaDepartureKey == 'vehicle') {
                          route.at(-1)!.trainName = viaDepartureObject as string;
                        }
                      }
                    }

                    if(viaKey == "station") {
                      let setFlag: boolean = false;
                      route.forEach(function (current: Stop) {
                        if(current.arrivalStation == "") {
                          if(!setFlag) {
                            current.arrivalStation = viaObject as string;
                            setFlag = true;
                          }
                        }
                      });
                    }

                  }
                }
              });
            }
          }
          //sort by time so stops are in correct order
          route.sort((a, b) => {
            return a.time > b.time ? 1 : -1;
          });

          route.forEach(function (current: Stop, index: number) {
            if(current.departureStation == "") {
              current.departureStation = route.at(index - 1)!.arrivalStation;
            }
          });

          let index: number = route.length - 1;

          for(index; index >= 0; index--) {
            if(route.at(index)!.arrivalStation == "") {
              route.at(index)!.arrivalStation = route.at(index + 1)!.arrivalStation;
            }
          }

          console.log(route)
          connections.push(route);
        })
      },

      error => {
        this.errorResponse = true;
        if (error.status == 404) this.errorResults = "Connection not found. Try again with different parameters!";
        if (error.status == 500) this.errorResults = "Internal server error. Try again later!";
        if (error.status == 504) this.errorResults = "Connection timed out. Try again later!";
      }
    )

    this.displaySearch = false;
    this.displayResults = true;
  }

  private createQueryLink(from: string, to: string, time: string): string {
    let query: string = '';

    query = 'https://api.irail.be/connections?'
      + "from=" + from + "&"
      + "to=" + to + "&"
      + "typeOfTransport=" + "trains" + "&"
      + "time=" + time.replace(':', '') + "&"
      + "format=" + "json";

    return query;
  }

  public restoreSearch(): void {
    this.displaySearch = true;
    this.displayResults = false;
    this.submitted = false;
    this.errorResponse = false;
    this.errorResults = '';
    this.connectionInfo = '';
    this.results = ['Loading...'];
  }
}
