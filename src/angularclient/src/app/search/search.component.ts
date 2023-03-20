import { Component, OnInit } from '@angular/core';
import {Station} from "../models/station";
import {StationService} from "../services/station.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  stations: Station[] = [];
  stationNames: string[] = [];
  displayBelow: boolean = false;
  toTextField: string = '';
  fromTextField: string = '';

  constructor(private stationService: StationService) {}
  private getStations(): void {
    this.stationService.findAll().subscribe(res => res.map(result => this.stations.push(result)));
  }

  ngOnInit() {
    this.getStations();
  }

  private loadNames(): string[] {
    let result: string[] = [];

    this.stations.forEach(function (current) {
      result.push(current.name);
    })

    return result;
  }

  public initNames(): void {
    this.stationNames = this.loadNames();
    this.stationNames.sort();
  }

  public displaySelection(): string {
    let result: string = '';

    result = this.fromTextField + ", " + this.toTextField;

    this.displayBelow = true;
    return result;
  }
}
