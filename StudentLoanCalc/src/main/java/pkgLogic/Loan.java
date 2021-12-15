package pkgLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private int LoanPaymentCnt;
	private boolean bCompoundingOption;
	private LocalDate StartDate;
	private double AdditionalPayment;
	private double Escrow;

	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();

	public Loan(double loanAmount, double interestRate, int loanPaymentCnt, LocalDate startDate,
			double additionalPayment, double escrow) {
		super();
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		LoanPaymentCnt = loanPaymentCnt * 12;
		StartDate = startDate;
		AdditionalPayment = additionalPayment;
		bCompoundingOption = false;
		LoanBalanceEnd = 0;
		this.Escrow = escrow;

		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		
		//TODO: Create a payment until 'remaining balance' is < PMT + Additional Payment
		//		Hint: use while loop

		//TODO: Create final payment (last payment might be partial payment)
		while (RemainingBalance >GetPMT()+additionalPayment) {	
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
			RemainingBalance = payment.getEndingBalance();
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
		}
		if(RemainingBalance>GetPMT()) {
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, true);
			payment.setPayment(RemainingBalance);
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
			RemainingBalance*=InterestRate/12;
			}
		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, true);
		
		startDate = startDate.plusMonths(1);
		loanPayments.add(payment);
		

	}

	public double GetPMT() {
		double PMT = 0;
		//TODO: Calculate PMT (use 
		PMT=FinanceLib.pmt(InterestRate/12, LoanPaymentCnt, LoanAmount, LoanBalanceEnd, bCompoundingOption);
		return Math.abs(PMT);
	}

	public double getTotalPayments() {
		double tot = 0;
		//TODO: Calculate total payments
		for(Payment p:loanPayments) {
			tot+=p.getPayment();
		}
		return tot;
	}

	public double getTotalInterest() {

		double interest = 0;
		//TODO: Calculate total Interest
		for(Payment p:loanPayments) {
			interest+=p.getInterestPayment();
		}
		return interest;

	}

	public double getTotalEscrow() {

		double escrow = 0;
		//TODO: Calculate total escrow
		escrow +=ths.Escrow;
		return escrow;

	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public void setLoanBalanceEnd(double loanBalanceEnd) {
		LoanBalanceEnd = loanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	public int getLoanPaymentCnt() {
		return LoanPaymentCnt;
	}

	public void setLoanPaymentCnt(int loanPaymentCnt) {
		LoanPaymentCnt = loanPaymentCnt;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}

	public void setbCompoundingOption(boolean bCompoundingOption) {
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public void setAdditionalPayment(double additionalPayment) {
		AdditionalPayment = additionalPayment;
	}

	public ArrayList<Payment> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(ArrayList<Payment> loanPayments) {
		this.loanPayments = loanPayments;
	}

	public double getEscrow() {
		return Escrow;
	}

}