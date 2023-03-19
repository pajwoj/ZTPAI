import {Component, Input} from '@angular/core';
import {UserService} from "./services/user.service";
import {Router} from "@angular/router";
import {User} from "./models/user";
import jwtDecode from "jwt-decode";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  @Input() res: string = '';
  constructor(private userService: UserService, private router: Router) {}

  onClick(): void {
    let jwt: string = document.cookie;
    let jwtDecoded: any = jwtDecode(jwt);
    let user: any = this.userService.getUser(jwtDecoded.sub);

    console.log(user);

    //this.userService.logout(user).subscribe(response => this.res = response, error => this.res = error.error);

    //this.router.navigate(['/login']);
  }
}
