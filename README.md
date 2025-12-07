# Sistem Rental Kendaraan

Program desktop berbasis Java menggunakan Swing GUI untuk manajemen sistem rental kendaraan. Proyek ini dikembangkan sebagai tugas mata kuliah Pemrograman Berorientasi Objek (OOP).

## 📋 Fitur Utama

- **Manajemen Data Kendaraan** – Menambah, mengedit, menghapus, dan menampilkan daftar kendaraan (Mobil, Bus, Motor, Truck, dan varian listrik)
- **Manajemen Data Pelanggan** – Menyimpan dan mengelola informasi pelanggan rental
- **Proses Peminjaman Kendaraan** – Mencatat detail peminjaman dengan perhitungan harga otomatis
- **Proses Pengembalian Kendaraan** – Menangani pengembalian kendaraan dan perhitungan biaya tambahan (jika ada)
- **Sistem Diskon** – Menerapkan diskon berdasarkan strategi (normal, weekend, persen)
- **Dashboard** – Tampilan ringkasan data dan statistik
- **Laporan Transaksi** – Menampilkan riwayat rental dan pengembalian

## 🛠 Teknologi & Arsitektur

### Stack Teknologi

- **Bahasa** – Java 8
- **UI Framework** – Swing (Java AWT/Swing)
- **Build Tool** – Maven 3.x+
- **Database** – PostgreSQL 42.7.3
- **Testing** – JUnit 4, JUnit 5 (Jupiter)

### Design Pattern & Prinsip OOP

- **Factory Pattern** – `KendaraanFactory` untuk pembuatan objek kendaraan
- **Strategy Pattern** – Strategi perhitungan harga (`HargaStrategy`, `DiskonPersenStrategy`, `WeekendStrategy`, `NormalStrategy`)
- **Facade Pattern** – `RentalFacade` untuk interface penyederhanaan operasi rental
- **MVC Architecture** – Pemisahan Controller, Model, dan View
- **Repository Pattern** – Abstraksi akses data melalui repository layer

## 📦 Prasyarat Instalasi

Sebelum menjalankan aplikasi, pastikan Anda memiliki:

- **Java Development Kit (JDK)** versi 8 atau lebih baru
  ```bash
  java -version
  ```
- **Maven** versi 3.6 atau lebih baru
  ```bash
  mvn -version
  ```
- **PostgreSQL** versi 9.0 atau lebih baru (server berjalan di `localhost:5432`)
- **Git** untuk clone repository

## 📁 Struktur Proyek

```
Sistem-Rental-Kendaraan/
├── README.md
├── rental-kendaraan/
│   ├── pom.xml                          # Konfigurasi Maven & dependencies
│   ├── sql/                             # Script database
│   │   ├── create_table.sql             # Schema tabel
│   │   ├── dummy_kendaraan.sql          # Data dummy kendaraan
│   │   ├── dummy_pelanggan.sql          # Data dummy pelanggan
│   │   └── dummy_rental.sql             # Data dummy rental
│   ├── src/
│   │   ├── main/java/com/rental/
│   │   │   ├── App.java                 # Entry point aplikasi
│   │   │   ├── controller/
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── PengembalianController.java
│   │   │   │   └── RentalController.java
│   │   │   ├── data/
│   │   │   │   └── DatabaseConnection.java    # Koneksi database
│   │   │   ├── model/
│   │   │   │   ├── kendaraan/           # Model kendaraan
│   │   │   │   │   ├── Kendaraan.java   # Base class
│   │   │   │   │   ├── KendaraanFactory.java
│   │   │   │   │   ├── Mobil.java
│   │   │   │   │   ├── MobilListrik.java
│   │   │   │   │   ├── Bus.java
│   │   │   │   │   ├── Truck.java
│   │   │   │   │   ├── Motor.java
│   │   │   │   │   └── MotorListrik.java
│   │   │   │   ├── pelanggan/
│   │   │   │   │   └── Pelanggan.java
│   │   │   │   └── rental/
│   │   │   │       ├── Rental.java
│   │   │   │       └── RentalFacade.java
│   │   │   ├── repository/
│   │   │   │   ├── KendaraanRepository.java
│   │   │   │   ├── PelangganRepository.java
│   │   │   │   └── RentalRepository.java
│   │   │   ├── strategy/
│   │   │   │   ├── HargaStrategy.java
│   │   │   │   ├── DiskonPersenStrategy.java
│   │   │   │   ├── NormalStrategy.java
│   │   │   │   └── WeekendStrategy.java
│   │   │   └── view/
│   │   │       ├── MainView.java
│   │   │       ├── DashboardView.java
│   │   │       ├── FormRentalView.java
│   │   │       └── PengembalianView.java
│   │   └── test/java/com/rental/        # Unit tests
│   │       ├── AppTest.java
│   │       └── model/kendaraan/KendaraanFactoryTest.java
│   └── target/                          # Build output (generated)
```

## 🚀 Cara Setup & Menjalankan Aplikasi

### 1. Clone Repository

```bash
git clone https://github.com/ftmhhwwa/Sistem-Rental-Kendaraan.git
cd Sistem-Rental-Kendaraan
```

### 2. Setup Database PostgreSQL

1. Buat database baru (misal: `rental_db`):

   ```sql
   CREATE DATABASE rental_db;
   ```

2. Jalankan script SQL dari folder `sql/`:

   ```bash
   psql -U postgres -d rental_db -f rental-kendaraan/sql/create_table.sql
   psql -U postgres -d rental_db -f rental-kendaraan/sql/dummy_kendaraan.sql
   psql -U postgres -d rental_db -f rental-kendaraan/sql/dummy_pelanggan.sql
   psql -U postgres -d rental_db -f rental-kendaraan/sql/dummy_rental.sql
   ```

3. Catat username dan password database Anda.

### 3. Konfigurasi Environment Variables

Sebelum menjalankan aplikasi, set environment variables untuk kredensial database:

**Pada Windows PowerShell (sementara untuk sesi saat ini):**

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/rental_db"
$env:DB_USER = "your_db_user"
$env:DB_PASSWORD = "your_db_password"
```

**Untuk membuat permanent (Windows):**

```powershell
setx DB_URL "jdbc:postgresql://localhost:5432/rental_db"
setx DB_USER "your_db_user"
setx DB_PASSWORD "your_db_password"
```

Setelah menjalankan `setx`, buka PowerShell window baru agar perubahan berlaku.

**Alternatif: Menggunakan file `.env`**

Buat file `.env` di root project:

```env
DB_URL=jdbc:postgresql://localhost:5432/rental_db
DB_USER=your_db_user
DB_PASSWORD=your_db_password
```

Tambahkan `.env` ke `.gitignore` agar tidak ter-commit ke repository:

```
.env
```

Load `.env` di PowerShell sebelum menjalankan aplikasi:

```powershell
Get-Content .env | ForEach-Object {
  if ($_ -match '^\s*#' -or $_ -match '^\s*$') { } else {
    $parts = $_ -split '=', 2
    if ($parts.Length -eq 2) { $env:$($parts[0].Trim()) = $parts[1].Trim() }
  }
}
```

### 4. Build & Jalankan Aplikasi

**Menggunakan Maven:**

```bash
cd rental-kendaraan
mvn clean install
mvn exec:java -Dexec.mainClass="com.rental.App"
```

**Atau dari IDE:**

1. Buka project di IDE favorit Anda (NetBeans, IntelliJ IDEA, Eclipse)
2. Right-click pada `App.java` → Run

**Manual build & run:**

```bash
# Compile
javac -d target/classes src/main/java/com/rental/*.java src/main/java/com/rental/*/*.java

# Run (pastikan environment variables sudah di-set)
java -cp "target/classes:lib/*" com.rental.App
```

## 📝 Contoh Penggunaan

### Menambah Kendaraan

1. Buka aplikasi → klik menu "Manajemen Kendaraan"
2. Klik tombol "Tambah Kendaraan"
3. Pilih jenis kendaraan (Mobil, Bus, Motor, Truck, Listrik)
4. Isi data kendaraan (plat nomor, merek, harga per hari, dll)
5. Klik "Simpan"

### Melakukan Rental

1. Klik menu "Rental Kendaraan"
2. Pilih pelanggan dan kendaraan yang akan dirental
3. Tentukan tanggal peminjaman dan estimasi pengembalian
4. Sistem akan menghitung total harga otomatis (dengan diskon jika berlaku)
5. Klik "Proses Rental" untuk menyimpan transaksi

### Pengembalian Kendaraan

1. Klik menu "Pengembalian"
2. Pilih ID rental dari daftar
3. Verifikasi kondisi kendaraan
4. Sistem akan menghitung denda jika ada keterlambatan
5. Klik "Selesaikan Pengembalian"

## 🧪 Menjalankan Unit Tests

```bash
cd rental-kendaraan
mvn test
```

Atau jalankan test spesifik:

```bash
mvn test -Dtest=KendaraanFactoryTest
```

## 🔒 Keamanan & Best Practices

- **Kredensial Database:** Jangan pernah commit file `.env` atau password ke Git. Gunakan environment variables atau secret manager.
- **Rotasi Password:** Jika password database terekspos, segera ubah melalui PostgreSQL:
  ```sql
  ALTER USER your_db_user WITH PASSWORD 'new_secure_password';
  ```
- **Validasi Input:** Aplikasi harus memvalidasi semua input user untuk mencegah SQL injection
- **Koneksi Database:** Selalu gunakan prepared statements untuk query ke database

## 🤝 Kontribusi

Kami menyambut kontribusi dari komunitas! Silakan lakukan langkah berikut:

1. **Fork** repository ini
2. **Buat branch** fitur baru:
   ```bash
   git checkout -b feature/nama-fitur
   ```
3. **Commit** perubahan Anda dengan pesan deskriptif:
   ```bash
   git commit -m "Tambah fitur: deskripsi singkat"
   ```
4. **Push** ke branch Anda:
   ```bash
   git push origin feature/nama-fitur
   ```
5. **Buat Pull Request** dan deskripsikan perubahan Anda dengan detail

### Panduan Coding

- Ikuti Java naming conventions (camelCase untuk variable/method, PascalCase untuk class)
- Tulis unit test untuk fitur baru
- Pastikan code ter-build tanpa warning
- Gunakan meaningful commit messages

## 📄 Lisensi

Proyek ini dilisensikan di bawah **MIT License**. Lihat file [LICENSE](LICENSE) untuk detail lebih lanjut.

MIT License memungkinkan Anda untuk:

- ✅ Menggunakan, memodifikasi, dan mendistribusikan kode
- ✅ Menggunakan untuk tujuan komersial atau personal
- ⚠️ Tetapi harus menyertakan lisensi dan disclaimer

## 👤 Penulis

Dibuat oleh **tim panconglumerrr** sebagai tugas mata kuliah **Pemrograman Berorientasi Objek (OOP)**.

---

**Terakhir diupdate:** Desember 2025

Jika ada pertanyaan atau menemukan bug, silakan buat [issue](https://github.com/ftmhhwwa/Sistem-Rental-Kendaraan/issues) di repository ini.
