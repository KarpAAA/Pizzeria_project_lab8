import com.example.lab_08_java.data.Client;
import com.example.lab_08_java.data.Order;
import com.example.lab_08_java.data.Paydesk;
import com.example.lab_08_java.data.generation.DefaultGenerationStrategy;
import com.example.lab_08_java.data.generation.GenerationStrategy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class TestFoTest {

    @Autowired
    private DefaultGenerationStrategy generationStrategy;

    @Test
    void testCancelOrderMethod_OrderCompleted_NoAction() {
        // Arrange
        Client client = new Client();
        client.setOrder(new Order(1, List.of(), LocalDateTime.now(), false));

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        assertFalse(client.getOrder().isCompleted());
        verify(restaurant, never()).getStat();
        verify(kafkaTemplateEvents, never()).send(anyString(), anyString());
    }

    @Test
    void testCancelOrderMethod_OrderNotCompleted_OrderRemoved() {
        // Arrange
        Client client = new Client();
        Order order = new Order(1, List.of(), LocalDateTime.now(), false);
        client.setOrder(order);
        restaurant.getCurrentOrders().add(order);

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        assertFalse(client.getOrder().isCompleted());
        assertTrue(restaurant.getCurrentOrders().isEmpty());
        verify(restaurant).getStat();
        verify(kafkaTemplateEvents, never()).send(anyString(), anyString());
    }

    @Test
    void testCancelOrderMethod_OrderNotCompleted_ClientRemoved() {
        // Arrange
        Client client = new Client();
        restaurant.getClients().add(client);

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        assertFalse(restaurant.getClients().contains(client));
        verify(restaurant).getStat();
        verify(kafkaTemplateEvents, never()).send(anyString(), anyString());
    }

    @Test
    void testCancelOrderMethod_OrderNotCompleted_PaydeskUpdated() {
        // Arrange
        Client client = new Client();
        Paydesk paydesk = new Paydesk();
        paydesk.getClients().add(client);
        restaurant.getPaydesks().add(paydesk);

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        assertFalse(paydesk.getClients().contains(client));
        verify(restaurant).getStat();
        verify(kafkaTemplateEvents, never()).send(anyString(), anyString());
    }

    @Test
    void testCancelOrderMethod_OrderNotCompleted_StatUpdated() {
        // Arrange
        Client client = new Client();
        Order order = new Order(1, List.of(), LocalDateTime.now(), false);
        client.setOrder(order);
        restaurant.getCurrentOrders().add(order);

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        verify(restaurant).getStat();
        verify(kafkaTemplateEvents, never()).send(anyString(), anyString());
    }

    @Test
    void testCancelOrderMethod_OrderNotCompleted_EventSent() {
        // Arrange
        Client client = new Client();
        Order order = new Order(1, List.of(), LocalDateTime.now(), false);
        client.setOrder(order);
        restaurant.getCurrentOrders().add(order);

        // Act
        generationStrategy.cancelOrderMethod(client);

        // Assert
        verify(kafkaTemplateEvents).send("events_failed_topic", "Order [" + order.getNumber() + "] was failed!");
    }
}