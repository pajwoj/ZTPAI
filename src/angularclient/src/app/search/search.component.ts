import {Component, OnInit} from '@angular/core';
import {Station} from "../models/station";
import {StationService} from "../services/station.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Stop} from "../models/stop";
import {HttpClient} from "@angular/common/http";
import {firstValueFrom, last, map} from "rxjs";

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
        this.results = ['Results: <br>'];

        for (const [key, value] of Object.entries(connectionList))
          if (key == 'connection') queriedResult = value;

        queriedResult.forEach(function (current: Object) {
          let route: Stop[] = [];
          let lastStop = new Stop('', '', new Date(0), '');

          for (const [key, value] of Object.entries(current)) {
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
              }
            }

            if (key == 'arrival') {
              for (const [arrivalKey, arrivalObject] of Object.entries(value)) {
                if (arrivalKey == 'station') {
                  lastStop.departureStation = 'DESTINATION REACHED';
                  lastStop.arrivalStation = arrivalObject as string;
                }

                if (arrivalKey == 'time') {
                  let timestamp = arrivalObject as number * 1000;
                  lastStop.time = new Date(timestamp);
                }

                if (arrivalKey == 'vehicle') {
                  lastStop.trainName = arrivalObject as string;
                }
              }
            }

            if (key == 'vias') {
              let viaArray: Object[] = []
              for(const [viasKey, viasObject] of Object.entries(value)) {
                if(viasKey == "via") viaArray = viasObject as Array<Object>;
              }

              viaArray.forEach(function (currentVia) {
                for(const [viaKey, viaObject] of Object.entries(currentVia)) {
                  if(viaKey == "departure") {
                    for(const [viaDepartureKey, viaDepartureObject] of Object.entries(viaObject)) {
                      if(viaDepartureKey == "time") {
                        route.push(new Stop('', '', new Date(0), ''));

                        let timestamp = viaDepartureObject as number * 1000;
                        route.at(-1)!.time = (new Date(timestamp));
                      }

                      if(viaDepartureKey == "vehicle") {
                        route.at(-1)!.trainName = viaDepartureObject as string;
                      }
                    }
                  }

                  if(viaKey == "station") {
                    let i: number = 0;

                    for(i; i < route.length; i++) {
                      if(route.at(i)!.arrivalStation == '') {
                        route.at(i)!.arrivalStation = viaObject as string;
                        break;
                      }
                    }

                    route.at(-1)!.departureStation = route.at(-2)!.arrivalStation;
                  }
                }
              })
            }
          }
          route.push(lastStop);

          route.forEach(function (currentStop, i) {
            if(route.at(i)!.arrivalStation == "") route.at(i)!.arrivalStation = route.at(i + 1)!.arrivalStation;
          });

          //sort by time so stops are in correct order
          route.sort((a, b) => {
            return a.time > b.time ? 1 : -1;
          });

          connections.push(route);
        })

        connections.forEach((currentRoute) => {
          currentRoute.forEach((currentStop) => {
            this.results.push(currentStop.toString());
          })
          this.results.push("-----------------------------------------------------------------<br>")
        })
      },

      error => {
        this.errorResponse = true;
        this.results = [];
        if (error.status == 404) this.errorResults = "Connection not found. Try again with different parameters!";
        if (error.status == 500) this.errorResults = "Internal server error. Try again later!";
        if (error.status == 504) this.errorResults = "Connection timed out. Try again later!";
      }
    )

    this.displaySearch = false;
    this.displayResults = true;
  }

  public showResults(results: string[]): string {
    let result: string = '';

    results.forEach(function (current) {
      result += current;
    })

    return result;
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
