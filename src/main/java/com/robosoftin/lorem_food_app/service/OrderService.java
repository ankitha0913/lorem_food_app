package com.robosoftin.lorem_food_app.service;
import com.robosoftin.lorem_food_app.dao.OrderItemRepository;
import com.robosoftin.lorem_food_app.dao.OrderRepository;
import com.robosoftin.lorem_food_app.entity.Auth.UserInfo;
import com.robosoftin.lorem_food_app.entity.Cart.CartItem;
import com.robosoftin.lorem_food_app.entity.Cart.MyCart;
import com.robosoftin.lorem_food_app.entity.Order.Order;
import com.robosoftin.lorem_food_app.entity.Order.OrderItem;
import com.robosoftin.lorem_food_app.entity.Order.OrderItemKey;
import com.robosoftin.lorem_food_app.entity.Restaurant.Restaurant;
import com.robosoftin.lorem_food_app.enums.OrderStatus;
import com.robosoftin.lorem_food_app.model.OrderRequest;
import com.robosoftin.lorem_food_app.model.StatusResponse;
import com.robosoftin.lorem_food_app.utility.UniqueIdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartItemService cartItemService;
    @Transactional
    public StatusResponse placeOrder(OrderRequest orderRequest) throws Exception {
        MyCart myCart=cartService.findByCartId(orderRequest.getCartId());
        if (myCart==null)
            throw new Exception("Invalid CartId");
        UserInfo userInfo=myCart.getMyCartKey().getUserInfo();
        Restaurant restaurant=myCart.getMyCartKey().getRestaurant();
        long cartId=myCart.getCartId();
        if(userInfo.getEmailId().equals(orderRequest.getEmailId()))
        {
            double extraCharge=40.0;
            double discount=20.0;
            double totalAmount=orderRequest.getItemCost()+extraCharge-discount;
            Order order=orderRepository.save(new Order(UniqueIdGenerator.generateUniqueId(),userInfo,restaurant,
                    orderRequest.getDate(),orderRequest.getTime(),orderRequest.getAddress(),
                    orderRequest.getCookingInstruction(),orderRequest.getDeliveryType(),
                    orderRequest.getContactName(),orderRequest.getMobileNo(),
                    orderRequest.getDeliveryInstruction(),orderRequest.getPaymentMode(),
                    orderRequest.getItemCost(),extraCharge,discount,totalAmount, OrderStatus.ORDER_PLACED));
            List<CartItem> cartItems=cartItemService.findByCartId(cartId);
            for (CartItem cartItem:cartItems)
            {
                orderItemRepository.save(new OrderItem(new OrderItemKey(order,cartItem.getCartItemKey().getMenuItem()),cartItem.getQuantity()));
            }
            cartItemService.deleteByCartId(cartId);
            cartService.deleteCart(cartId);
            return new StatusResponse(HttpStatus.OK.value(), "Order placed successfully");
        }
        else
            throw new Exception("EmailId and CartId user mismatch!");
    }
}
