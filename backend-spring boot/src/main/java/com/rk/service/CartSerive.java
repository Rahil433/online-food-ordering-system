package com.rk.service;

import com.rk.Exception.CartException;
import com.rk.Exception.CartItemException;
import com.rk.Exception.FoodException;
import com.rk.Exception.UserException;
import com.rk.model.Cart;
import com.rk.model.CartItem;
import com.rk.model.Food;
import com.rk.model.User;
import com.rk.request.AddCartItemRequest;
import com.rk.request.UpdateCartItemRequest;

public interface CartSerive {

	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws UserException, FoodException, CartException, CartItemException;

	public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws CartItemException;

	public Cart removeItemFromCart(Long cartItemId, String jwt) throws UserException, CartException, CartItemException;

	public Long calculateCartTotals(Cart cart) throws UserException;
	
	public Cart findCartById(Long id) throws CartException;
	
	public Cart findCartByUserId(Long userId) throws CartException, UserException;
	
	public Cart clearCart(Long userId) throws CartException, UserException;
	

	

}
