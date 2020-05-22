package com.charter.example;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.charter.example.customer.Customer;
import com.charter.example.customer.CustomerPredicate;
import com.charter.example.customer.CustomerRepository;
import com.charter.example.util.DateUtility;


/** 
 *  Author: Ramamoorthy Natarajan
 *  Date:2020-05-21
 * */

@SpringBootApplication
public class SpringBootCharterDemoApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CustomerRepository repository;
	
	@Value("${customer.hundred.dollar.threshold}")
	private int customerHundredDollarThreshold;
	
	@Value("${customer.fifty.dollar.threshold}")
	private int customerFiftyDollarThreshold;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCharterDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Customer> customers = loadCustomerSalesData();

		// return if no customers present
		if (customers.size() == 0) return;
		
		// predicate usage demo
        Predicate<Customer> isAmountGT100 = CustomerPredicate.isAmountGT100(customerHundredDollarThreshold);
        Predicate<Customer> isAmountGT50 = CustomerPredicate.isAmountGT50(customerFiftyDollarThreshold);
        
		// calculate per transaction point
		customers.stream().forEach(c -> {
			if (isAmountGT100.test(c)) {
				c.setRewardsPoint((c.getAmount() - customerHundredDollarThreshold) * 2);
			}
			if (isAmountGT50.test(c)) {
				c.setRewardsPoint(c.getRewardsPoint() +  (c.getAmount() - customerFiftyDollarThreshold) * 1);
			}
		});
		
		// sort by name before printing
		customers.sort(Comparator.comparing(Customer::getName));
		
		// print the report
		printReportHeading();
		int totalCustomerRewardsPoints = 0;
		int grandTotalRewardsPoints = 0;
		
		Customer savedCustomer = customers.get(0);
		for(Customer c : customers) {
			if (!savedCustomer.getName().equals(c.getName())) {
				printCustomerTotal(savedCustomer.getName(), totalCustomerRewardsPoints);
				
				// change customer? init break values
				savedCustomer = c;
				totalCustomerRewardsPoints = 0;
			}
			printCustomerDetail(c);
			
			// accumulate totals
			totalCustomerRewardsPoints += c.getRewardsPoint();
			grandTotalRewardsPoints += c.getRewardsPoint();
		}
		
		printCustomerTotal(savedCustomer.getName(), totalCustomerRewardsPoints);
		printGrandTotal(grandTotalRewardsPoints);

	}
		  
	// this method uses static method to format month
	private void printCustomerDetail(Customer c) {
		
		String s = String.format("%-25s %-10s %-4s %4d %-15s %4d",c.getName(), DateUtility.getMonthName((c.getMonth())), " ", c.getAmount(), " ", c.getRewardsPoint());
		System.out.println(s);
	}
	
	private void printCustomerTotal(String customerName, int totalCustomerRewardsPoints) {
		System.out.println("--------------------------------------------------------------------");
		String s = String.format("%-62s %4d","Customer Total("+customerName+")", totalCustomerRewardsPoints);
		System.out.println(s);		
		System.out.println("--------------------------------------------------------------------");
	}
	
	private void printGrandTotal(int grandTotal) {
		String s = String.format("%-63s %d", "Grand Total", grandTotal);
		System.out.println(s);	
		System.out.println("--------------------------------------------------------------------");
	}
	
	private void printReportHeading() {
		//System.out.println("1234567890123456789012345678901234567890123456789012345678901234567890");
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Customer Name             Month         Amount            RewardsPoint");
		System.out.println("----------------------------------------------------------------------");
		
	}

	// Spring Data JPA call demo
	private List<Customer> loadCustomerSalesData() {

		logger.info("Inserting -> {}", repository.save(new Customer("John", 1, 120)));
		logger.info("Inserting -> {}", repository.save(new Customer("John", 2, 75)));
		logger.info("Inserting -> {}", repository.save(new Customer("John", 3, 50)));

		logger.info("Inserting -> {}", repository.save(new Customer("Derek", 1, 50)));
		logger.info("Inserting -> {}", repository.save(new Customer("Derek", 2, 65)));
		logger.info("Inserting -> {}", repository.save(new Customer("Derek", 3, 100)));

		return repository.findAll();
	}
	

}
