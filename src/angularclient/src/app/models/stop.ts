export class Stop {
  private _departureStation: string = '';
  private _arrivalStation: string = '';
  private _departureTime: string = '';
  private _trainName: string = '';


  get departureStation(): string {
    return this._departureStation;
  }

  set departureStation(value: string) {
    this._departureStation = value;
  }

  get arrivalStation(): string {
    return this._arrivalStation;
  }

  set arrivalStation(value: string) {
    this._arrivalStation = value;
  }

  get departureTime(): string {
    return this._departureTime;
  }

  set departureTime(value: string) {
    this._departureTime = value;
  }

  get trainName(): string {
    return this._trainName;
  }

  set trainName(value: string) {
    this._trainName = value;
  }
}
