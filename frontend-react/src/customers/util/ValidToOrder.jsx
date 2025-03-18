export function isValid(cartItems) {
  // Check if cartItems is empty first
  if (!cartItems || cartItems.length === 0) {
    return false;  // Or true depending on your logic, but usually, an empty cart is invalid
  }

  // Safely access restaurantId from the first item
  const restaurantId = cartItems[0]?.food?.restaurant?.id;
  
  if (!restaurantId) {
    return false; // If the restaurantId is undefined, it's invalid
  }

  // Check if all items belong to the same restaurant
  for (let item of cartItems) {
    console.log("item ---- ", item.food?.restaurant?.id);
    
    if (item.food?.restaurant?.id !== restaurantId) {
      return false; // If any item doesn't match, return false
    }
  }
  
  return true; // All items belong to the same restaurant
}
