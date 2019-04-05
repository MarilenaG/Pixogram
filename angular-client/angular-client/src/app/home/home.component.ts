import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/components/common/menuitem';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() { }

  activeItem: MenuItem;
     
  items: MenuItem[];

  ngOnInit() {
      this.items = [
          {label: 'Account', icon: 'fa fa-fw fa-bar-chart', routerLink: ['/home/account']},
          {label: 'Upload Media', icon: 'fa fa-fw fa-calendar', routerLink: ['/home/uploadMedia']},
          {label: 'My Media', icon: 'fa fa-fw fa-book', routerLink: ['/home/myMedia']},
          {label: 'Following', icon: 'fa fa-fw fa-support', routerLink: ['/home/following']},
          {label: 'Followers', icon: 'fa fa-fw fa-support', routerLink: ['/home/follower']},
      ];
      this.activeItem = this.items[0];
  }

}
