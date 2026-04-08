package com.fsb.onlinestore.web.controllers;

import com.fsb.onlinestore.web.models.requests.ProductForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.fsb.onlinestore.web.models.Product;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/products")
public class ProductController {
    private static List<Product> products = new ArrayList<Product>();
    private static Long idCount = 0L;

    static {
        products.add(new Product(++idCount, "AP - IP13", "Apple iPhone 13 Pro", 999D, 25, "iPhone-13.png"));
        products.add(new Product(++idCount, "SM - S24", "Samsung Galaxy S24 Ultra", 1199D, 15, "S24-Ultra.png"));
        products.add(new Product(++idCount, "GG - PX8", "Google Pixel 8 Pro", 899D, 30, "Pixel-8.png"));
    }

    //Hadhia el READ it lists all products  matest7a9ech read wa7da o5ra
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products", products);
        return "list";
    }

    //Use relative paths when you have class-level @RequestMapping snn t7ot URL kemla kol marra fl GetMapping
    //@GetMapping("/{id}/edit") //products/edit

    // GET - shows the empty form
    @GetMapping("/create")
    public String CreateForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "create";
    }

    // POST - receives the submitted data
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("productForm") ProductForm productForm,
                                BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "create";
        }

        Product newProduct = new Product(++idCount, productForm.getCode(), productForm.getName(),
                productForm.getPrice(), productForm.getQuantity(), productForm.getImage());

        products.add(newProduct);

        // send the list
        model.addAttribute("products", products);
        return "list";
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = findProductById(id);
        if (product == null) {
            model.addAttribute("products", products);
            return "list";
        }

        ProductForm productForm = new ProductForm();
        productForm.setCode(product.getCode());
        productForm.setName(product.getName());
        productForm.setPrice(product.getPrice());
        productForm.setQuantity(product.getQuantity());
        productForm.setImage(product.getImage());

        model.addAttribute("productForm", productForm);
        return "update";
    }

    // UPDATE - save changes
    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("productForm") ProductForm productForm,
                                BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "update";
        }

        Product product = findProductById(id);
        if (product != null) {
            product.setCode(productForm.getCode());
            product.setName(productForm.getName());
            product.setPrice(productForm.getPrice());
            product.setQuantity(productForm.getQuantity());
            product.setImage(productForm.getImage());
        }

        // Ndirectly show the list page
        model.addAttribute("products", products);
        return "list";
    }

    private Product findProductById(Long id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, Model model) {
        products.removeIf(p -> p.getId().equals(id));
        model.addAttribute("products", products);
        return "redirect:/products";
    }
}
