import { AppConstants } from './../app-constants';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AeronaveService {
  constructor(private http: HttpClient) {}

  getAeronaveList(): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl);
  }

  deleteAeronave(id: Number): Observable<any> {
    return this.http.delete(AppConstants.baseUrl + id, {
      responseType: 'text',
    });
  }

  //Edição
  getAeronave(id: any): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl + id);
  }

  salvarAeronave(aeronave: any): Observable<any> {
    return this.http.post<any>(AppConstants.baseUrl, aeronave);
  }

  updateAeronave(aeronave: any): Observable<any> {
    return this.http.put<any>(AppConstants.baseUrl, aeronave);
  }

  getAeronaveSemanal(): Observable<{ semanal: number }> {
    return this.http.get<{ semanal: number }>(
      AppConstants.baseUrl + 'registro-semanal'
    );
  }

  getAeronaveDecada(): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl + 'decada');
  }
  getAeronavMarcaQuantidade(): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl + 'marca-quantidade');
  }

  getAeronaveNaoVendida(): Observable<{ disponiveis: number }> {
    return this.http.get<{ disponiveis: number }>(
      AppConstants.baseUrl + 'no-sellers'
    );
  }

  consultarModelo(nome:String) : Observable<any> {
    return this.http.get(AppConstants.baseUrl + "find/" + nome);
  }

  getAeronaveListPage(pagina: any): Observable<any> {
    return this.http.get<any>(AppConstants.baseUrl + 'page?page=' + pagina);
  }

}