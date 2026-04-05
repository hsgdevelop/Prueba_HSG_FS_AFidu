
/**
 * En la clase ClientApiService se gestionan las llamadas HTTP hacia el backend para la administración de clientes.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdvancedSearchParams, Client, CreateClientRequest } from '../models/client.model';

@Injectable({ providedIn: 'root' })
export class ClientApiService {
  private readonly baseUrl = 'http://localhost:8080/api/clients';

  constructor(private readonly http: HttpClient) {}

  getClients(sharedKey?: string): Observable<Client[]> {
    let params = new HttpParams();
    if (sharedKey?.trim()) {
      params = params.set('sharedKey', sharedKey.trim());
    }
    return this.http.get<Client[]>(this.baseUrl, { params });
  }

  createClient(payload: CreateClientRequest): Observable<Client> {
    return this.http.post<Client>(this.baseUrl, payload);
  }

  advancedSearch(filters: AdvancedSearchParams): Observable<Client[]> {
    let params = new HttpParams();
    Object.entries(filters).forEach(([key, value]) => {
      if (value) {
        params = params.set(key, value);
      }
    });
    return this.http.get<Client[]>(`${this.baseUrl}/advanced-search`, { params });
  }

  exportCsv(sharedKey?: string, filters?: AdvancedSearchParams): Observable<Blob> {
    let params = new HttpParams();

    if (sharedKey?.trim()) {
      params = params.set('sharedKey', sharedKey.trim());
    }

    Object.entries(filters ?? {}).forEach(([key, value]) => {
      if (value) {
        params = params.set(key, value);
      }
    });

    return this.http.get(`${this.baseUrl}/export`, {
      params,
      responseType: 'blob'
    });
  }
}
