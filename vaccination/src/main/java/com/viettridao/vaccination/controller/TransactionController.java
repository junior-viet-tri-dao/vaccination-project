package com.viettridao.vaccination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController<Model> {

    @GetMapping("/transactions-customer")
    public String showTransactionCustomer(Model model) {
        return "financeEmployee/transaction-customer";
    }

    @GetMapping("/transactions-supplier")
    public String showTransactionSupplier(Model model) {
        return "financeEmployee/transaction-supplier";
    }
}

