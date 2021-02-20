package org.rjgchw.hmall.order.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.rjgchw.hmall.order.OrderApp;
import io.cucumber.java.Before;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = OrderApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
