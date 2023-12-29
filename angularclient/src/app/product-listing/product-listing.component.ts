import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductListingService } from '../product-listing.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';

@Component({
  selector: 'app-product-listing',
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  templateUrl: './product-listing.component.html',
  styleUrl: './product-listing.component.css'
})
export class ProductListingComponent implements OnInit {
  products: Product[] = [];
  currentPage: number = 1;
  pageSize: number = 10;
  totalPages: number = 0;
  error: string | undefined;
    
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productListingService: ProductListingService) {
    this.productListingService = productListingService
    if (this.route.snapshot.queryParamMap.get('page') !== null) {
      this.currentPage = parseInt(this.route.snapshot.queryParamMap.get('page')!);
    }
    if (this.route.snapshot.queryParamMap.get('size') !== null) {
      this.pageSize = parseInt(this.route.snapshot.queryParamMap.get('size')!);
    }
  }
  
  ngOnInit() {
    this.listProducts();
  }
  
  listProducts(): void {
    this.productListingService.listProducts(this.currentPage-1, this.pageSize).subscribe(
      data => {
        this.products = data.content;
        this.totalPages = data.totalPages;
      },
      error => {
        this.error = this.productListingService.getErrorMessage(error);
      }
    );
  }
  
  onPageChange(page: number): void {
    this.currentPage = page;
    this.router.navigateByUrl("/list?page=" + this.currentPage + "&size=" + this.pageSize);
    this.listProducts();
  }
}
