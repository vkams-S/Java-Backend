package com.vkams.personal.lasyEats.RepositoryService;

import com.vkams.personal.lasyEats.Repository.OrderRepository;
import com.vkams.personal.lasyEats.dto.Cart;
import com.vkams.personal.lasyEats.dto.Order;
import com.vkams.personal.lasyEats.model.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class OrderrepositoryServiceImpl implements OrderRepositoryService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    Provider<ModelMapper> modelMapperProvider;
    @Override
    public Order placeOrder(Cart cart) {
        ModelMapper modelMapper = modelMapperProvider.get();
        OrderEntity orderEntity = modelMapper.map(cart,OrderEntity.class);
        orderRepository.save(orderEntity);
        return modelMapper.map(orderEntity,Order.class);
    }

    @Override
    public List<Order> getOrdersByRestaurant(String restaurantId) {
        ModelMapper modelMapper = modelMapperProvider.get();
        List<Order> orderList = new ArrayList<>();
        Optional<List<OrderEntity>> optionalOrderEntities = orderRepository.findByRestaurantId(restaurantId);
        if(optionalOrderEntities.isPresent())
        {
          List<OrderEntity> orderEntityList = optionalOrderEntities.get();
          for(OrderEntity orderEntity: orderEntityList)
          {
             orderList.add(modelMapper.map(orderEntity,Order.class));
          }
        }
        return orderList;
    }

    @Override
    public Order updateStatus(String restaurantId, String orderId, String status) {
        ModelMapper modelMapper = modelMapperProvider.get();
        Order order=null;
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        if(optionalOrderEntity.isPresent())
        {
           OrderEntity orderEntity = optionalOrderEntity.get();
           orderEntity.setStatus(status);
           orderRepository.save(orderEntity);
           order = modelMapper.map(orderEntity,Order.class);
        }
        return order;
    }

    @Override
    public Order getOrderById(String orderId) {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(orderId);
        Order orderR=null;
        if(optionalOrderEntity.isPresent())
        {
            OrderEntity  order = optionalOrderEntity.get();
            orderR = modelMapper.map(order,Order.class);
        }
        return orderR;
    }
}
