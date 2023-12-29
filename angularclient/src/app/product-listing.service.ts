import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductListingService {
  private apiUrl: string;

  constructor(private http: HttpClient) {
    this.apiUrl = 'http://localhost:8080/api/products';
  }
  
  public createProduct(product: Product): Observable<Product> {
    if (product.brand === "*NULL*") {
        product.brand = undefined;
    }
    if (product.type === "*NULL*") {
        product.type = undefined;
    }
    if (product.description === "*NULL*") {
        product.description = undefined;
    }
    return this.http.post<Product>(this.apiUrl, product);
  }
  
  public updateProduct(code: string, product: Product): Observable<Product> {
    if (product.brand === "*NULL*") {
        product.brand = undefined;
    }
    if (product.type === "*NULL*") {
        product.type = undefined;
    }
    if (product.description === "*NULL*") {
        product.description = undefined;
    }
    return this.http.put<Product>(this.apiUrl + "/" + code, product);
  }
  
  public getProduct(code: string): Observable<Product> {
    return this.http.get<Product>(this.apiUrl + "/" + code);
  }

  public listProducts(page: number, size: number): Observable<any> {
    return this.http.get(this.apiUrl,
      { params: new HttpParams()
          .set('page', page)
          .set('size', size) 
      }
    );
  }
  
  public deleteProduct(code: string): Observable<any> {
    return this.http.delete<Product>(this.apiUrl + "/" + code);
  }
  
  public getErrorMessage(error: any): string {
    return error.error.error.message === undefined? "Application error" : error.error.error.message;
  }
}
