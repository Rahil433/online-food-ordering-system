import { Button, Chip, Divider, IconButton } from "@mui/material";
import React from "react";
import AddCircleOutlineIcon from "@mui/icons-material/AddCircleOutline";
import RemoveCircleOutlineIcon from "@mui/icons-material/RemoveCircleOutline";
import { useDispatch, useSelector } from "react-redux";
import {
  addItemToCart,
  removeCartItem,
  updateCartItem,
} from "../../../State/Customers/Cart/cart.action";

const CartItemCard = ({ item }) => {
  const dispatch = useDispatch();
  const jwt = localStorage.getItem("jwt");

  // Correctly selecting specific parts of the state
  const auth = useSelector((state) => state.auth);
  const cart = useSelector((state) => state.cart);

  const handleUpdateCartItem = (value) => {
    const newQuantity = item.quantity + value;

    console.log(`🟠 Current Quantity: ${item.quantity}, New Quantity: ${newQuantity}`);

    if (newQuantity < 1) {
      console.log("🛑 Removing item from cart");
      handleRemoveCartItem();  // Correctly trigger removal when reaching zero
      return;
    }

    console.log("✅ Updating item in cart");
    const data = { cartItemId: item.id, quantity: newQuantity };
    dispatch(updateCartItem({ data, jwt: auth.jwt || jwt }));
  };

  const handleRemoveCartItem = () => {
    dispatch(removeCartItem({ cartItemId: item.id, jwt: auth.jwt || jwt }));
  };

  return (
    <div className="px-5">
      <div className="lg:flex items-center lg:space-x-5">
        <div>
          <img
            className="w-[5rem] h-[5rem] object-cover"
            src={item.food.images[0]}
            alt=""
          />
        </div>

        <div className="flex items-center justify-between lg:w-[70%]">
          <div className="space-y-1 lg:space-y-3 w-full">
            <p className="">{item.food.name}</p>
            <div className="flex justify-between items-center">
              <div className="flex items-center space-x-1">
                <IconButton onClick={() => handleUpdateCartItem(-1)} color="primary">
                  <RemoveCircleOutlineIcon />
                </IconButton>
                <div className="w-5 h-5 text-xs flex items-center justify-center">
                  {item.quantity}
                </div>
                <IconButton onClick={() => handleUpdateCartItem(1)} color="primary">
                  <AddCircleOutlineIcon />
                </IconButton>
              </div>
            </div>
          </div>

          <p>₹{item.totalPrice}</p>
        </div>
      </div>
      <Divider sx={{ my: 1 }} />
    </div>
  );
};

export default React.memo(CartItemCard);
