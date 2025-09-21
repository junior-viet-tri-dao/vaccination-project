package com.viettridao.vaccination.config;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.viettridao.vaccination.model.QuyenHanEntity;
import com.viettridao.vaccination.model.TaiKhoanEntity;
import com.viettridao.vaccination.model.VaiTroEntity;
import com.viettridao.vaccination.repository.QuyenHanRepository;
import com.viettridao.vaccination.repository.TaiKhoanRepository;
import com.viettridao.vaccination.repository.VaiTroRepository;

import lombok.RequiredArgsConstructor;

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

		// Khởi tạo tài khoản admin mặc định
		initAdminAccount();
        initOtherAccounts();

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

	/// / QuyenHanEntity.builder().ten("VIEW_REPORT").moTa("Xem báo cáo thống
	/// kê").build()
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
				QuyenHanEntity.builder().ten("CREATE_EXPORT_WAREHOUSE").moTa("Xuất kho").build());

		List<QuyenHanEntity> newPermissions = permissions.stream()
				.filter(p -> quyenHanRepository.findByTen(p.getTen()).isEmpty()).toList();

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
			List<String> warehousePerms = List.of("VIEW_WAREHOUSE", "CREATE_IMPORT_WAREHOUSE",
					"CREATE_EXPORT_WAREHOUSE");

			List<QuyenHanEntity> permissions = quyenHanRepository.findAll().stream()
					.filter(p -> warehousePerms.contains(p.getTen())).toList();

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
			TaiKhoanEntity admin = TaiKhoanEntity.builder().tenDangNhap("admin1")
					.matKhauHash(passwordEncoder.encode("admin123")) // mã hóa password
					.email("admin@system.com").vaiTro(adminRole).hoTen("Nguyễn Văn Admin").soCmnd("0123456789")
					.soDienThoai("0987654321").diaChi("Trụ sở chính").isDeleted(false).hoatDong(true)
					.ngayTao(java.time.LocalDateTime.now()).ngayCapNhat(java.time.LocalDateTime.now()).build();

			taiKhoanRepository.save(admin);
		}

	}

	private void initOtherAccounts() {
		createAccountIfNotExists("doctor1", "doctor123", "DOCTER", "doctor@system.com", "Bác sĩ A", "123456789",
				"0901111222", "Phòng khám A");

		createAccountIfNotExists("support1", "support123", "SUPPORTER", "support@system.com", "Nhân viên hỗ trợ B",
				"223456789", "0902222333", "CSKH");

		createAccountIfNotExists("warehouse1", "warehouse123", "WAREHOUSE", "warehouse@system.com", "Nhân viên kho C",
				"323456789", "0903333444", "Kho trung tâm");

		createAccountIfNotExists("user1", "user123", "NORMAL_USER", "user@system.com", "Người dùng D", "423456789",
				"0904444555", "Hà Nội");

		createAccountIfNotExists("finance1", "finance123", "FINANCE", "finance@system.com", "Kế toán E", "523456789",
				"0905555666", "Phòng tài chính");
	}

	private void createAccountIfNotExists(String username, String rawPassword, String roleName, String email,
			String hoTen, String soCmnd, String soDienThoai, String diaChi) {
		if (taiKhoanRepository.findByTenDangNhap(username).isEmpty()) {
			VaiTroEntity role = vaiTroRepository.findByTen(roleName)
					.orElseThrow(() -> new RuntimeException("Role " + roleName + " chưa tồn tại"));

			TaiKhoanEntity account = TaiKhoanEntity.builder().tenDangNhap(username)
					.matKhauHash(passwordEncoder.encode(rawPassword)).email(email).vaiTro(role).hoTen(hoTen)
					.soCmnd(soCmnd).soDienThoai(soDienThoai).diaChi(diaChi).isDeleted(false).hoatDong(true)
					.ngayTao(java.time.LocalDateTime.now()).ngayCapNhat(java.time.LocalDateTime.now()).build();

			taiKhoanRepository.save(account);
			System.out.println(">>> Đã khởi tạo account mặc định: " + username + " (" + roleName + ")");
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
