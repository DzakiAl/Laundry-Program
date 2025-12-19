import java.util.ArrayList;
import java.util.Scanner;

// MODEL
class DataLaundry {
    String namaCustomer;
    String noTelepon;

    ArrayList<Double> jumlahItem = new ArrayList<>();
    ArrayList<Double> biayaItem = new ArrayList<>();

    String namaLayanan;
    double persenLayanan;

    double subtotal;
    double totalSetrika;
    double diskon;
    double totalBayar;
    String catatan;

    public DataLaundry(String nama, String telp, int totalItem) {
        this.namaCustomer = nama;
        this.noTelepon = telp;
        this.namaLayanan = "Reguler";
        this.persenLayanan = 0;
        this.totalSetrika = 0;

        for (int i = 0; i < totalItem; i++) {
            jumlahItem.add(0.0);
            biayaItem.add(0.0);
        }
    }

    boolean adaItem() {
        for (double j : jumlahItem) {
            if (j > 0)
                return true;
        }
        return false;
    }

    void hitungSubtotal() {
        subtotal = 0;
        for (double b : biayaItem)
            subtotal += b;
    }
}

// MAIN
public class ProgramLaundry {

    static Scanner input = new Scanner(System.in);
    static ArrayList<DataLaundry> riwayat = new ArrayList<>();

    // ITEM
    static ArrayList<String> NAMA_ITEM = new ArrayList<>();
    static ArrayList<String> SATUAN = new ArrayList<>();
    static ArrayList<Double> HARGA = new ArrayList<>();
    static ArrayList<Boolean> BISA_SETRIKA = new ArrayList<>();
    static ArrayList<Boolean> ITEM_AKTIF = new ArrayList<>();

    // LAYANAN
    static ArrayList<String> NAMA_LAYANAN = new ArrayList<>();
    static ArrayList<Double> PERSEN_LAYANAN = new ArrayList<>();
    static ArrayList<Boolean> LAYANAN_AKTIF = new ArrayList<>();

    static double HARGA_SETRIKA = 3000;

    public static void main(String[] args) {

        // DEFAULT ITEM
        tambahItem("Pakaian", "kg", 5000, true);
        tambahItem("Celana Dalam", "pcs", 2000, false);
        tambahItem("Selimut", "meter", 15000, true);

        // DEFAULT LAYANAN
        tambahLayanan("Reguler", 0);
        tambahLayanan("Express", 0.3);
        tambahLayanan("Super Express", 0.6);

        while (true) {
            menuUtama();
            int pilih = inputInt("Pilih menu: ");

            switch (pilih) {
                case 1 -> transaksiBaru();
                case 2 -> tampilkanRiwayat();
                case 3 -> editHargaItem();
                case 4 -> menuTambahItem();
                case 5 -> menuTambahLayanan();
                case 6 -> menuToggleItem();
                case 7 -> menuToggleLayanan();
                case 8 -> editHargaSetrika();
                case 9 -> tampilkanDaftarLayanan();
                case 10 -> {
                    System.out.println("Program selesai.");
                    return;
                }
            }
        }
    }

    // ================= MENU =================
    static void menuUtama() {
        System.out.println("\n===== PROGRAM LAUNDRY =====");
        System.out.println("1. Transaksi Baru");
        System.out.println("2. Riwayat Transaksi");
        System.out.println("3. Edit Harga Item");
        System.out.println("4. Tambah Item Laundry");
        System.out.println("5. Tambah Jenis Layanan");
        System.out.println("6. Aktif / Nonaktif Item");
        System.out.println("7. Aktif / Nonaktif Layanan");
        System.out.println("8. Edit Harga Setrika");
        System.out.println("9. Lihat Daftar Layanan");
        System.out.println("10. Keluar");
    }

    // ================= ITEM =================
    static void tambahItem(String nama, String satuan, double harga, boolean setrika) {
        NAMA_ITEM.add(nama);
        SATUAN.add(satuan);
        HARGA.add(harga);
        BISA_SETRIKA.add(setrika);
        ITEM_AKTIF.add(true);
    }

    static void menuTambahItem() {
        System.out.print("Nama item: ");
        String nama = input.nextLine();
        System.out.print("Satuan: ");
        String satuan = input.nextLine();
        double harga = inputDouble("Harga: ");
        System.out.print("Bisa setrika? (y/n): ");
        boolean setrika = input.nextLine().equalsIgnoreCase("y");

        tambahItem(nama, satuan, harga, setrika);
        System.out.println("Item ditambahkan.");
    }

    static void menuToggleItem() {
        while (true) {
            System.out.println("\n===== ITEM =====");
            for (int i = 0; i < NAMA_ITEM.size(); i++) {
                System.out.println((i + 1) + ". " + NAMA_ITEM.get(i) +
                        " [" + (ITEM_AKTIF.get(i) ? "AKTIF" : "NONAKTIF") + "]");
            }
            System.out.println("0. Kembali");

            int pilih = inputInt("Pilih: ");
            if (pilih == 0)
                return;

            int idx = pilih - 1;
            ITEM_AKTIF.set(idx, !ITEM_AKTIF.get(idx));
            System.out.println("Status diubah.");
        }
    }

    static void editHargaItem() {
        for (int i = 0; i < NAMA_ITEM.size(); i++) {
            System.out.println((i + 1) + ". " + NAMA_ITEM.get(i) + " Rp " + HARGA.get(i));
        }
        int pilih = inputInt("Pilih: ");
        double harga = inputDouble("Harga baru: ");
        HARGA.set(pilih - 1, harga);
        System.out.println("Harga diperbarui.");
    }

    // ================= LAYANAN =================
    static void tambahLayanan(String nama, double persen) {
        NAMA_LAYANAN.add(nama);
        PERSEN_LAYANAN.add(persen);
        LAYANAN_AKTIF.add(true);
    }

    static void menuTambahLayanan() {
        System.out.print("Nama layanan: ");
        String nama = input.nextLine();
        double persen = inputDouble("Persentase (contoh 30): ") / 100;
        tambahLayanan(nama, persen);
        System.out.println("Layanan ditambahkan.");
    }

    static void menuToggleLayanan() {
        while (true) {
            System.out.println("\n===== LAYANAN =====");
            for (int i = 0; i < NAMA_LAYANAN.size(); i++) {
                System.out.println((i + 1) + ". " + NAMA_LAYANAN.get(i) +
                        " [" + (LAYANAN_AKTIF.get(i) ? "AKTIF" : "NONAKTIF") + "]");
            }
            System.out.println("0. Kembali");

            int pilih = inputInt("Pilih: ");
            if (pilih == 0)
                return;

            int idx = pilih - 1;
            LAYANAN_AKTIF.set(idx, !LAYANAN_AKTIF.get(idx));
            System.out.println("Status diubah.");
        }
    }

    static void tampilkanDaftarLayanan() {
        for (int i = 0; i < NAMA_LAYANAN.size(); i++) {
            int persen = (int) (PERSEN_LAYANAN.get(i) * 100);
            System.out.println(NAMA_LAYANAN.get(i) + " (" + persen + "%)");
        }
    }

    // ================= TRANSAKSI =================
    static void transaksiBaru() {
        System.out.println("\n===== MASUKKAN DATA CUSTOMER =====");
        System.out.print("Nama Customer: ");
        String nama = input.nextLine();
        System.out.print("No Telepon: ");
        String telp = input.nextLine();

        DataLaundry data = new DataLaundry(nama, telp, NAMA_ITEM.size());
        pilihLayanan(data);

        while (true) {
            int nomor = 1;
            ArrayList<Integer> mapIndex = new ArrayList<>();
            System.out.println("\n===== PILIH ITEM LAUNDRY =====");
            for (int i = 0; i < NAMA_ITEM.size(); i++) {
                if (ITEM_AKTIF.get(i)) {
                    System.out.println(nomor + ". " + NAMA_ITEM.get(i));
                    mapIndex.add(i);
                    nomor++;
                }
            }
            System.out.println(nomor + ". Checkout");

            int pilih = inputInt("Pilih: ");

            if (pilih == nomor) {
                if (!data.adaItem()) {
                    System.out.println("Tidak bisa checkout. Pilih minimal 1 item laundry!");
                    continue;
                }
                break;
            }

            int idx = mapIndex.get(pilih - 1);
            double jumlah = inputDouble("Jumlah (" + SATUAN.get(idx) + "): ");
            double biaya = jumlah * HARGA.get(idx);

            data.jumlahItem.set(idx, data.jumlahItem.get(idx) + jumlah);
            data.biayaItem.set(idx, data.biayaItem.get(idx) + biaya);

            if (BISA_SETRIKA.get(idx)) {
                System.out.print("Tambah setrika? (y/n): ");
                if (input.nextLine().equalsIgnoreCase("y")) {
                    data.totalSetrika += jumlah * HARGA_SETRIKA;
                }
            }
        }

        checkout(data);
        riwayat.add(data);
    }

    static void pilihLayanan(DataLaundry data) {
        ArrayList<Integer> map = new ArrayList<>();

        System.out.println("\n===== PILIH LAYANAN =====");
        for (int i = 0; i < NAMA_LAYANAN.size(); i++) {
            if (LAYANAN_AKTIF.get(i)) {
                int persen = (int) (PERSEN_LAYANAN.get(i) * 100);
                System.out.println((i + 1) + ". " + NAMA_LAYANAN.get(i) +
                        (persen > 0 ? " (+" + persen + "%)" : " (0%)"));
                map.add(i);
            }
        }

        int pilih = inputInt("Pilih layanan: ");
        int idx = map.get(pilih - 1);
        data.namaLayanan = NAMA_LAYANAN.get(idx);
        data.persenLayanan = PERSEN_LAYANAN.get(idx);
    }

    static void checkout(DataLaundry data) {
        data.hitungSubtotal();
        double diskonPersen = inputDouble("Diskon (%): ");
        data.diskon = data.subtotal * diskonPersen / 100;

        double biayaLayanan = data.subtotal * data.persenLayanan;
        data.totalBayar = data.subtotal + data.totalSetrika + biayaLayanan - data.diskon;

        System.out.print("Catatan (Klik Enter jika tidak ada): ");
        data.catatan = input.nextLine();

        cetakStruk(data);
    }

    static void cetakStruk(DataLaundry d) {
        System.out.println("\n===== STRUK =====");
        System.out.println("Nama: " + d.namaCustomer);
        System.out.println("Layanan: " + d.namaLayanan + " (+" + (int) (d.persenLayanan * 100) + "%)");

        System.out.println("----------------------------------");
        for (int i = 0; i < d.jumlahItem.size(); i++) {
            if (d.jumlahItem.get(i) > 0) {
                double jumlah = d.jumlahItem.get(i);
                double harga = HARGA.get(i);
                double total = d.biayaItem.get(i);

                System.out.println(
                        NAMA_ITEM.get(i) + " : " +
                                jumlah + " " + SATUAN.get(i) +
                                " x " + harga +
                                " = Rp " + total);
            }
        }
        System.out.println("----------------------------------");

        System.out.println("Subtotal: Rp " + d.subtotal);
        System.out.println("Setrika: Rp " + d.totalSetrika);
        System.out.println("Diskon: Rp " + d.diskon);
        System.out.println("Total: Rp " + d.totalBayar);

        if (!d.catatan.isEmpty()) {
            System.out.println("Catatan: " + d.catatan);
        }
    }

    static void tampilkanRiwayat() {
        if (riwayat.isEmpty()) {
            System.out.println("\nBelum ada transaksi.");
            return;
        }

        System.out.println("\n===== RIWAYAT TRANSAKSI =====");

        for (int i = 0; i < riwayat.size(); i++) {
            System.out.println("\n=== TRANSAKSI #" + (i + 1) + " ===");
            cetakStruk(riwayat.get(i));
        }
    }

    // ================= INPUT =================
    static int inputInt(String p) {
        while (true) {
            try {
                System.out.print(p);
                return Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Masukkan angka!");
            }
        }
    }

    static double inputDouble(String p) {
        while (true) {
            try {
                System.out.print(p);
                return Double.parseDouble(input.nextLine());
            } catch (Exception e) {
                System.out.println("Masukkan angka!");
            }
        }
    }

    static void editHargaSetrika() {
        System.out.println("Harga setrika saat ini: " + HARGA_SETRIKA);
        HARGA_SETRIKA = inputDouble("Harga baru: ");
    }
}