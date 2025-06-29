package uk.co.prodapt.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.prodapt.inventory.model.Product;
import uk.co.prodapt.inventory.model.Supplier;

@Service
public class ProductService {

    private final ConcurrentHashMap<Integer, Product> products = new ConcurrentHashMap<>();
    private final SupplierService supplierService;

    @Autowired
    public ProductService(SupplierService supplierService) {
        this.supplierService = supplierService;

        // Initialize some sample products
        products.put(1, new Product(1, "Product A", true, 1, null));
        products.put(2, new Product(2, "Product B", false, 2, null));
        products.put(3, new Product(3, "Product C", true, 3, null));
    }

    public List<Product> getAll() {
        return enrichWithSupplierInfo(products.values().stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    public List<Product> getAvailableStockProducts() {
        return enrichWithSupplierInfo(products.values().stream()
                .filter(Product::isAvailable)
                .distinct()
                .collect(Collectors.toList()));
    }

    public Optional<Product> getById(Integer id) {
        return Optional.ofNullable(products.get(id))
                .map(this::enrichWithSupplierInfo);
    }

    public Product create(Product product) {
        products.computeIfAbsent(product.getId(), key -> product);
        return enrichWithSupplierInfo(product);
    }

    public Product update(Integer id, Product updatedProduct) {
        return products.compute(id, (key, existingProduct) -> {
            if (existingProduct == null) {
                throw new RuntimeException("Product not found");
            }
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setAvailable(updatedProduct.isAvailable());
            existingProduct.setSupplierId(updatedProduct.getSupplierId());
            return enrichWithSupplierInfo(existingProduct);
        });
    }

    public void delete(Integer id) {
        products.remove(id);
    }

    private List<Product> enrichWithSupplierInfo(List<Product> products) {
        for (Product product : products) {
            enrichWithSupplierInfo(product);
        }
        return products;
    }

    private Product enrichWithSupplierInfo(Product product) {
        if (product.getSupplierId() != null) {
            try {
                Supplier supplier = supplierService.getSupplierById(product.getSupplierId());
                product.setSupplierName(supplier.getName());
            } catch (RuntimeException e) {
                product.setSupplierName("Unknown Supplier");
            }
        }
        return product;
    }
}