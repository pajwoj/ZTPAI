export class Stop {
  private _departureStation: string = '';
  private _arrivalStation: string = '';
  private _time: Date;
  private _trainName: string = '';

  constructor(departureStation: string, arrivalStation: string, departureTime: Date, trainName: string) {
    this._departureStation = departureStation;
    this._arrivalStation = arrivalStation;
    this._time = departureTime;
    this._trainName = trainName;
  }

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

  get time(): Date {
    return this._time;
  }

  set time(value: Date) {
    this._time = value;
  }

  get trainName(): string {
    return this._trainName;
  }

  set trainName(value: string) {
    this._trainName = value;
  }

  public toString(): string {
    return this._departureStation + " - " + this._arrivalStation + ", " + this._time.toLocaleTimeString("pl-PL") + ", " + this._trainName + "<br>";
  }
}
