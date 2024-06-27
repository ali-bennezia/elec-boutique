import { Injectable } from '@angular/core';
import { ProductInboundDTO } from '../data/products/dto/inbound/product-inbound-dto';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  constructor() {}

  cart: ProductInboundDTO[] = [];

  addToCart(product: ProductInboundDTO) {
    this.cart.push(product);
    this.saveCart();
  }

  removeFromCart(index: number) {
    this.cart.splice(index, 1);
    this.saveCart();
  }

  saveCart() {
    localStorage.setItem('cart', JSON.stringify(this.cart));
  }

  fetchCart() {
    let cartData = localStorage.getItem('cart');
    if (cartData != null) {
      this.cart = JSON.parse(cartData);
    }
  }

  getTotalPrice() {
    return this.cart.map((art) => art.price).reduce((a, b) => a + b);
  }
}
