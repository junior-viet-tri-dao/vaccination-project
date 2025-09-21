package com.viettridao.vaccination.config;

import com.viettridao.vaccination.model.QuyenHanEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.QuyenHanRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.repository.VaiTroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApplicationInitConfig implements ApplicationRunner {
    private final VaiTroRepository vaiTroRepository;
    private final TaiKhoanRepository taiKhoanRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuyenHanRepository quyenHanRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initRoles();
        initPermissions();
        assignPermissionsToAdmin();
        assignPermissionsToWarehouse();

        //Khởi tạo tài khoản admin mặc định
        initAdminAccount();
    }

    private void initRoles() {
        createRoleIfNotExists("ADMIN", "Quản trị hệ thống");
        createRoleIfNotExists("DOCTER", "Nhân viên y tế");
        createRoleIfNotExists("SUPPORTER", "Nhân viên hỗ trợ");
        createRoleIfNotExists("WAREHOUSE", "Nhân viên kho");
        createRoleIfNotExists("NORMAL_USER", "Bệnh nhân");
        createRoleIfNotExists("FINANCE", "Nhân viên tài chính");

    }

    private void createRoleIfNotExists(String ten, String moTa) {
        if (!vaiTroRepository.findByTen(ten).isPresent()) {
            VaiTroEntity role = new VaiTroEntity();
            role.setTen(ten);
            role.setMoTa(moTa);
            vaiTroRepository.save(role);
        }
    }

    private void createPermissionIfNotExists(String ten, String moTa) {
        if (quyenHanRepository.findByTen(ten).isEmpty()) {
            QuyenHanEntity role = new QuyenHanEntity();
            role.setTen(ten);
            role.setMoTa(moTa);
            quyenHanRepository.save(role);
        }
    }

//    private void initPermissions() {
//        // Danh sách quyền hạn mặc định
//        List<QuyenHanEntity> permissions = List.of(
//                QuyenHanEntity.builder().ten("READ_USER").moTa("Xem thông tin người dùng").build(),
//                QuyenHanEntity.builder().ten("CREATE_USER").moTa("Tạo người dùng mới").build(),
//                QuyenHanEntity.builder().ten("UPDATE_USER").moTa("Cập nhật thông tin người dùng").build(),
//                QuyenHanEntity.builder().ten("DELETE_USER").moTa("Xóa người dùng").build(),
//                QuyenHanEntity.builder().ten("VIEW_WAREHOUSE").moTa("Quyền xem tình hình kho bãi").build(),
//                QuyenHanEntity.builder().ten("CREATE_IMPORT_WAREHOUSE").moTa("Nhập kho").build(),
//                QuyenHanEntity.builder().ten("CREATE_EXPORT_WAREHOUSE").moTa("Xuất kho").build()

    /// /                QuyenHanEntity.builder().ten("VIEW_REPORT").moTa("Xem báo cáo thống kê").build()
//        );
//
//        // Lọc ra những permission chưa tồn tại trong DB
//        List<QuyenHanEntity> newPermissions = permissions.stream()
//                .filter(p -> quyenHanRepository.findByTen(p.getTen()).isEmpty())
//                .toList();
//
//        // Lưu tất cả permission mới
//        if (!newPermissions.isEmpty()) {
//            quyenHanRepository.saveAll(newPermissions);
//        }
//    }
    private void initPermissions() {
        System.out.println("=== [initPermissions] Bắt đầu khởi tạo quyền hạn ===");
        List<QuyenHanEntity> permissions = List.of(
                QuyenHanEntity.builder().ten("READ_USER").moTa("Xem thông tin người dùng").build(),
                QuyenHanEntity.builder().ten("CREATE_USER").moTa("Tạo người dùng mới").build(),
                QuyenHanEntity.builder().ten("UPDATE_USER").moTa("Cập nhật thông tin người dùng").build(),
                QuyenHanEntity.builder().ten("DELETE_USER").moTa("Xóa người dùng").build(),
                QuyenHanEntity.builder().ten("VIEW_WAREHOUSE").moTa("Quyền xem tình hình kho bãi").build(),
                QuyenHanEntity.builder().ten("CREATE_IMPORT_WAREHOUSE").moTa("Nhập kho").build(),
                QuyenHanEntity.builder().ten("CREATE_EXPORT_WAREHOUSE").moTa("Xuất kho").build()
        );

        List<QuyenHanEntity> newPermissions = permissions.stream()
                .filter(p -> quyenHanRepository.findByTen(p.getTen()).isEmpty())
                .toList();

        System.out.println("Số quyền hạn sẽ insert: " + newPermissions.size());
        newPermissions.forEach(q -> System.out.println("Insert quyền: " + q.getTen()));

        if (!newPermissions.isEmpty()) {
            quyenHanRepository.saveAll(newPermissions);
            System.out.println("=== [initPermissions] Đã insert quyền hạn mới ===");
        } else {
            System.out.println("=== [initPermissions] Không có quyền hạn mới cần insert ===");
        }
    }

    /**
     * Gán tất cả quyền cho ADMIN
     */
    private void assignPermissionsToAdmin() {
        vaiTroRepository.findByTenWithPermissions("ADMIN").ifPresent(role -> {
            List<QuyenHanEntity> allPermissions = quyenHanRepository.findAll();
            role.getQuyenHans().addAll(allPermissions);
            vaiTroRepository.save(role);
        });
    } // n+1 query

    /**
     * Gán quyền kho cho nhân viên WAREHOUSE
     */
    private void assignPermissionsToWarehouse() {
        vaiTroRepository.findByTenWithPermissions("WAREHOUSE").ifPresent(role -> {
            List<String> warehousePerms = List.of(
                    "VIEW_WAREHOUSE",
                    "CREATE_IMPORT_WAREHOUSE",
                    "CREATE_EXPORT_WAREHOUSE"
            );

            List<QuyenHanEntity> permissions = quyenHanRepository.findAll().stream()
                    .filter(p -> warehousePerms.contains(p.getTen()))
                    .toList();

            role.getQuyenHans().addAll(permissions);
            vaiTroRepository.save(role);
        });
    }

    /**
     * Khởi tạo tài khoản admin nếu chưa tồn tại
     */
    private void initAdminAccount() {
        if (taiKhoanRepository.findByTenDangNhap("admin1").isEmpty()) {
            VaiTroEntity adminRole = vaiTroRepository.findByTen("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role ADMIN chưa tồn tại"));

            System.out.println("loadding admin account...");
            TaiKhoanEntity admin = TaiKhoanEntity.builder()
                    .tenDangNhap("admin1")
                    .matKhauHash(passwordEncoder.encode("admin123")) // mã hóa password
                    .email("admin@system.com")
                    .vaiTro(adminRole)
                    .hoTen("Nguyễn Văn Admin")
                    .soCmnd("0123456789")
                    .soDienThoai("0987654321")
                    .diaChi("Trụ sở chính")
                    .isDeleted(false)
                    .hoatDong(true)
                    .ngayTao(java.time.LocalDateTime.now())
                    .ngayCapNhat(java.time.LocalDateTime.now())
                    .build();

            taiKhoanRepository.save(admin);
        }
    }

    /**
     * Khởi tạo tài khoản normal user nếu chưa tồn tại
     */
    private void initNormalUserAccount() {
        if (taiKhoanRepository.findByTenDangNhap("user1").isEmpty()) {
            VaiTroEntity userRole = vaiTroRepository.findByTen("NORMAL_USER")
                    .orElseThrow(() -> new RuntimeException("Role bệnh nhân chưa tồn tại"));

            System.out.println("loadding normal user account...");
            TaiKhoanEntity user = TaiKhoanEntity.builder()
                    .tenDangNhap("user1")
                    .matKhauHash(passwordEncoder.encode("user123")) // mã hóa password
                    .email("user1@system.com")
                    .vaiTro(userRole)
                    .hoTen("Nguyễn Văn Nam")
                    .soCmnd("01546456789")
                    .soDienThoai("0968787321")
                    .diaChi("An Khánh, Huyện Hoài Đức, Hà Nội")
                    .isDeleted(false)
                    .hoatDong(true)
                    .ngayTao(java.time.LocalDateTime.now())
                    .ngayCapNhat(java.time.LocalDateTime.now())
                    .build();

            taiKhoanRepository.save(user);
        }
    }

}
