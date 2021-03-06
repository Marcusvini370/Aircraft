import { HttpInterceptorModule } from './service/handler-interceptor.service';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';
import { AeronaveComponent } from './componentes/aeronave/aeronave.component';
import { RouterModule, Routes } from '@angular/router';
import { ListAeronavesComponent } from './componentes/list-aeronaves/list-aeronaves.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MensagemValidacaoComponent } from './componentes/mensagem-valida/mensagem-validacao.component';

export const appRouters: Routes = [
  { path: 'aeronave', component: AeronaveComponent },
  { path: 'aeronave/:id', component: AeronaveComponent },
  { path: '', component: ListAeronavesComponent },
  { path: '**', redirectTo: '' },
];

export const routes: ModuleWithProviders<any> =
  RouterModule.forRoot(appRouters);

@NgModule({
  declarations: [AppComponent, AeronaveComponent, ListAeronavesComponent, MensagemValidacaoComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
    routes,
    HttpInterceptorModule,
    NgxPaginationModule,
    ReactiveFormsModule

  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
