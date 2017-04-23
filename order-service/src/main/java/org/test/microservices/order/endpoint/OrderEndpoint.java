package org.test.microservices.order.endpoint;

import com.test.microservices.order.client.OrderResource;
import com.test.microservices.order.model.OrderDto;
import com.test.microservices.order.model.OrderReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.test.microservices.order.model.Order;
import org.test.microservices.order.model.OrderConverter;
import org.test.microservices.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rustam_Kadyrov on 25.04.2017.
 */
@RestController
public class OrderEndpoint implements OrderResource {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderConverter converter;

    @Override
    public ResponseEntity<OrderDto> findOne(@PathVariable("id") String id) {
        return ResponseEntity.ok(converter.doForward(service.findOne(id)));
    }

    @Override
    public ResponseEntity<List<OrderDto>> findAll() {

        List<OrderDto> result = new ArrayList<>();
        for (Order order : service.findAll()) {
            result.add(converter.doForward(order));
        }
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<OrderReportDto> reportCustomer(String customerName) {
        List<OrderDto> orders = new ArrayList<>();
        for (Order order : service.reportCustomer(customerName)) {
            orders.add(converter.doForward(order));
        }

        return ResponseEntity.ok(OrderReportDto.builder()
                .customerName(customerName)
                .orders(orders)
                .build());
    }
}
