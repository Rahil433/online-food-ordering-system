import React from "react";
import { Route, Routes } from "react-router-dom";
import Admin from "../Admin/Admin";
import NotFound from "../customers/pages/NotFound/NotFound";
import { useSelector } from "react-redux";
import CreateRestaurantForm from "../Admin/AddRestaurants/CreateRestaurantForm";

const AdminRouters = () => {
  const { restaurant } = useSelector((store) => store);
  
  return (
    <div>
      <Routes>
        <Route
          path="/*"
          element={
            !restaurant.usersRestaurant ? (
              <CreateRestaurantForm />
            ) : (
              <Admin />
            )
          }
        />
        <Route path="*" element={<NotFound />} />
      </Routes>
    </div>
  );
};

export default AdminRouters;
