package codex_rishi.ecom_spring.service;

import codex_rishi.ecom_spring.model.CartItem;
import codex_rishi.ecom_spring.model.Product;
import codex_rishi.ecom_spring.model.Role;
import codex_rishi.ecom_spring.model.User;
import codex_rishi.ecom_spring.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    CartItemRepository cartItemRepository;
    @InjectMocks
    CartService cartService;

    Product product = new Product(
            54,
            "sh",
            new BigDecimal("554.25"),
            "dn",
            "dd",
            "ss",
            new SimpleDateFormat("yyyy").parse("2025"),
            33,
            "ss",
            "dd",
            new byte[] {1,2,3}
    );

    CartItem cartItem;
    User user=new User(1l,"gmail","Rishi","jpg", Role.ADMIN);

    CartServiceTest() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItem =new CartItem();
        cartItem.setId(15l);
        cartItem.setUser(user);
        cartItem.setAddedAt(LocalDateTime.now());
        cartItem.setQuantity(50);
        }

    @Test
    void addToCart_createsNewItem() {
        // Arrange
        when(cartItemRepository.findByUserAndProduct(user, product)).thenReturn(Optional.empty());

        // Act
        cartService.addToCart(user, product, 5);

        // Assert
        // Verify save() was called once (for new item)
        verify(cartItemRepository, times(1)).save(argThat(cartItem ->
                cartItem.getUser().equals(user) &&
                        cartItem.getProduct().equals(product) &&
                        cartItem.getQuantity() == 5
        ));
    }

    @Test
    void addToCart_updatesExistingItem() {
        // Arrange
        CartItem existing = new CartItem();
        existing.setUser(user);
        existing.setProduct(product);
        existing.setQuantity(10);

        when(cartItemRepository.findByUserAndProduct(user, product))
                .thenReturn(Optional.of(existing));

        // Act
        cartService.addToCart(user, product, 5); // Add more items

        // Assert
        // Verify save() was called with updated quantity (10 + 5 = 15)
        verify(cartItemRepository, times(1)).save(argThat(cartItem ->
                cartItem.getQuantity() == 15 &&
                        cartItem.getUser().equals(user) &&
                        cartItem.getProduct().equals(product)
        ));
    }

    @Test
    void getCartItemsByUser() {
        when(cartItemRepository.findAllByUser_Id(1l)).thenReturn(List.of(cartItem));

        // Act (call the real service method)
        List<CartItem> result = cartService.getCartItemsByUser(user);
         //Assert
        assertNotNull(cartItem);
        assertEquals(1, result.size());
        assertEquals(15L, result.get(0).getId());
        assertEquals(user.getId(), result.get(0).getUser().getId());

        verify(cartItemRepository, times(1)).findAllByUser_Id(1L);

    }

    @Test
    public void removeFromCart()
    {
        cartService.removeFromCart(15l,18l);
        verify(cartItemRepository,times(1)).deleteByProduct_IdAndUser_Id(18l,15l);

    }
}