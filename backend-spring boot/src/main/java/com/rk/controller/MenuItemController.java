package com.rk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rk.Exception.FoodException;
import com.rk.Exception.RestaurantException;
import com.rk.Exception.UserException;
import com.rk.model.Food;
import com.rk.model.User;
import com.rk.request.CreateFoodRequest;
import com.rk.service.FoodService;
import com.rk.service.UserService;

@RestController
@RequestMapping("/api/food")
public class MenuItemController {
	@Autowired
	private FoodService menuItemService;
	
	@Autowired
	private UserService userService;


	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(
			@RequestParam String name)  {
		List<Food> menuItem = menuItemService.searchFood(name);
		return ResponseEntity.ok(menuItem);
	}
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getMenuItemByRestaurantId(
			@PathVariable Long restaurantId,
			@RequestParam boolean vegetarian,
			@RequestParam boolean seasonal,
			@RequestParam boolean nonveg,
			@RequestParam(required = false) String food_category) throws FoodException {
		List<Food> menuItems= menuItemService.getRestaurantsFood(
				restaurantId,vegetarian,nonveg,seasonal,food_category);
		return ResponseEntity.ok(menuItems);
	}
	


}
