package com.shoppingapp.shoppingapp.service.OrdersServiceTest;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.service.Impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Orders order;

    @BeforeEach
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a sample order
        order = new Orders();
        order.setOrderId(1L);
        order.setIsPaid(true);
    }

    @Test
    public void testAddOrder() {
        // Arrange
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Orders savedOrder = orderService.addOrder(order);

        // Assert
        assertNotNull(savedOrder);
        assertEquals(order.getOrderId(), savedOrder.getOrderId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testUpdateOrder() {
        // Arrange
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Orders updatedOrder = orderService.updateOrder(order);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(order.getOrderId(), updatedOrder.getOrderId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetAllOrders() {
        // Arrange
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(order);
        when(orderRepository.findAll()).thenReturn(ordersList);

        // Act
        List<Orders> allOrders = orderService.getAllOrders();

        // Assert
        assertNotNull(allOrders);
        assertEquals(1, allOrders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void testGetOrderById() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Act
        Orders foundOrder = orderService.getOrderById(1L);

        // Assert
        assertNotNull(foundOrder);
        assertEquals(order.getOrderId(), foundOrder.getOrderId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteOrder() {
        // Act
        orderService.deleteOrder(order);
        // Assert
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    public void testDeleteOrders() {
        // Arrange
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(order);

        // Act
        orderService.deleteOrders(ordersList);

        // Assert
        verify(orderRepository, times(1)).deleteAll(ordersList);
    }

    @Test
    public void testGetTotalOrders() {
        // Arrange
        when(orderRepository.count()).thenReturn(5L);
        // Act
        int totalOrders = orderService.getTotalOrders();
        // Assert
        assertEquals(5, totalOrders);
        verify(orderRepository, times(1)).count();
    }

}
