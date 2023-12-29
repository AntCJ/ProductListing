import { Component } from '@angular/core';
import { Product } from '../product';
import { Router, RouterModule } from '@angular/router';
import { ProductListingService } from '../product-listing.service';
import { FormsModule }   from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent {
  product: Product = new Product();
  error: string | undefined;

  constructor(
    private router: Router, 
    private productListingService: ProductListingService) {
  }

  onSubmit() {
    this.productListingService.createProduct(this.product).subscribe(
      () => this.router.navigate(["/"]),
      error => {
        this.error = this.productListingService.getErrorMessage(error);
      }
    );
  }

}
