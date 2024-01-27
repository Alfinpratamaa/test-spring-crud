package com.alfin.crud_mongodb;

import com.alfin.crud_mongodb.Product;
import com.alfin.crud_mongodb.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;



    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable String id) {
        // Mengecek apakah ID produk ada dalam database
        Product product = productRepository.findById(id).orElse(null);
        Map<String,Object> response = new HashMap<>();

        response.put("msg", "success get data");
        response.put("data", product);
        if (product != null) {
            // Jika produk ditemukan, kirim respons OK
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            // Jika produk tidak ditemukan, kirim respons Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
     public ResponseEntity<Iterable<Product>> getAllProducts() {
        // Mengambil semua produk dalam database
        Iterable<Product> products = productRepository.findAll();

        // Mengirim respons OK dengan semua produk dalam body
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        // Mengecek apakah ID produk sudah ada dalam database
        if (productRepository.existsById(product.getId())) {
            // Jika ID sudah ada, kirim respons Conflict
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            // Jika ID belum ada, simpan produk dan kirim respons Created
            Product createdProduct = productRepository.save(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProductData) {
        // Mengecek apakah ID produk sudah ada dalam database
        Product existingProduct = productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            // Jika ID tidak ada, kirim respons Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Jika ID ada, update hanya field tertentu dan kirim respons OK
            existingProduct.setName(updatedProductData.getName());
            existingProduct.setDescription(updatedProductData.getDescription());
            existingProduct.setPrice(updatedProductData.getPrice());

            Product updatedProduct = productRepository.save(existingProduct);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        // Mengecek apakah ID produk ada dalam database
        if (!productRepository.existsById(id)) {
            // Jika ID tidak ada, kirim respons Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            // Jika ID ada, hapus produk dan kirim respons No Content
            productRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
