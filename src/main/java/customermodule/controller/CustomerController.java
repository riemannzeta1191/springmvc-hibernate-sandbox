package customermodule.controller;

import customermodule.entity.Customer;
import customermodule.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
//    removed this section after including service layer
//    // need to inject the customer dao
//    @Autowired
//    private CustomerDAO customerDAO;


    //inject customer service
    @Autowired
    private CustomerService customerService;


    //@PostMapping("/list") //LOL
    @GetMapping("/list")
    public String listCustomers(Model theModel) {

        // get customers from the dao (removed after including the service layer)
//        List<Customer> theCustomers = customerDAO.getCustomers();


//        get customers from service
        List<Customer> theCustomers = customerService.getCustomers();

        // add the customers to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        //sending empty model to get stuff for us
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer customer){

        //save the customer uising our service
        customerService.saveCustomer(customer);


        return "redirect:/customer/list";
    }

}
