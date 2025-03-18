export const cartTotal = (items) => {
   return items.reduce((accumulator, item) => item.totalPrice + accumulator, 0);
 };
 