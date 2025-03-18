import { api } from "../../../config/api";
import {
  createOrderFailure,
  createOrderRequest,
  createOrderSuccess,
  getUsersOrdersFailure,
  getUsersOrdersRequest,
  getUsersOrdersSuccess,
} from "./ActionCreators";
import {
  GET_USERS_NOTIFICATION_FAILURE,
  GET_USERS_NOTIFICATION_SUCCESS,
} from "./ActionTypes";

export const createOrder = (reqData) => {
  return async (dispatch) => {
    dispatch(createOrderRequest());

    const token = reqData.jwt || localStorage.getItem("jwt");
    if (!token) {
      console.error("JWT Token Missing");
      dispatch(createOrderFailure({ message: "JWT Token is missing" }));
      return;
    }

    try {
      console.log("Using JWT Token:", token);

      const { data } = await api.post("/api/order", reqData.order, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      dispatch(createOrderSuccess(data));

      const razorpayOptions = {
        key: "rzp_test_NWZKhL7eHJSGuI",
        amount: data.amount,
        currency: data.currency,
        order_id: data.orderId,
        name: "Zosh Food",
        description: "Order Payment",
        handler: function (response) {
          alert(
            "Payment successful! Payment ID: " + response.razorpay_payment_id
          );
        },
        prefill: {
          name: "Customer Name",
          email: "customer@example.com",
          contact: "9999999999",
        },
        theme: {
          color: "#f37254",
        },
      };

      const rzp = new window.Razorpay(razorpayOptions);
      rzp.open();
    } catch (error) {
      console.error(
        "Order Creation Error:",
        error.response?.data || error.message
      );
      dispatch(createOrderFailure(error));
    }
  };
};

export const getUsersOrders = () => {
  return async (dispatch) => {
    dispatch(getUsersOrdersRequest());

    const token = localStorage.getItem("jwt"); // Added token retrieval for getUsersOrders
    if (!token) {
      console.error("JWT Token Missing for Orders");
      dispatch(getUsersOrdersFailure({ message: "JWT Token is missing" }));
      return;
    }

    try {
      const { data } = await api.get(`/api/order/user`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      console.log("users order ", data);
      dispatch(getUsersOrdersSuccess(data));
    } catch (error) {
      console.error("Get Orders Error:", error.response?.data || error.message);
      dispatch(getUsersOrdersFailure(error));
    }
  };
};

export const getUsersNotificationAction = () => {
  return async (dispatch) => {
    dispatch(createOrderRequest());

    const token = localStorage.getItem("jwt"); // Added token for notifications too
    if (!token) {
      console.error("JWT Token Missing for Notifications");
      dispatch({
        type: GET_USERS_NOTIFICATION_FAILURE,
        payload: { message: "JWT Token is missing" },
      });
      return;
    }

    try {
      const { data } = await api.get("/api/notifications", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      console.log("all notifications ", data);
      dispatch({ type: GET_USERS_NOTIFICATION_SUCCESS, payload: data });
    } catch (error) {
      console.error(
        "Notification Error:",
        error.response?.data || error.message
      );
      dispatch({ type: GET_USERS_NOTIFICATION_FAILURE, payload: error });
    }
  };
};
