package com.moshood.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.moshood.observer.LogInComponent;
import com.moshood.repository.AccountRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/sse")
public class SseController {

    private final AccountRepository accountRepository;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final LogInComponent logInComponent;
    public SseController(AccountRepository accountRepository, LogInComponent logInComponent) {
        this.accountRepository = accountRepository;
        this.logInComponent  = logInComponent;  
        }

    @GetMapping(value = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamBalanceUpdates(@RequestParam Long userId) {
        SseEmitter emitter = new SseEmitter();

        executor.execute(() -> {
            try {
                while (true) {
                	BigDecimal balance = accountRepository.findByUsername(logInComponent.getUserName()).getBalance();; // Fetch balance from DB
                    if (balance != null) {
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(balance) // Send balance amount
                                .id(String.valueOf(balance)) // Use timestamp as ID
                                .name("balance-update"); // Event name
                        emitter.send(event);
                    }
                    Thread.sleep(5000); // Poll every 5 seconds
                }
            } catch (IOException | InterruptedException e) {
                emitter.completeWithError(e); // Handle errors
            }
        });

        return emitter;
    }
}