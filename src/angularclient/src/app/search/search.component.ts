import {Component, OnInit} from '@angular/core';
import {Station} from "../models/station";
import {StationService} from "../services/station.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Stop} from "../models/stop";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  stations: Station[] = [];
  results: Stop[] = [];
  stationNames: string[] = [];
  connectionData: Object[] = [];
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

  private getConnections(query: string): void {
    this.http.get(query).pipe(map(data => {
      for (const [key, value] of Object.entries(data)) {
        if (key == "connection") {
          value.forEach((element: Object) => {
            this.connectionData.push(element);
          })
        }
      }
    })).subscribe(value => {
    }, (error) => {
      this.errorResponse = true;
      if (error.status == 404) this.errorResults = "Connection not found. Try again with different parameters!";
      if (error.status == 500) this.errorResults = "Internal server error. Try again later!";
      if (error.status == 504) this.errorResults = "Connection timed out. Try again later!";
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
    this.getConnections(query)

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
}
