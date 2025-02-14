## Refleksi 1
---
## > Clean code practices yang diimplementasikan:
### 1.) Struktur package yang mengikuti konvensi Spring Boot
- Menggunakan struktur controller, service, repository, dan model membantu dalam memisahkan tanggung jawab (separation of concerns).
- Memudahkan pengelolaan dan perawatan kode dalam jangka panjang.

### 2.) Pembagian yang jelas antara controller, service, repository, dan model
- Controller hanya menangani request dan response, sementara logika bisnis ditempatkan di service.
- Database query dikelola oleh repository, sedangkan model hanya digunakan untuk merepresentasikan data.

### 3.) Setiap method hanya memiliki satu fungsi, sama halnya dengan tiap class
- Prinsip Single Responsibility Principle (SRP) diterapkan, sehingga kode lebih mudah dibaca dan diuji.
- Misalnya, metode untuk mengedit produk hanya menangani proses pengeditan, tanpa mencampurkan validasi atau logika database secara langsung.

### 4.) Menggunakan UUID sebagai ID produk agar lebih aman
- Dibandingkan dengan integer auto-increment, UUID lebih sulit ditebak sehingga mengurangi kemungkinan eksploitasi seperti ID enumeration attacks.

### 5.) Penamaan yang jelas sesuai dengan fungsi variabel/class/methodnya
- Contoh: Daripada menggunakan p sebagai nama variabel untuk produk, lebih baik menggunakan product agar lebih deskriptif.

## > Code yang dapat di-improve:
### 1.) Menambahkan Validasi ke Model Product
Saat ini, model Product belum memiliki validasi yang cukup untuk memastikan bahwa input yang dimasukkan benar. Dengan menggunakan annotation seperti @Valid, @NotNull, dan @Size, kita bisa mengurangi kesalahan input user.

Solusi:
Tambahkan validasi di model Product:
```
public class Product {
    @NotBlank(message = "Nama produk tidak boleh kosong")
    private String productName;

    @Min(value = 0, message = "Jumlah produk tidak boleh negatif")
    private int productQuantity;

    @NotNull(message = "Harga harus diisi")
    @DecimalMin(value = "0.01", message = "Harga tidak boleh 0 atau negatif")
    private BigDecimal price;
}
```
Manfaatnya:
- Memastikan bahwa produk selalu memiliki nama dan harga.
- Menghindari kesalahan input seperti harga negatif atau jumlah produk yang tidak masuk akal.

### 2.) Error Handling yang Lebih Baik
Saat ini, error handling dalam aplikasi masih minim. Jika terjadi error seperti produk tidak ditemukan saat ingin dihapus, sistem mungkin akan menampilkan error default yang kurang informatif atau bahkan menyebabkan crash.

Solusi:
Gunakan @ControllerAdvice untuk menangani error secara global.
```
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```
Manfaatnya:
- Pesan error lebih terstruktur dan mudah dipahami user.
- Menghindari tampilan error default yang mungkin mengandung informasi sensitif.

### 3.) Menambahkan Proteksi CSRF
Tanpa CSRF protection, aplikasi rentan terhadap serangan di mana user bisa secara tidak sengaja mengirimkan request tanpa sadar.

Solusi:
Pastikan CSRF token digunakan dalam setiap form yang mengubah data:
```
<form th:action="@{/product/delete/{id}(id=${product.id})}" method="post">
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
    <button type="submit">Delete</button>
</form>
```
Manfaatnya:
- Mencegah serangan CSRF dengan memastikan setiap request berasal dari sumber yang sah.

### 4.) Menambahkan Autentikasi dan Validasi untuk User/Admin

Saat ini, sistem belum membedakan antara user biasa dan admin. Ini bisa menyebabkan risiko keamanan di mana siapa pun bisa menghapus atau mengedit produk.

Solusi:
Gunakan Spring Security untuk membatasi akses hanya kepada admin:
```
@PreAuthorize("hasRole('ADMIN')")
@PostMapping("/product/delete/{id}")
public String deleteProduct(@PathVariable String id) {
    productService.deleteById(id);
    return "redirect:/product/list";
}
```
Manfaatnya:
- Hanya admin yang bisa mengedit atau menghapus produk, meningkatkan keamanan aplikasi.

### 5.) Implementasi Logging untuk Debugging dan Monitoring
Saat ini, kode tidak memiliki logging, sehingga jika terjadi error, sulit untuk mengetahui penyebabnya.

Solusi:
```
private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

public void deleteById(String id) {
    logger.info("Menghapus produk dengan ID: {}", id);
    productRepository.deleteById(id);
}
```
Manfaatnya:
- Membantu melacak aksi user untuk debugging dan keamanan.
- Bisa digunakan untuk monitoring aplikasi.
