package codex_rishi.ecom_spring.service;

import codex_rishi.ecom_spring.model.CartItem;
import codex_rishi.ecom_spring.model.Product;
import codex_rishi.ecom_spring.model.User;
import codex_rishi.ecom_spring.repository.CartItemRepository;
import codex_rishi.ecom_spring.repository.ProductRepo;

import codex_rishi.ecom_spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add product to cart
    public void addToCart(User user, Product product, int quantity) {
        // Check if the cart item already exists
        Optional<CartItem> existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem.isPresent()) {
            // Update the quantity if the item already exists
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setAddedAt(java.time.LocalDateTime.now());
            cartItemRepository.save(cartItem);
        } else {
            // Create a new cart item if it doesn't exist
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setAddedAt(java.time.LocalDateTime.now()); // Add timestamp
            cartItemRepository.save(cartItem);
        }
    }

    // ✅ Get all cart items for the logged-in user
    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findAllByUser_Id(user.getId());
    }

    // ✅ Remove item from user's cart
    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByProduct_IdAndUser_Id(productId, userId);
    }
}
