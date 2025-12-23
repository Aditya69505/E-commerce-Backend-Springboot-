package com.ecom.ecom.controller;

import com.ecom.ecom.dto.CartItemRequest;
import com.ecom.ecom.model.CartItem;
import com.ecom.ecom.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private  final CartService cartService;



    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-ID") String userId,
                                            @RequestBody CartItemRequest request){
        if(!cartService.addToCart(userId,request)){
            return  ResponseEntity.badRequest().body("product not available");
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/items/{productId}")
    public  ResponseEntity<Void> removeFromCart(@RequestHeader("X-User-ID") String userId,@PathVariable Long productId){
       boolean result  =  cartService.deleteItemFromCart(userId,productId);
      return result? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public  ResponseEntity<List<CartItem>> getCard(
            @RequestHeader("X-User-ID") String userId
    ){
        return  ResponseEntity.ok(cartService.getCart(userId));
    }

}
