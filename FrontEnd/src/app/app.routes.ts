/**
 * En el archivo app.routes se definen las rutas principales de navegación del frontend.
 *
 * @author hsgdevelop
 * @fechaCreacion 04/04/2026
 * @version 1.0.0
 */

import { Routes } from '@angular/router';
import { ClientsPageComponent } from './features/clients/pages/clients-page/clients-page.component';

export const routes: Routes = [
  { path: '', redirectTo: 'clients', pathMatch: 'full' },
  { path: 'clients', component: ClientsPageComponent },
  { path: '**', redirectTo: 'clients' }
];
