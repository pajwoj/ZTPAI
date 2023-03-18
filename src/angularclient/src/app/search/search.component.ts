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

  constructor(private stationService: StationService) {}

  ngOnInit() {
    this.stationService.findAll().subscribe(data => this.stations = data);
  }
}
