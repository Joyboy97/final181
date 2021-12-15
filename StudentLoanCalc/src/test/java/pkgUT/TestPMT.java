package pkgUT;


import org.apache.poi.ss.formula.functions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import pkgLogic.Loan;

public class TestPMT {

	@Test
	public void TestPMT() {
		
		//	PMT is a standard function included in apache POI.
		//	For a given r (rate), n (number of payments), p (present value), f (future value), t (how compounding is applied)
		//	this function will determine payment
		
		//	This is an example with known values
		//	PMT returns with negative values (this is typical accounting).  
		
		double PMT;
		double r = 0.07 / 12;
		double n = 20 * 12;
		double p = 150000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));		
		double PMTExpected = 1162.95;		
		assertEquals(PMTExpected, PMT, 0.01);
	}
	@Test
	public void TestLoanWithNoExtraPayment() {
		
		//This unit test should work with the values given
		double dLoanAmount = 50000;
		double dInterestRate = 0.07;
		int iNbrOfYears = 20;
		LocalDate localDate = LocalDate.now();
		double dAdditionalPayment = 0;
		double dEscrow = 0;

		Loan loan = new Loan(dLoanAmount, dInterestRate, iNbrOfYears, localDate, dAdditionalPayment, dEscrow);

		assertTrue(loan.getLoanPayments().size() == 240);
		assertEquals(loan.getTotalPayments(), 93033.62, 0.01);
		assertEquals(loan.getTotalInterest(), 43035.87, 0.01);
	}
	
	@Test
	public void TestLoanWithExtraPayment() {
		
		double dLoanAmount1 = 50000;
		double dInterestRate1 = 0.07;
		int iNbrOfYears1 = 20;
		LocalDate localDate1 = LocalDate.now();
		double dAdditionalPayment1 = 0;
		double dEscrow1 = 0;
		
		double dLoanAmount2 = 50000;
		double dInterestRate2 = 0.07;
		int iNbrOfYears2 = 20;
		LocalDate localDate2 = LocalDate.now();
		double dAdditionalPayment2 = 200;
		double dEscrow2 = 0;

		Loan loan1 = new Loan(dLoanAmount1, dInterestRate1, iNbrOfYears1, localDate1, dAdditionalPayment1, dEscrow1);
		Loan loan2 = new Loan(dLoanAmount2, dInterestRate2, iNbrOfYears2, localDate2, dAdditionalPayment2, dEscrow2);

		assertTrue(loan2.getLoanPayments().size() == 240);
		assertEquals(loan2.getTotalPayments(), 69291.83, 0.01);
		assertEquals(loan2.getTotalInterest(), 19291.85, 0.01);
		assertEquals(loan1.getTotalPayments()-loan2.getTotalPayments(), 23744.02, 0.01);
		assertEquals(loan1.getTotalInterest()-loan2.getTotalInterest(), 23741.79, 0.01);

	}	
}

 

