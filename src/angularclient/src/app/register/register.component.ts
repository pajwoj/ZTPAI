import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import Validation from "../validation";
import {UserService} from "../services/user.service";
import {User} from "../models/user";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
    confirmPassword: new FormControl(''),
  });
  submitted = false;
  res: string = '';

  constructor(private formBuilder: FormBuilder, private userService: UserService) {}

  ngOnInit() {
    this.form = this.formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
        confirmPassword: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(50)]],
      }, { validators: Validation.match('password', 'confirmPassword')}
    );
  }

  onSubmit(): void {
    this.submitted = true;

    let user = new User(this.form.value.email, this.form.value.password);

    if(this.form.invalid) return;
    else this.userService.addUser(user).subscribe(response => this.res = response, error => this.res = error.error);

    setTimeout(() => {
      this.submitted = false;
    }, 3000);
  }
}
