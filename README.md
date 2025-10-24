## Responsi 1 Praktikum Pemrograman Mobile
Nama       : Isma Fadhilatizzahra

NIM        : H1D023107

Shift KRS  : D

Shift Baru : C 

## Video Demo Aplikasi (Getafe CF {82})

https://github.com/user-attachments/assets/78be5e18-02a0-454c-97a5-95537275a22b

## Alur Penjelasan API Alur Data
Alur data pada aplikasi ini menggunakan arsitektur modern Android (MVVM) yang memanfaatkan ViewModel untuk manajemen state, Retrofit untuk komunikasi jaringan, Gson untuk parsing JSON, dan LiveData untuk observasi data.
Berikut adalah penjelasan rincinya langkah demi langkah:
1. Pemuatan Data Awal (MainActivity & ViewModel)
Aplikasi pertama kali terbuka di MainActivity.

MainActivity menginisiasi MainViewModel menggunakan by viewModels().

Saat MainViewModel dibuat (pertama kali), blok init di dalamnya akan langsung memanggil fungsi fetchTeamData().


2. Permintaan API (Retrofit & ApiClient)
Fungsi fetchTeamData() di MainViewModel menampilkan ProgressBar (via LiveData) dan membuat antrean (enqueue) pemanggilan API menggunakan ApiClient.apiService.getTeamDetails().

ApiClient adalah singleton object Retrofit. Saat membuat client, ia menyuntikkan (inject) sebuah Interceptor khusus.

Interceptor ini bertugas menambahkan header X-Auth-Token yang berisi Token API Anda ke setiap request yang keluar.

ApiService adalah interface yang mendefinisikan endpoint. Retrofit menggabungkan BASE_URL ("https://api.football-data.org/") dengan @GET ("v4/teams/82") untuk membentuk URL pemanggilan yang lengkap.


3. Parsing Data (Gson & Data Models)
API merespons dengan data JSON yang berisi semua informasi klub.

Retrofit (yang sudah terkonfigurasi dengan GsonConverterFactory) secara otomatis mem-parsing string JSON tersebut menjadi objek data class Kotlin yang telah kita siapkan: TeamResponse.

Objek TeamResponse ini berisi data klub, serta objek Coach dan List<Player> di dalamnya.


4. Observasi & Tampilan (LiveData & MainActivity)
Jika pemanggilan API sukses (onResponse), MainViewModel mengambil response.body() (yang berisi objek TeamResponse) dan memperbarui _teamData.value.

MainActivity secara aktif meng-observe (mengamati) viewModel.teamData.

Saat teamData berubah, MainActivity menerima objek TeamResponse baru. Data ini kemudian ditampilkan di UI (seperti tvClubName, dan memuat crestUrl via Glide) dan juga disimpan dalam cache lokal (teamDataCache) untuk digunakan nanti.


5. Alur Navigasi & Pengiriman Data (Intent & Fragment)
Aplikasi kemudian menunggu interaksi pengguna:

A. Halaman Pelatih (CoachActivity)

Pengguna mengklik tombol "Head Coach".

MainActivity mengambil data pelatih (teamDataCache.coach) yang sudah tersimpan.

Data objek Coach (yang sudah Parcelable) dimasukkan ke dalam Intent sebagai EXTRA_COACH.

CoachActivity dimulai, mengambil objek Coach dari Intent, dan menampilkan datanya.

B. Halaman Skuad (SquadActivity & Fragment)

Pengguna mengklik tombol "Team Squad".

MainActivity mengambil data skuad (teamDataCache.squad).

Data List<Player> diubah menjadi ArrayList<Player> (agar bisa dikirim via Intent) dan dimasukkan sebagai EXTRA_SQUAD.

SquadActivity dimulai dan mengambil ArrayList<Player> dari Intent.

SquadActivity mengacak (.shuffle()) daftar pemain agar warnanya bervariasi.

Daftar pemain yang sudah diacak diberikan ke SquadAdapter.

SquadAdapter (di dalam onBindViewHolder) mengatur data (nama, kebangsaan) dan mengatur warna kartu (setCardBackgroundColor) menggunakan logika when berdasarkan player.position (dengan workaround untuk data null).

Saat pengguna mengklik sebuah kartu pemain, callback onPlayerClicked di adapter akan terpicu.

SquadActivity menerima callback ini dan memanggil showPlayerDetails(player).

Fungsi showPlayerDetails membuat instance baru dari PlayerDetailFragment (sebuah BottomSheetDialogFragment) menggunakan PlayerDetailFragment.newInstance(player), yang memasukkan data Player ke dalam Arguments Fragment.

Fragment tersebut ditampilkan (.show()) dan muncul dari bawah layar, mengambil data pemain dari arguments-nya, dan menampilkannya di layout fragment_player_detail.xml.
