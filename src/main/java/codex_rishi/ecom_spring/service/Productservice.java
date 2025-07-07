package codex_rishi.ecom_spring.service;

import codex_rishi.ecom_spring.model.Product;
import codex_rishi.ecom_spring.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class Productservice {
    @Autowired
    private ProductRepo repo;

    public List<Product> getallproducts() {
        return repo.findAll();

    }

    public Product getProductByID(int id) {
        return repo.findById(id).get();
    }


    public Product addproduct(Product product, MultipartFile imagefile) throws IOException {
        product.setImage(imagefile.getOriginalFilename());
        product.setImagetype(imagefile.getContentType());
        product.setImagedata(imagefile.getBytes());
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("❌ Price cannot be negative.");
        }

        if (product.getBrand() == null || product.getBrand().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Brand name cannot be empty.");
        }

        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("❌ Description cannot be empty.");
        }

        Date cutoff = new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime();

        if (product.getReleaseDate() == null || product.getReleaseDate().before(cutoff)) {
            throw new IllegalArgumentException("❌ Release date must be in or after 2019.");
        }

        return repo.save(product);




    }

    public Product updateproduct(int id, Product product, MultipartFile imagefile) throws IOException {
        Product existingProduct = repo.findById(id).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        // Update fields
        existingProduct.setBrand(product.getBrand());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setReleaseDate(product.getReleaseDate());
//        existingProduct.setAvailable(product.isAvailable());
        // Update image only if a new file is provided
        if (imagefile != null && !imagefile.isEmpty()) {
            existingProduct.setImage(imagefile.getOriginalFilename());
            existingProduct.setImagetype(imagefile.getContentType());
            existingProduct.setImagedata(imagefile.getBytes());
        }

        return repo.save(existingProduct);
    }





    public void deleteproduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchproduct(String keyword) {
            return repo.searchproducts(keyword);
    }

    public List<Product> newarrival() {
        LocalDate today = LocalDate.now();
        return repo.newarrival(today.getMonthValue(), today.getYear());
    }

}


