Nama : Arief Ridzki Darmawan

NPM : 2306210115

Kelas : A

---
## Refleksi - Modul 4
### > Apakah flow TDD ini cukup berguna? Jika tidak, apa yang harus saya lakukan saat membuat lebih banyak test nanti?
Menurut saya, flow TDD berguna agar saya mengimplementasikan hal-hal yang hanya dibutuhkan untuk lolos test saja, sehingga saya tidak membuang waktu dengan menulis lebih banyak kode dari apa yang dibutuhkan. Hal yang dapat saya lakukan jika membuat lebih banyak test nantinya adalah menambahkan lebih banyak edge case dan refactoring test agar lebih efisien.

### > Apakah test saya mengikuti prinsip F.I.R.S.T? Jika tidak, apa yang harus saya lakukan saat membuat lebih banyak test nanti?
Ya, test saya mengikuti prinsip F.I.R.S.T.
* Fast
Saya menggunakan mock dan test juga berjalan dengan cepat.
* Independent
Setiap test tidak bergantung pada test lain.
* Repeatable
Test tidak memerlukan sistem lain, sehingga dapat diulang-ulang.
* Self-validating
Test memberi umpan balik langsung berupa apakah test berhasil atau tidak, bukan hasil test yang harus di-cek secara manual.
* Timely
Test dibuat sebelum kode dibuat dan menelusuri semua happy/unhappy path.

<details>
<summary>Modul 3</summary>
  
## Refleksi - Modul 3
### > Prinsip SOLID yang dipakai:
#### 1.) SRP
Saya memisah `CarController` dari `ProductController` agar tiap controller mempunyai tugas masing-masing.
#### 2.) OCP
-
#### 3.) LSP
-
#### 4.) ISP
`CarServiceImpl` dan `ProductServiceImpl` mengimplementasi semua method dari interface masing-masing.
#### 5.) DIP
Saya mengubah `CarController` agar menggunakan interface `CarService` dibanding langsung menggunakan `CarServiceImpl`.

### > Keuntungan SOLID pada project:
- Membuat kode menjadi lebih readable dan lebih mudah dimengerti
  
Contohnya CarController dan ProductController yang berada di file berbeda sehingga memudahkan kita dan orang lain untuk mencari dan memperbaiki bug.
- Mengurangi dependensi modul level tinggi dari modul level rendah

Contohnya CarController yang bergantung pada interface CarService dibanding CarServiveImpl yang dapat menyebabkan tight coupling.

### > Kelemahan jika tidak mengimplementasi SOLID pada project:
- Menyulitkan kita dan orang lain untuk mengerti kode kita

Contohnya banyak controller yang berada dalam satu file akan membingungkan kita dan memakan waktu untuk mencari dan memperbaiki kode yang bermasalah.
- Menyulitkan kita jika mengganti potongan kode

Contohnya kita mungkin harus mengubah CarController juga setelah mengubah ProductController karena CarController meng-extend ProductContoller yang akan menambah pekerjaan kita.
</details>

<details>
<summary>Modul 2</summary>
    
## Refleksi - Modul 2
### > Potongan kode yang mengalami isu:
#### Unnecessary modifier 'public' on method 'create': the method is declared in an interface type
```
public Product create(Product product);
public List<Product> findAll();
public Product update(Product product);
public Product findById(String id);
public boolean deleteProduct(String id);
```
Cara saya mengatasi isu ini hanyalah dengan menghapus modifier 'public' pada method-method di ProductService.java karena field secara otomatis dianggap public static final pada interfaces and annotations dan public abstract pada sebuah method.

### > Apakah implementasi workflow CI/CD sekarang masuk ke dalam definisi CI dan CD?
Tidak secara sepenuhnya, karena implementasi saya belum mencakup beberapa aspek penting dari CI/CD, terutama dalam Continuous Deployment. Saya sudah menerapkan CI dengan `ci.yml` untuk otomatisasi testing. Saya juga memiliki Dockerfile untuk deployment, namun workflow deployment ini masih berjalan secara independen tanpa pipeline yang mengintegrasikan build, push, dan deployment otomatis. Selain itu, belum ada mekanisme rollback dan monitoring, yang penting untuk memastikan sistem tetap stabil setelah deployment.
</details>

<details>
<summary>Modul 1</summary>
    
## Refleksi 1 - Modul 1
### > Clean code practices yang diimplementasikan:
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

---

## Refleksi 2 - Modul 1
## 1. Menulis Unit Test & Code Coverage
### Bagaimana perasaanmu setelah menulis unit test?
- Unit testing memberikan kepercayaan diri bahwa setiap komponen program bekerja sesuai harapan.
- Menulis unit test membantu memahami program lebih dalam dan berpikir tentang edge case yang mungkin akan terjadi.
- Proses membuat unit test bisa memakan waktu, dan terkadang tes gagal hanya karena perubahan kecil dalam implementasi. Namun, dari apa yang disebutkan oleh pembicara kuliah kemarin, unit test akan menghemat waktu dalam jangka panjang dan membayar kembali waktu yang dibutuhkan di awal.

### Berapa banyak unit test yang harus dibuat dalam satu class?
Tidak ada jumlah yang tetap karena ini bergantung pada kompleksitas class tersebut. Namun, good practice-nya adalah memiliki setidaknya satu tes per metode dan tambahan untuk berbagai skenario, seperti:
- Kasus normal => Input yang sesuai harapan.
- Edge cases => Nilai minimum/maksimum, input kosong.
- Kasus error => Input tidak valid, pengecualian (exception).

### Bagaimana memastikan unit test sudah mencukupi?
Dengan melihat code coverage, yaitu metrik untuk mengukur seberapa banyak dan luas cakupan kode yang diuji. Tools seperti SonarQube dapat menunjukkan baris/cabang kode mana yang sudah dieksekusi oleh tes serta memberikan analisis kualitas kode secara keseluruhan.
Jenis code coverage meliputi:
- Line coverage => Persentase baris kode yang dieksekusi.
- Branch coverage => Memastikan semua kondisi if/else diuji.
- Path coverage => Menguji semua jalur eksekusi yang mungkin.

### Apakah 100% code coverage berarti tidak ada bug atau error?
Tidak. 100% coverage hanya berarti setiap baris kode dieksekusi, tetapi tidak menjamin kebenaran fungsionalitasnya.

Implementation
```
public class ShoppingCart {
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}
```

Unit Test (100% Coverage)
```
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {
    @Test
    void testCalculateTotal() {
        ShoppingCart cart = new ShoppingCart();
        assertEquals(20.0, cart.calculateTotal(10.0, 2));
    }
}
```
Semua line dieksekusi, sehingga test tersebut memiliki 100% coverage.
Tapi, code masih memiliki bug karena tidak mempertimbangkan beberapa edge case:
- Negative quantity: cart.calculateTotal(10.0, -2);
- Zero quantity: cart.calculateTotal(10.0, 0);
- Floating-point: cart.calculateTotal(0.1, 3);

Tes yang baik harus mencakup:
- Assertions untuk memverifikasi perilaku yang diharapkan.
- Skenario user yang realistis.
- Penanganan edge case dengan baik.

## 2. Kebersihan & Reusability dalam Functional Test
### Apa yang terjadi jika kita membuat test suite baru dengan setup yang mirip?
- Jika test class baru menduplikasi kode setup, maka kode menjadi lebih sulit untuk dipelihara.
- Redundansi ini melanggar prinsip DRY (Don't Repeat Yourself).

### Bagaimana dampaknya terhadap kualitas kode?
- Meningkatkan biaya pemeliharaan, jika setup perlu diubah, kita harus memperbarui di banyak tempat.
- Menurunkan readability, kode yang berulang membuat sulit melihat apa yang sebenarnya diuji.
- Meningkatkan risiko inkonsistensi, jika satu setup diperbarui tetapi yang lain tidak, hasil tes bisa berbeda.

### Apa perbaikan yang mungkin agar membuat kode lebih bersih?
- Membuat BaseFunctionalTest agar setup tidak berulang dan meng-extend base class untuk test case yang lebih spesifik
- Menggunakan helper methods
- Menggunakan parameterized tests
</details>
