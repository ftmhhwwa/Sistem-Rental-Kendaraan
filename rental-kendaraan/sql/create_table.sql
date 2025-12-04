-- 1. PELANGGAN (Tidak ada perubahan signifikan)
CREATE TABLE pelanggan (
    id SERIAL PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    nik VARCHAR(50) NOT NULL UNIQUE,
    no_hp VARCHAR(20) NOT NULL,
    alamat TEXT
);

-- 2. KENDARAAN (Perubahan: Status huruf kecil & DOUBLE PRECISION)
CREATE TABLE kendaraan (
    no_polisi VARCHAR(20) PRIMARY KEY,
    merk VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    tahun INT NOT NULL,
    harga_dasar DOUBLE PRECISION NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('tersedia', 'tidak tersedia')), 
    jenis VARCHAR(30) NOT NULL
);

-- 3. RENTAL (Tidak ada perubahan signifikan)
CREATE TABLE rental (
    id SERIAL PRIMARY KEY,
    pelanggan_id INT NOT NULL,
    no_polisi VARCHAR(20) NOT NULL,
    tgl_pinjam DATE NOT NULL,
    tgl_kembali DATE NOT NULL,
    harga_total DOUBLE PRECISION NOT NULL,
    strategy_name VARCHAR(50) NOT NULL,

    CONSTRAINT fk_pelanggan 
        FOREIGN KEY (pelanggan_id) REFERENCES pelanggan(id)
        ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_kendaraan 
        FOREIGN KEY (no_polisi) REFERENCES kendaraan(no_polisi)
        ON UPDATE CASCADE ON DELETE CASCADE
);