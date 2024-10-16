package com.shoppingapp.shoppingapp.service.OrdersServiceTest;

import com.shoppingapp.shoppingapp.models.Orders;
import com.shoppingapp.shoppingapp.repository.OrderRepository;
import com.shoppingapp.shoppingapp.service.Impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplNegativeTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Orders order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample order
        order = new Orders();
        order.setOrderId(1L);
        order.setIsPaid(false);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        // Arrange
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });

        String expectedMessage = "No value present";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteOrderNotFound() {
        // Arrange
        doThrow(new RuntimeException("Order not found")).when(orderRepository).delete(order);
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.deleteOrder(order);
        });
        String expectedMessage = "Order not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    public void testAddOrderWithInvalidData() {
        // Arrange
        order.setOrderId(null);  // Invalid data (e.g., no order ID)

        when(orderRepository.save(order)).thenThrow(new RuntimeException("Invalid order data"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.addOrder(order);
        });

        String expectedMessage = "Invalid order data";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(orderRepository, times(1)).save(order);
    }
}
