package task02;

import task02.service.IDemoService;
import task02.service.impl.DemoServiceImpl;

import static task02.utils.TransactionsConstants.NUMBER_OF_ACCOUNTS;

public class Runner {
    public static void main(String[] args) {
        IDemoService demoService = new DemoServiceImpl();
        demoService.runDemo(NUMBER_OF_ACCOUNTS);
    }
}
