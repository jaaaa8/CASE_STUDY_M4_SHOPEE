package com.example.case_study_mdl_4_shopee.controller.customer;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final IProductService productService;
    private final IShopService shopService;
    private final ICartService cartService;
    private final ICustomerOrderService orderService;
    private final IReviewService reviewService;
    private final IUserManagementService userService;

    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/customer/home";
    }

    @GetMapping("/search")
    public String search(@RequestParam String keyword, Model model) {
        List<Product> products = productService.searchProductsByName(keyword);
        List<Account> shops = shopService.searchShopsByName(keyword);
        model.addAttribute("products", products);
        model.addAttribute("shops", shops);
        model.addAttribute("keyword", keyword);
        return "user/customer/home"; // Hoặc một trang kết quả tìm kiếm riêng
    }

    @GetMapping("/shop/{id}")
    public String viewShop(@PathVariable Long id, Model model) {
        Account shop = shopService.getShopById(id);
        model.addAttribute("shop", shop);
        model.addAttribute("products", productService.getProductsBySeller(id));
        return "user/customer/home"; // Hoặc trang chi tiết shop
    }


    @GetMapping("/product/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "user/customer/product/detail";
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        Account currentAccount = userService.getCurrentAccount();
        model.addAttribute("cart", cartService.getCart(currentAccount.getAccountId()));
        return "user/customer/cart/cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, @RequestParam(required = false) String action) {
        Account currentAccount = userService.getCurrentAccount();
        if ("buyNow".equals(action)) {
            return "redirect:/payment?productId=" + productId + "&quantity=" + quantity;
        }
        cartService.addToCart(currentAccount.getAccountId(), productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateQuantity(@RequestParam Long orderItemId, @RequestParam int quantity) {
        cartService.updateQuantity(orderItemId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long orderItemId) {
        cartService.removeFromCart(orderItemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/toggle-select")
    public String toggleSelect(@RequestParam Long orderItemId) {
        cartService.toggleSelect(orderItemId);
        return "redirect:/cart";
    }

    @GetMapping("/payment")
    public String payment(@RequestParam(required = false) Long productId, @RequestParam(required = false) Integer quantity, Model model) {
        Account currentAccount = userService.getCurrentAccount();
        Long customerId = currentAccount.getAccountId();
        if (productId != null && quantity != null) {
            Product product = productService.getProductById(productId);
            Account customer = currentAccount;
            
            // Giả lập một đối tượng Orders cho trang payment.html
            OrderItems item = OrderItems.builder()
                    .product(product)
                    .quantity(quantity)
                    .price((long) product.getPrice().intValue())
                    .build();
            
            SubOrders subOrder = SubOrders.builder()
                    .sellerOrder(product.getSeller())
                    .total((long) product.getPrice() * quantity)
                    .build();
            subOrder.setOrderItems(new java.util.ArrayList<>(List.of(item)));
            
            Orders tempOrder = Orders.builder()
                    .customerOrder(customer)
                    .total((long) (product.getPrice().intValue() * quantity))
                    .build();
            tempOrder.setSubOrders(new java.util.ArrayList<>(List.of(subOrder)));
            subOrder.setOrder(tempOrder);
            
            model.addAttribute("cart", tempOrder);
            model.addAttribute("productId", productId);
            model.addAttribute("quantity", quantity);
        } else {
            model.addAttribute("cart", cartService.getCart(customerId));
        }
        return "user/customer/payment/payment";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String paymentMethod, 
                           @RequestParam(required = false) Long productId, 
                           @RequestParam(required = false) Integer quantity,
                           Model model) {
        Account currentAccount = userService.getCurrentAccount();
        try {
            Long customerId = currentAccount.getAccountId();
            Orders order = orderService.checkout(customerId, paymentMethod, productId, quantity);
            return "redirect:/success/success/" + order.getOrdersId();
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return payment(productId, quantity, model); // Quay lại trang thanh toán với thông báo lỗi
        }
    }

    @GetMapping("/success/success/{orderId}")
    public String checkoutSuccess(@PathVariable Long orderId, Model model) {
        Orders order = orderService.findById(orderId);
        model.addAttribute("orderCode", order != null ? order.getOrderCode() : "#" + orderId);
        return "user/customer/success/success";
    }

    @PostMapping("/wallet/deposit")
    public String deposit(@RequestParam Long amount) {
        Account currentAccount = userService.getCurrentAccount();
        userService.deposit(currentAccount.getAccountId(), amount);
        return "redirect:/profile";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/profile";
    }

    @PostMapping("/product/review")
    public String addReview(@RequestParam Long productId, @RequestParam String comments, @RequestParam int rating) {
        Account currentAccount = userService.getCurrentAccount();
        reviewService.addReview(currentAccount.getAccountId(), productId, comments, rating, null);
        return "redirect:/product/detail/" + productId;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Account currentAccount = userService.getCurrentAccount();
        model.addAttribute("orders", orderService.viewOrderHistory(currentAccount.getAccountId()));
        model.addAttribute("account", currentAccount);
        return "user/profile";
    }

    @GetMapping("/admin")
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("/seller")
    public String sellerHome() {
        return "user/seller/home";
    }

    @GetMapping("/seller/product/create")
    public String sellerProductCreate() {
        return "user/seller/order/create";
    }

    @GetMapping("/seller/product/update")
    public String sellerProductUpdate() {
        return "user/seller/order/update";
    }
}
