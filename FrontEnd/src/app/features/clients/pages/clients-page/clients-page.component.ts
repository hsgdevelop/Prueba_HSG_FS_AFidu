/**
 * En la clase ClientsPageComponent se administra la vista principal de clientes, incluyendo listado, búsqueda y acciones principales.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ClientApiService } from '../../../../core/services/client-api.service';
import { LoggerService } from '../../../../core/services/logger.service';
import { AdvancedSearchParams, Client, CreateClientRequest } from '../../../../core/models/client.model';
import { SidebarComponent } from '../../components/sidebar/sidebar.component';
import { ClientFormModalComponent } from '../../components/client-form-modal/client-form-modal.component';
import { AdvancedSearchModalComponent } from '../../components/advanced-search-modal/advanced-search-modal.component';

@Component({
  selector: 'app-clients-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    SidebarComponent,
    ClientFormModalComponent,
    AdvancedSearchModalComponent
  ],
  templateUrl: './clients-page.component.html',
  styleUrl: './clients-page.component.css'
})
export class ClientsPageComponent implements OnInit {
  clients: Client[] = [];
  sharedKeySearch = '';
  activeAdvancedFilters: AdvancedSearchParams = {};
  loading = false;
  errorMessage = '';
  showCreateModal = false;
  showAdvancedModal = false;

  constructor(
    private readonly clientApiService: ClientApiService,
    private readonly logger: LoggerService
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  loadClients(sharedKey?: string): void {
    this.loading = true;
    this.errorMessage = '';

    this.clientApiService.getClients(sharedKey).subscribe({
      next: (clients) => {
        this.clients = clients;
        this.loading = false;
        this.activeAdvancedFilters = {};
        this.logger.info('Clients loaded', clients.length);
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Could not load clients.';
      }
    });
  }

  searchBySharedKey(): void {
    this.logger.info('Basic search triggered', this.sharedKeySearch);
    this.loadClients(this.sharedKeySearch);
  }

  clearSearch(): void {
    this.sharedKeySearch = '';
    this.activeAdvancedFilters = {};
    this.loadClients();
  }

  createClient(payload: CreateClientRequest): void {
    this.clientApiService.createClient(payload).subscribe({
      next: () => {
        this.showCreateModal = false;
        this.sharedKeySearch = '';
        this.loadClients();
      },
      error: (error) => {
        this.errorMessage = error?.error?.details?.join(' | ') || 'Could not create client.';
      }
    });
  }

  applyAdvancedSearch(filters: AdvancedSearchParams): void {
    this.loading = true;
    this.errorMessage = '';
    this.sharedKeySearch = '';
    this.activeAdvancedFilters = filters;

    this.clientApiService.advancedSearch(filters).subscribe({
      next: (clients) => {
        this.clients = clients;
        this.loading = false;
        this.showAdvancedModal = false;
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = error?.error?.details?.join(' | ') || 'Could not perform advanced search.';
      }
    });
  }

  exportCsv(): void {
    this.clientApiService.exportCsv(this.sharedKeySearch, this.activeAdvancedFilters).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'clients.csv';
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error: () => {
        this.errorMessage = 'Could not export CSV.';
      }
    });
  }

  trackBySharedKey(_: number, client: Client): string {
    return client.sharedKey;
  }
}
