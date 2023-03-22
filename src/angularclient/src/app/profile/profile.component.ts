import {Component, OnInit} from '@angular/core';
import jwtDecode from "jwt-decode";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  ngOnInit() {
  }

  public checkCookie(): string {
    let cookie: string = document.cookie;

    if(cookie == '') return "You are not logged in!";

    let decoded: string = JSON.stringify(jwtDecode(cookie))
    let date: Date = new Date(JSON.parse(decoded).iat * 1000);

    return "Currently logged in as "
      + JSON.parse(decoded).sub
      + ", you logged in at "
      + date.toLocaleTimeString("pl-PL") + ", " + date.toLocaleDateString("pl-PL") + ".";
  }

  public currentDate(): string {
    let currentDate: Date = new Date();

    return "Current date: "
      + currentDate.toLocaleTimeString("pl-PL") + ", " + currentDate.toLocaleDateString("pl-PL") + ".";
  }
}
