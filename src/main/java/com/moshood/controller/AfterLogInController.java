package com.moshood.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.moshood.config.BalanceWebSocketHandler;
import com.moshood.dto.Payment;
import com.moshood.exception.CantSendToYourSelfException;
import com.moshood.exception.InsufficientAmountException;
import com.moshood.exception.UserNotFoundException;
import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;
import com.moshood.repository.UserRepository;
import com.moshood.services.AccountService;
import com.moshood.services.BalanceService;
import com.moshood.services.ReceiptService;
import com.moshood.services.SessionService;
import com.moshood.services.TransferService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
public class AfterLogInController {
	
	private final LogInComponent logInComponent;
	private final TransferService transferService;
	private final UserRepository userRepository;
	private final AccountRepository accountRepository;
	private final String location = "C:\\Users\\sOPHIAT\\Documents\\itext\\pdf\\receipt.pdf";
	private Logger logger = Logger.getLogger("text the logIn comonent");
	private final ReceiptService receiptService;
	
	private final BalanceService balanceService;
	public AfterLogInController(LogInComponent logInComponent, 
			TransferService transferService, UserRepository userRepository,
			AccountRepository accountRepository, ReceiptService receiptService, BalanceService balanceService) {
		this.logInComponent = logInComponent;
		this.transferService = transferService;
		this.userRepository = userRepository;
		this.accountRepository = accountRepository;
		this.receiptService = receiptService;
		this.balanceService = balanceService;
		
	}
	@GetMapping("/mainPage")
	public String getMainPage(HttpSession session) {
		try {
			BigDecimal balance = accountRepository.findByUsername(logInComponent.getUserName()).getBalance();
			logger.info("Your balance is " + balance);
			session.setAttribute("balance", balance);
////			logger.info(" The user is " + logInComponent.getUserName());
////			logger.info(" The user balance is " + balance); 
//			session.setAttribute("balance", balance);
				
				return "mainPage";
			}
		catch(Exception e) {
			return "redirect:login";
		}
		
		
		
		
	}
 
	@GetMapping("/transfer")
	public String getTranferPage() {
		return "transfer";
	}
	
	// This method runs whent e submit button is pressed
	@PostMapping("/transfer")
	public String getSuccessfulTransferPage(HttpSession session, @RequestParam String receiverId, 
			@RequestParam BigDecimal amount) {
			long senderId =   (Long) session.getAttribute("id");
			
			logger.info("the real id is " + senderId );
			long receiversId = Long.parseLong(receiverId);
			
			try {
				Payment payment = new Payment();
				String receiverName = accountRepository.findById(receiversId)
										.get().getUsername();
				session.setAttribute("receiverName", receiverName);
				
				String senderName = accountRepository.findById(senderId).get().getUsername();
				
				session.setAttribute("senderName", senderName);
				payment.setAmount(amount);
				payment.setReceiverName(receiverName);
				payment.setSenderName(senderName);
				// add the payment object to the session
				session.setAttribute("payment", payment);
				BigDecimal receiverBalance = accountRepository.findById(receiversId).get().getBalance();
				BigDecimal senderBalance = accountRepository.findById(senderId).get().getBalance();
				transferService.transferMoney(senderId,receiversId , amount);
				balanceService.updateBalance(receiverName.toLowerCase(), receiverBalance.add(amount), senderName.toLowerCase(), senderBalance.subtract(amount));
				
				
			} catch(InsufficientAmountException e) {
				session.setAttribute("message", "Sorry, you don't have enough funds in your wallet");
 				return "handleError";	
			} catch (UserNotFoundException e) {
				session.setAttribute("message", "sorry, user with id" + receiversId + "not found");
				return "handleError";
			} catch (CantSendToYourSelfException e) {
				session.setAttribute("message" , "Hi, you can't send money to your account");
				return "handleError";
				}
			catch(Exception e) {
				session.setAttribute("message", "Something went wrong, kindly try again");
				e.printStackTrace();
				return "handleError";
			}
			
			return "SuccessfulTransfer";
			
		
	}
	
	// get the receipt
	@GetMapping("/receipt")
	public String getReceipt(HttpSession session, HttpServletResponse response) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		
		response.setHeader("Content-Disposition", "attachment;filename=receipt.pdf");
		
		OutputStream stream = response.getOutputStream();
		String receiverName = session.getAttribute("receiverName").toString();
		String senderName = session.getAttribute("senderName").toString();
		
		receiptService.generateReceipt( stream, receiverName, senderName);
		return "receipt";
	}
}
