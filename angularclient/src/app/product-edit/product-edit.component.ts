import { Component } from '@angular/core';
import { Product } from '../product';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ProductListingService } from '../product-listing.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-edit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-edit.component.html',
  styleUrl: './product-edit.component.css'
})
export class ProductEditComponent {
  code: string;
  product: Product | undefined;
  error: string | undefined;
    
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productListingService: ProductListingService) {
    this.code = this.route.snapshot.params['code']
    productListingService.getProduct(this.code).subscribe(
      data => this.product = data,
      error => {
        this.error = this.productListingService.getErrorMessage(error);
      }
    );
  }

  onSubmit() {
    if (!this.product) {
        this.error = "Application error"
        return;
    }
    this.productListingService.updateProduct(this.code, this.product).subscribe(
      () => this.router.navigate(["/"]),
      error => {
        this.error = this.productListingService.getErrorMessage(error);
      }
    );
  }
  
  deleteProduct() {
    this.productListingService.deleteProduct(this.code).subscribe(
      () => this.router.navigate(["/"]),
      error => {
        this.error = this.productListingService.getErrorMessage(error);
      }
    );
  }
}
