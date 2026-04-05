import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  readonly menuItems = [
    { label: 'Clients', active: true },
    { label: 'Client look history', active: false },
    { label: 'Emergency PIN configuration', active: false },
    { label: 'Emergency PIN history', active: false }
  ];
}
