package com.rk.service;

import java.util.List;

import com.rk.model.CustomOrder;
import com.rk.model.Notification;
import com.rk.model.Restaurant;
import com.rk.model.User;

public interface NotificationService {
	
	public Notification sendOrderStatusNotification(CustomOrder order);
	public void sendRestaurantNotification(Restaurant restaurant, String message);
	public void sendPromotionalNotification(User user, String message);
	
	public List<Notification> findUsersNotification(Long userId);

}
